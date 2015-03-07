package com.example.android.thecommuter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.android.thecommuter.data.SubwayContract;
import com.example.android.thecommuter.data.SubwayProvider;
import com.example.android.thecommuter.sync.CommuterSyncAdapter;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Shubhang on 2/17/2015.
 */
public class StopsFragment extends Fragment {
    //Red Line info
    int[] iconsRed = {R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
    R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
    R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station};
    String[] stopsRed = {"Howard", "Jarvis", "Morse", "Loyola", "Granville", "Thorndale", "Bryn Mawr", "Berwyn", "Argyle", "Lawrence",
    "Wilson", "Sheridon", "Addison", "Belmont", "Fullerton", "North/Clyborn", "Clark/Division", "Chicago", "Grand", "Lake", "Monroe",
    "Jackson", "Harrison", "Roosevelt", "Cermak-Chinatown", "Sox/35th", "47th", "Garfield", "63rd", "69th", "79th", "87th", "95th/Dan Ryan"};

    //Blue Line info
    int[] iconsBlue = {R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station};
    String[] stopsBlue = {"O'Hare", "Rosemont", "Cumberland", "Harlem (O'Hare)", "Jefferson Park", "Montrose", "Irving Park", "Addison",
    "Belmont", "Logan Square", "California", "Western (O'Hare)", "Damen", "Division", "Chicago", "Grand", "Clark/Lake", "Washington", "Monroe",
    "Jackson", "LaSalle", "Clinton", "UIC-Halsted", "Racine", "Illinois Medical District", "Western (Forest Park)", "Kedzie-Homam", "Pulaski", "Cicero", "Austin",
    "Oak Park", "Harlem (Forest Park)", "Forest Park"};

    //Brown Line info
    int[] iconsBrown = {R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station};
    String[] stopsBrown = {"Kimball","Kedzie", "Francisco", "Rockwell", "Western", "Damen", "Montrose", "Ivring Park", "Addison",
    "Paulina", "Southport", "Belmont", "Wellington", "Diversey", "Fullerton", "Armitage", "Sedgwick", "Chicago", "Merchandise Mart",
    "Washington/Wells", "Quincy/Wells", "LaSalle/Van Buren", "Harold Washington Library-State/Van Buren", "Adams/Wabash", "Madison/Wabash",
    "Randolph/Wabash", "State/Lake", "Clark/Lake"};

    //Green Line info
    int[] iconsGreen = {R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station};
    String[] stopsGreen = {"Harlem/Lake", "Oak Park", "Ridgeland", "Austin", "Central", "Laramie", "Cicero", "Pulaski", "Conservatory",
    "Kedzie", "California", "Ashland", "Morgan", "Clinton", "Clark/Lake","State/Lake", "Randolph/Wabash", "Madison/Wabash", "Adams/Wabash",
    "Roosevelt", "35th-Bronzeville-IIT", "Indiana", "43rd", "47th", "51st", "Garfield", "King Drive", "Cottage Grove",
    "Halsted", "Ashland/63rd"};

    //Orange Line info
    int[] iconsOrange = {R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station};
    String[] stopsOrange = {"Midway", "Pulaski", "Kedzie", "Western", "35th/Archer", "Ashland", "Halsted", "Roosevelt", "Harold Washington Library-State/Van Buren",
    "LaSalle/Van Buren", "Quincy/Wells", "Washington/Wells", "Clark/Lake", "State/Lake", "Randolph/Wabash", "Madison/Wabash", "Adams/Wabash"};

    //Purple Line info
    int[] iconsPurple = {R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station};
    String[] stopsPurple = {"Linden", "Central", "Noyes", "Foster", "Davis", "Dempster", "Main", "South Blvd", "Howard", "Belmont",
    "Wellington", "Diversey", "Fullerton", "Armitage", "Sedgwick", "Chicago", "Merchandise Mart", "Clark/Lake", "State/Lake", "Randolph/Wabash",
    "Madison/Wabash", "Adams/Wabash", "Harold Washington Library-State/Van Buren", "LaSalle/Van Buren", "Quincy/Wells", "Washington/Wells"};

