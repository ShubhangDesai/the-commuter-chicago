package com.example.android.thecommuter;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class SettingsActivity extends ActionBarActivity {
    public static DrawerLayout mDrawerLayout;
    public static boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        running = true;
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SettingsFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        running = false;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }

    private class SubwayTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... urls) {
            String xml = null;
            Document doc = null;
            String rt;
            String rtLetter;
            int rtIcon;
            String destNm;
            String arrT;
            String mins;
            String result = "result";
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            String value = "0";
            String line = "0";
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lapi.transitchicago.com")
                    .appendPath("api")
                    .appendPath("1.0")
                    .appendPath("ttarrivals.aspx")
                    .appendQueryParameter("key", "5faa26c0b51d49608cec89656eeaf383")
                    .appendQueryParameter("mapid", value)
                    .appendQueryParameter("max", "1")
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
                return "UnsupportedEncodingException";
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                return "ClientProtocolException";
            } catch (IOException e) {
                e.printStackTrace();
                return "IOException";
            }

            try {

                DocumentBuilder db = dbf.newDocumentBuilder();

                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));
                doc = db.parse(is);

            } catch (ParserConfigurationException e) {
                Log.e("Error: ", e.getMessage());
                return "ParserConfigurationException";
            } catch (SAXException e) {
                Log.e("Error: ", e.getMessage());
                return e.getMessage();
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage());
                return "IOException";
            }
            // return XML


            NodeList nl = doc.getElementsByTagName("eta");

            for (int i = 0; i < nl.getLength(); i++) {
                Element e = (Element) nl.item(i);

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
                    mins = "arriving now";
                } else {
                    mins = "arriving in " + Integer.toString(totMins) + " mins";
                }
                //Log.e(CommuterSyncAdapter.class.getSimpleName(), "Synced.");
                result = destNm + ", " + mins;
            }
            return result;
        }
    }
}
