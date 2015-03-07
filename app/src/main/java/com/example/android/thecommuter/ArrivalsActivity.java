package com.example.android.thecommuter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.ImageView;

import com.example.android.thecommuter.data.SubwayContract;
import com.example.android.thecommuter.data.SubwayProvider;
import com.example.android.thecommuter.sync.CommuterSyncAdapter;

/**
 * Created by Shubhang on 2/17/2015.
 */
public class ArrivalsActivity extends ActionBarActivity {
    SubwayProvider provider;
    Account mAccount;
    private static final String LOG_TAG = CommuterSyncAdapter.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrivals);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ArrivalsFragment())
                    .commit();
        }
        /*
        Intent intent = getIntent();
        final int lineId = (int) intent.getExtras().getLong("Line");
        final int stopId = (int) intent.getExtras().getLong(Intent.EXTRA_TEXT);
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


        provider = new SubwayProvider();

        // Create the account type and default account
        mAccount = new Account("dummyaccount", "test.example.com");
        AccountManager accountManager = (AccountManager) this.getSystemService(ACCOUNT_SERVICE);
        // If the account already exists no harm is done but
        // a warning will be logged.
        accountManager.addAccountExplicitly(mAccount, null, null);

        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        settingsBundle.putString("station", stationIds[lineId][stopId]);
        settingsBundle.putString("line", line);

        ContentResolver.setSyncAutomatically(mAccount, SubwayContract.AUTHORITY, true);
        ContentResolver.requestSync(mAccount, SubwayContract.AUTHORITY, settingsBundle);
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