    //Pink Line info
    int[] iconsPink = {R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station,
            R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station};
    String[] stopsPink = {"54th/Cermak", "Cicero", "Kostner", "Pulaski", "Central Park", "Kedzie", "California", "Western", "Damen",
    "18th", "Polk", "Ashland", "Morgan", "Clinton", "Clark/Lake", "State/Lake", "Randolph/Wabash", "Madison/Wabash", "Harold Washington Library-State/Van Buren",
    "Adams/Wabash", "LaSalle/Van Buren", "Quincy/Wells", "Washington/Wells"};

    //Yellow Line info
    int[] iconsYellow = {R.drawable.ic_station, R.drawable.ic_station, R.drawable.ic_station};
    String[] stopsYellow = {"Skokie", "Oakton-Skokie", "Howard"};

    int[] icons;
    String[] stops;
    ListView list;

    SubwayProvider provider;
    Account mAccount;
    private static final String LOG_TAG = CommuterSyncAdapter.class.getSimpleName();
    String[][] stationIds = {//Red stops
            new String[] {"40900", "41190", "40100", "41300", "40760", "40880", "41380", "40340", "41200",
                    "40770", "40540", "40080", "41420", "41320", "41220", "40650", "40630", "41450",
                    "40330", "41660", "41090", "40560", "41490", "41400", "41000", "40190", "41230",
                    "41170", "40910", "40990", "40240", "41430", "40450"},
            //Blue stops
            new String[] {"40890", "40820", "40230", "40750", "41280", "41330", "40550", "41240", "40060",
                    "41020", "40570", "40670", "40590", "40320", "41410", "40490", "40380", "40370",
                    "40790", "40070", "41340", "40430", "40350", "40470", "40810", "40220", "40250",
                    "40920", "40970", "40010", "40980", "40180", "40390"},
            //Brown stops
            new String[] {"41290", "41180", "40870", "41010", "41480", "40090", "41500", "41460", "41440",
                    "41310", "40360", "41320", "41210", "40530", "41220", "40660", "40800", "40710",
                    "40460", "40730", "40040", "40160", "40850", "40680", "40640", "40200", "40260",
                    "40380"},
            //Green stops
            new String[] {"40020", "41350", "40610", "41350", "40610", "41260", "40280", "40700", "40480",
                    "40030", "41670", "41070", "41360", "40170", "41510", "41160", "40380", "40260",
                    "40200", "40640", "40680", "41400", "41120", "40300", "41270", "41080", "40130",
                    "40510", "41140", "40720", "40940", "40290"},
            //Orange stops
            new String[] {"40930", "40960", "41150", "40310", "40120", "41060", "41130", "41400", "40850",
                    "40160", "40040", "40730", "40380", "40260", "40200", "40640", "40680"},
            //Purple stops
            new String[] {"41050", "41250", "40400", "40520", "40050", "40690", "40270", "40840", "40900",
                    "41320", "41210", "40530", "41220", "40660", "40800", "40710", "40460", "40380", "40260",
                    "40200", "40640", "40680", "40850", "40160", "40040", "40730"},
            //Pink stops
            new String[] {"40580", "40420", "40600", "40150", "40780", "41040", "40440", "40740", "40210",
                    "40830", "41030", "40170", "41510", "41160", "40380", "40260", "40200", "40640",
                    "40850", "40680", "40160", "40040", "40730"},
            //Yellow stops
            new String[] {"40140", "41680", "40900"}};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final long lineId;
        if (!MainActivity.TABLET) {
            Intent intent = getActivity().getIntent();
            lineId = intent.getExtras().getLong(Intent.EXTRA_TEXT);
        } else {
            lineId = getArguments().getLong("lineId");
        }

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
         if (lineId == 0) {
            icons = iconsRed;
            stops = stopsRed;
         } else if (lineId == 1) {
            icons = iconsBlue;
            stops = stopsBlue;
         } else if (lineId == 2) {
             icons = iconsBrown;
             stops = stopsBrown;
         } else if (lineId == 3) {
             icons = iconsGreen;
             stops = stopsGreen;
         } else if (lineId == 4) {
             icons = iconsOrange;
             stops = stopsOrange;
         } else if (lineId == 5) {
             icons = iconsPurple;
             stops = stopsPurple;
         } else if (lineId == 6) {
             icons = iconsPink;
             stops = stopsPink;
         } else if (lineId == 7) {
             icons = iconsYellow;
             stops = stopsYellow;
         } else {
                icons = new int[] {R.drawable.white_circle};
                stops = new String[] {""};
        }

