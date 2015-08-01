package com.example.android.thecommuter.location;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.location.Location;
import android.text.format.Time;
import android.util.Log;

import com.example.android.thecommuter.MainActivity;
import com.example.android.thecommuter.MetaData;
import com.example.android.thecommuter.R;
import com.example.android.thecommuter.managers.FavoritesManager;

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
import java.util.Calendar;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Shubhang on 7/16/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    Context mContext;
    String mStation;
    String mLine;
    int mWait = 300000;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        FavoritesManager favoritesManager = new FavoritesManager(context);
        GPSTracker gps = new GPSTracker(context);
        ArrayList<Loc> locations = favoritesManager.getLocations();
        Calendar c = Calendar.getInstance();
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (!favoritesManager.isEmpty() && sharedPreferences.getBoolean("notification", true)) {
            editor.putBoolean("running", true);
            editor.commit();

            float minDistance = 804;
            Location current = new Location("Pt A");
            current.setLatitude(gps.getLatitude());
            current.setLongitude(gps.getLongitude());
            int closest = -1; //Change to -1
            for (int i = 0; i < locations.size(); i++) {
                //Log.e("Tag", Integer.toString(locations.size()));
                //Log.e("Tag", Boolean.toString(FavoritesManager.getRemove().get(i)));
                if (!FavoritesManager.getRemove().get(i)) {
                    Loc l = locations.get(i);
                    Location station = new Location("Pt B");
                    station.setLatitude(l.getLatitude());
                    station.setLongitude(l.getLongitude());
                    float distance = current.distanceTo(station);
                    if (distance < minDistance) {
                        minDistance = distance;
                        closest = i;
                    }
                }
            }

            if (closest != -1) {
                int line = favoritesManager.getLines().get(closest);
                String lineTxt;
                int color = 0;

                if (line == 0) {
                    mLine = "Red";
                    lineTxt = "Red";
                    color = R.color.red_primary;
                } else if (line == 1) {
                    mLine = "Blue";
                    lineTxt = "Blue";
                    color = R.color.blue_primary;
                } else if (line == 2) {
                    mLine = "Brn";
                    lineTxt = "Brown";
                    color = R.color.brown_primary;
                } else if (line == 3) {
                    mLine = "G";
                    lineTxt = "Green";
                    color = R.color.green_primary;
                } else if (line == 4) {
                    mLine = "Org";
                    lineTxt = "Orange";
                    color = R.color.orange_primary;
                } else if (line == 5) {
                    mLine = "P";
                    lineTxt = "Purple";
                    color = R.color.purple_primary;
                } else if (line == 6) {
                    mLine = "Pink";
                    lineTxt = "Pink";
                    color = R.color.pink_primary;
                } else if (line == 7) {
                    mLine = "Y";
                    lineTxt = "Yellow";
                    color = R.color.yellow_primary;
                } else {
                    mLine = "";
                    lineTxt = "";
                    color = Color.WHITE;
                }

                mStation = favoritesManager.getStationIds().get(closest);
                String title = "Next train at " + favoritesManager.getStations().get(closest) + " (" + lineTxt + ")";

                SubwayTask subwayTask = new SubwayTask();
                subwayTask.execute();
                String message = "";

                try {
                    message = subwayTask.get();
                } catch (Exception e) {
                    //do nothing
                }

                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
                NotificationManagerCompat manager = NotificationManagerCompat.from(context);
                NotificationCompat.Style style = new NotificationCompat.BigTextStyle();
                NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender();
                wearableExtender.setBackground(BitmapFactory.decodeResource(context.getResources(), color));
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

                builder.setContentIntent(contentIntent)
                        .setColor(context.getResources().getColor(color))
                        .setSmallIcon(R.drawable.ic_subway)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setStyle(style)
                        .setWhen(c.getTimeInMillis())
                        .setAutoCancel(true)
                        .extend(wearableExtender);

                manager.notify(0, builder.build());
            }
            PendingIntent mAlarmSender = PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmReceiver.class),
                    PendingIntent.FLAG_ONE_SHOT);
            long firstTime = c.getTimeInMillis() + mWait;
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, firstTime, mAlarmSender);
        } else {
            editor.putBoolean("running", false);
            editor.commit();
        }
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
                return "Encountered UnsupportedEncodingException, try again later";
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                return "Encountered ClientProtocolException, try again later";
            } catch (IOException e) {
                e.printStackTrace();
                return "Connect to network to view next train";
            }

            try {

                DocumentBuilder db = dbf.newDocumentBuilder();

                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));
                doc = db.parse(is);

            } catch (ParserConfigurationException e) {
                Log.e("Error: ", e.getMessage());
                return "Encountered ParserConfigurationException, try again later";
            } catch (SAXException e) {
                Log.e("Error: ", e.getMessage());
                return e.getMessage();
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage());
                return "Connect to network to view next train";
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
                    totMins = 2;
                } else {
                    mins = "arriving in " + Integer.toString(totMins) + " mins";
                }

                if (totMins < 5) {
                    //mWait = totMins * 60000;
                }
                result = destNm + ", " + mins;
            }
            return result;
        }
    }
}