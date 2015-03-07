package com.example.android.thecommuter.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;

import com.example.android.thecommuter.R;
import com.example.android.thecommuter.data.SubwayContract;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Handle the transfer of data between a server and an
 * app, using the Android sync adapter framework.
 */
public class CommuterSyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String LOG_TAG = CommuterSyncAdapter.class.getSimpleName();
    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;

    /**
     * Set up the sync adapter
     */
    public CommuterSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public CommuterSyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }

    /*
     * Specify the code you want to run in the sync adapter. The entire
     * sync adapter runs in a background thread, so you don't have to set
     * up your own background processing.
     */
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        String xml = null;
        Document doc = null;
        String rt;
        String rtLetter;
        int rtIcon;
        String destNm;
        String arrT;
        String mins;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        String value = extras.getString("station");
        String line = extras.getString("line");
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lapi.transitchicago.com")
                .appendPath("api")
                .appendPath("1.0")
                .appendPath("ttarrivals.aspx")
                .appendQueryParameter("key", "5faa26c0b51d49608cec89656eeaf383")
                .appendQueryParameter("mapid", value)
                .appendQueryParameter("max", "5")
                .appendQueryParameter("rt", line);

        String url = builder.build().toString();

        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return;
        }
        // return XML


        NodeList nl = doc.getElementsByTagName("eta");

        // looping through all item nodes <item>
        final int rows = mContentResolver.delete(SubwayContract.CONTENT_URI, null, null);

        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);

            NodeList rtNode = e.getElementsByTagName("rt");
            rt = rtNode.item(0).getFirstChild().getNodeValue();
            if (rt.equals("Red")) {
                rtIcon = R.drawable.red_circle;
            } else if (rt.equals("Blue")) {
                rtIcon = R.drawable.blue_circle;
            } else if (rt.equals("Brn")) {
                rtIcon = R.drawable.brown_circle;
            } else if (rt.equals("G")) {
                rtIcon = R.drawable.green_circle;
            } else if (rt.equals("Org")) {
                rtIcon = R.drawable.orange_circle;
            } else if (rt.equals("P")) {
                rtIcon = R.drawable.purple_circle;
            } else if (rt.equals("Pink")) {
                rtIcon = R.drawable.pink_circle;
            } else if (rt.equals("Y")) {
                rtIcon = R.drawable.yellow_circle;
            } else {
                rtIcon = R.drawable.white_circle;
            }

            NodeList destNmNode = e.getElementsByTagName("destNm");
            destNm = destNmNode.item(0).getFirstChild().getNodeValue();
            destNm = destNm + "-Bound";

            NodeList arrTNode = e.getElementsByTagName("arrT");
            arrT = arrTNode.item(0).getFirstChild().getNodeValue();
            String arrH = Character.toString(arrT.charAt(9)) + Character.toString(arrT.charAt(10));
            String arrM = Character.toString(arrT.charAt(12)) + Character.toString(arrT.charAt(13));
            String arrS = Character.toString(arrT.charAt(15)) + Character.toString(arrT.charAt(16));

            Time now = new Time();
            now.setToNow();
            String time = now.toString();
            String timeH = Character.toString(time.charAt(9)) + Character.toString(time.charAt(10));
            String timeM = Character.toString(time.charAt(11)) + Character.toString(time.charAt(12));
            String timeS = Character.toString(time.charAt(13)) + Character.toString(time.charAt(14));
            int hoursInt = ((Integer.parseInt(arrH)-Integer.parseInt(timeH)))*3600;
            int minsInt = (Integer.parseInt(arrM)-Integer.parseInt(timeM))*60;
            int secsInt = Integer.parseInt(arrS)-Integer.parseInt(timeS);
            int totMins = (hoursInt + minsInt + secsInt)/60;
            if (totMins < 0) {
                totMins = totMins + 1440;
            }
            mins = Integer.toString(totMins) + " mins";

            ContentValues values = new ContentValues();
            values.put(SubwayContract.ROUTE_IMG, rtIcon);
            values.put(SubwayContract.FINAL_STATION, destNm);
            values.put(SubwayContract.ARRIVAL_TIME, mins);
            final Uri uri = mContentResolver.insert(SubwayContract.CONTENT_URI, values);
            //Log.e(CommuterSyncAdapter.class.getSimpleName(), "Synced.");
        }
    }
}
