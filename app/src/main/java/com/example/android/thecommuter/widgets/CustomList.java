package com.example.android.thecommuter.widgets;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.thecommuter.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Shubhang on 7/21/2015.
 */
public class CustomList extends LinearLayout {
    Context mContext;
    String mLine;
    String mStation;
    CustomList mList;
    String mToast;
    DataSetObserver observer = new DataSetObserver() {
        @Override
        public void onChanged() {
            //addViews();
        }

        @Override
        public void onInvalidated() {
            removeAllViews();
        }
    };

    public CustomList(Context context, AttributeSet attrs) { super(context, attrs); }

    public CustomList(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }

    public void setContext(Context context) {
        mContext = context;
        mList = this;
    }

    //public void addViews() {
    public void addViews(String stationId, String lineId) {
        mStation = stationId;
        mLine = lineId;
        SubwayTask subwayTask = new SubwayTask();
        subwayTask.execute();
        ArrayList<View> views = new ArrayList<>();

        try {
            views = subwayTask.get();
        } catch (Exception e) {
            //do nothing
        }

        if (views != null) {
                views = sort(views);

                String station = "";
                final LayoutInflater inflater = LayoutInflater.from(mContext);

                for (int i = 0; i < views.size(); i++) {
                    String text = ((TextView) views.get(i).findViewById(R.id.arrival_dest)).getText().toString();
                    if (!text.equals(station)) {
                        station = text;
                        View header = inflater.inflate(R.layout.arrival_header, this, false);
                        ((TextView) header.findViewById(R.id.header_text)).setText(text);
                        ((TextView) header.findViewById(R.id.header_text)).setTextColor(getResources().getColor(R.color.abc_secondary_text_material_light));
                        addView(header);
                    }
                    addView(views.get(i));
                }
        } else {
            Toast toast = Toast.makeText(mContext, mToast, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public ArrayList<View> sort(ArrayList<View> views) {
        boolean sorted = false;
        int lastStationIndex = 0;
        String lastStationName;

        while(!sorted && views.size() > 0) {
            lastStationName = ((TextView) views.get(lastStationIndex).findViewById(R.id.arrival_dest)).getText().toString();
            //lastStationName = names.get(lastStationIndex);
            int temp = lastStationIndex+1;
            for (int i = temp; i < views.size(); i++) {
                if (((TextView) views.get(i).findViewById(R.id.arrival_dest)).getText().toString().equals(lastStationName)) {
                //if (names.get(i).equals(lastStationName)) {
                    lastStationIndex++;
                    views.add(lastStationIndex, views.get(i));
                    views.remove(i+1);
                }
            }
            lastStationIndex++;
            if (lastStationIndex == views.size()) {
                sorted = true;
            }
        }

        return views;
    }

    private class SubwayTask extends AsyncTask<Void, Void, ArrayList<View>> {
        @Override
        protected ArrayList<View> doInBackground(Void... urls) {
            String xml = null;
            Document doc = null;
            String rt;
            String rtLetter;
            int rtIcon;
            String destNm;
            String arrT;
            String mins;
            String dly;
            String result = "result";
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            final LayoutInflater inflater = LayoutInflater.from(mContext);
            ArrayList<View> views = new ArrayList<View>();
            View view;

            String value = mStation;
            String line = mLine;
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
                mToast = "Internal error, try again later";
                return null;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                mToast = "Internal error, try again later";
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                mToast = "You are offline";
                return null;
            }

            try {

                DocumentBuilder db = dbf.newDocumentBuilder();

                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));
                doc = db.parse(is);

            } catch (ParserConfigurationException e) {
                Log.e("Error: ", e.getMessage());
                mToast = "Internal error, try again later";
                return null;
            } catch (SAXException e) {
                Log.e("Error: ", e.getMessage());
                mToast = "Internal error, try again later";
                return null;
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage());
                mToast = "You are offline";
                return null;
            }
            // return XML


            NodeList nl = doc.getElementsByTagName("eta");

            for (int i = 0; i < nl.getLength(); i++) {
                view = inflater.inflate(R.layout.arrivals_list_item, mList, false);
                TextView dest = (TextView) view.findViewById(R.id.arrival_dest);
                TextView delay = (TextView) view.findViewById(R.id.delay);
                TextView timeTV = (TextView) view.findViewById(R.id.arrival_time);

                Element e = (Element) nl.item(i);

                NodeList destNmNode = e.getElementsByTagName("destNm");
                destNm = destNmNode.item(0).getFirstChild().getNodeValue();
                destNm = destNm + "-Bound";
                dest.setText(destNm);

                NodeList dlyNode = e.getElementsByTagName("isDly");
                dly = dlyNode.item(0).getFirstChild().getNodeValue();

                if (dly.equals("0")) {
                    dly = "On Time";
                    delay.setTextColor(mContext.getResources().getColor(R.color.green));
                } else {
                    dly = "Delayed";
                    delay.setTextColor(mContext.getResources().getColor(R.color.red));
                }
                delay.setText(dly);

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
                String tz = TimeZone.getDefault().toString();
                if (tz.contains("ADT")) {
                    totMins = totMins - 1440;
                    totMins = totMins + 120;
                } else if (tz.contains("EDT")) {
                    totMins = totMins - 1440;
                    totMins = totMins + 60;
                } else if (tz.contains("MDT")) {
                    totMins = totMins - 60;
                } else if (tz.contains("PDT")) {
                    totMins = totMins - 120;
                } else if (tz.contains("AKDT")) {
                    totMins = totMins - 180;
                } else if (tz.contains("HADT")) {
                    totMins = totMins + 180;
                }
                if (totMins < 0) {
                    totMins = totMins + 1440;
                }
                if (totMins == 0) {
                    mins = "Arriving now";
                    totMins = 2;
                } else {
                    mins = "Arriving in " + Integer.toString(totMins) + " mins";
                }
                timeTV.setText(mins);
                views.add(view);
            }
            return views;
        }
    }
}
