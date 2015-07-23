package com.example.android.thecommuter.widgets;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.thecommuter.ArrivalsActivity;
import com.example.android.thecommuter.data.SubwayContract;
import com.example.android.thecommuter.data.SubwayProvider;

/**
 * Created by Shubhang on 7/20/2015.
 */
public class StopOnClickListener implements View.OnClickListener  {
    Context mContext;
    RecyclerView mRecyclerView;
    int mLineId;
    String lineTxt;

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
            new String[] {"40140", "41680", "40900"}
    };

    String[] mStops;

    public StopOnClickListener(Context context, RecyclerView recyclerView, int lineId, String[] stops) {
        mContext = context;
        mRecyclerView = recyclerView;
        mLineId = lineId;
        mStops = stops;
    }

    @Override
    public void onClick(View v) {
        int stopId = mRecyclerView.getChildPosition(v);
        startSync(stopId);
        Intent intent = new Intent(mContext, ArrivalsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_TEXT, mStops[stopId] + " (" + lineTxt + ")");
        intent.putExtra("lineId", mLineId);
        mContext.startActivity(intent);
    }

    public void startSync(int stopId) {
        String line;

        if (mLineId == 0) {
            line = "Red";
            lineTxt = "Red";
        } else if (mLineId == 1) {
            line = "Blue";
            lineTxt = "Blue";
        } else if (mLineId == 2) {
            line = "Brn";
            lineTxt = "Brown";
        } else if (mLineId == 3) {
            line = "G";
            lineTxt = "Green";
        } else if (mLineId == 4) {
            line = "Org";
            lineTxt = "Orange";
        } else if (mLineId == 5) {
            line = "P";
            lineTxt = "Purple";
        } else if (mLineId == 6) {
            line = "Pink";
            lineTxt = "Pink";
        } else if (mLineId == 7) {
            line = "Y";
            lineTxt = "Yellow";
        } else {
            line = "";
            lineTxt = "";
        }

        SubwayProvider provider = new SubwayProvider();

        // Create the account type and default account
        Account mAccount = new Account("dummyaccount", "test.example.com");
        AccountManager accountManager = (AccountManager) mContext.getSystemService(mContext.ACCOUNT_SERVICE);
        // If the account already exists no harm is done but
        // a warning will be logged.
        accountManager.addAccountExplicitly(mAccount, null, null);

        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        settingsBundle.putString("station", stationIds[mLineId][stopId]);
        settingsBundle.putString("line", line);

        ContentResolver.setSyncAutomatically(mAccount, SubwayContract.AUTHORITY, true);
        ContentResolver.requestSync(mAccount, SubwayContract.AUTHORITY, settingsBundle);
    }
}
