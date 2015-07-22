package com.example.android.thecommuter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.android.thecommuter.data.SubwayContract;
import com.example.android.thecommuter.data.SubwayProvider;
import com.example.android.thecommuter.sync.CommuterSyncAdapter;
import com.google.transit.realtime.GtfsRealtime;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class MainActivity extends ActionBarActivity {
    public static Boolean TABLET;
    SubwayProvider provider;
    Account mAccount;

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

            String[] lines = {
                    "Red",
                    "Blue",
                    "Brown",
                    "Green",
                    "Orange",
                    "Purple",
                    "Pink",
                    "Yellow"
            };

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        if (findViewById(R.id.container) != null) {
            TABLET = false;

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new LinesFragment())
                        .commit();
            }
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
        }

        return super.onOptionsItemSelected(item);
    }

    public void startSync(int stopId, long lineId) {

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
        AccountManager accountManager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
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
    }
}