        LineAdapter adapter = new LineAdapter(getActivity().getApplicationContext(), icons, stops);
        list = (ListView) rootView.findViewById(R.id.lines_list_view);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int stopId = (int) parent.getItemIdAtPosition(position);
                if (!MainActivity.TABLET) {
                    startSync(stopId);
                    Intent intent = new Intent(getActivity(), ArrivalsActivity.class);
                    startActivity(intent);
                } else {
                    //((MainActivity) getActivity()).startTabletSync(stopId, lineId);
                    ((MainActivity) getActivity()).startSync(stopId, lineId);
                }
            }

            public void startSync(int stopId) {

                String line;

                if (lineId == 0)
                    line = "Red";
                else if (lineId == 1)
                    line = "Blue";
                else if (lineId == 2)
                    line = "Brn";
                else if (lineId == 3)
                    line = "G";
                else if (lineId == 4)
                    line = "Org";
                else if (lineId == 5)
                    line = "P";
                else if (lineId == 6)
                    line = "Pink";
                else if (lineId == 7)
                    line = "Y";
                else {
                    line = "";
                }

                int lineIdInt = (int) lineId;


                provider = new SubwayProvider();

                // Create the account type and default account
                mAccount = new Account("dummyaccount", "test.example.com");
                AccountManager accountManager = (AccountManager) getActivity().getSystemService(getActivity().ACCOUNT_SERVICE);
                // If the account already exists no harm is done but
                // a warning will be logged.
                accountManager.addAccountExplicitly(mAccount, null, null);

                Bundle settingsBundle = new Bundle();
                settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
                settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                settingsBundle.putString("station", stationIds[lineIdInt][stopId]);
                settingsBundle.putString("line", line);

                ContentResolver.setSyncAutomatically(mAccount, SubwayContract.AUTHORITY, true);
                ContentResolver.requestSync(mAccount, SubwayContract.AUTHORITY, settingsBundle);
                new TestTask().execute("40880", "Red");
            }
        });

        return rootView;
    }

private class TestTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        String xml = null;
        Document doc = null;
        String rt;
        String rtLetter;
        int rtIcon;
        String destNm;
        String arrT;
        String mins;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        String value = params[0];
        String line = params[1];
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
            return "test";
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return "test";
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return "test";
        } catch (NullPointerException e) {
            Log.e("Error: ", e.getMessage());
            return "test";
        }
        // return XML


        NodeList nl = doc.getElementsByTagName("eta");

        // looping through all item nodes <item>
        //final int rows = mContentResolver.delete(SubwayContract.CONTENT_URI, null, null);

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
            int hoursInt = ((Integer.parseInt(arrH) - Integer.parseInt(timeH))) * 3600;
            int minsInt = (Integer.parseInt(arrM) - Integer.parseInt(timeM)) * 60;
            int secsInt = Integer.parseInt(arrS) - Integer.parseInt(timeS);
            int totMins = (hoursInt + minsInt + secsInt) / 60;
            if (totMins < 0) {
                totMins = totMins + 1440;
            }
            mins = Integer.toString(totMins) + " mins";

            ContentValues values = new ContentValues();
            values.put(SubwayContract.ROUTE_IMG, rtIcon);
            values.put(SubwayContract.FINAL_STATION, destNm);
            values.put(SubwayContract.ARRIVAL_TIME, mins);
            //final Uri uri = mContentResolver.insert(SubwayContract.CONTENT_URI, values);
            //Log.e(CommuterSyncAdapter.class.getSimpleName(), "Synced.");
        }
        return "test";
    }
}
}
