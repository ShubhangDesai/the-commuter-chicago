package com.example.android.thecommuter.adapters;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.thecommuter.ArrivalsActivity;
import com.example.android.thecommuter.managers.FavoritesManager;

import java.util.HashMap;

/**
 * Created by Shubhang on 7/20/2015.
 */
public class StopOnClickListener implements View.OnClickListener  {
    Context mContext;
    RecyclerView mRecyclerView;
    int mLineId;
    String lineTxt;
    boolean favorite = false;
    int stopId;
    FavoritesManager favoritesManager;

    private static String[][] stationIds = {//Red stops
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
    int[] mImages;

    public StopOnClickListener(Context context, RecyclerView recyclerView, int lineId, String[] stops, int[] images) {
        mContext = context;
        mRecyclerView = recyclerView;
        mLineId = lineId;
        mStops = stops;
        mImages = images;
        favoritesManager = new FavoritesManager(context);
        if (lineId == -1) { favorite = true; }
    }

    @Override
    public void onClick(View v) {
        stopId = mRecyclerView.getChildPosition(v);
        Intent intent = new Intent(mContext, ArrivalsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        int stop;
        if (favorite) {
            mLineId = favoritesManager.getLines().get(stopId);
            stop = favoritesManager.getStation(favoritesManager.getStations().get(stopId), mLineId);
        } else {
            stop = stopId;
        }
        if (mLineId == 0) {
            lineTxt = "Red";
        } else if (mLineId == 1) {
            lineTxt = "Blue";
        } else if (mLineId == 2) {
            lineTxt = "Brown";
        } else if (mLineId == 3) {
            lineTxt = "Green";
        } else if (mLineId == 4) {
            lineTxt = "Orange";
        } else if (mLineId == 5) {
            lineTxt = "Purple";
        } else if (mLineId == 6) {
            lineTxt = "Pink";
        } else if (mLineId == 7) {
            lineTxt = "Yellow";
        } else {
            lineTxt = "";
        }

        intent.putExtra(Intent.EXTRA_TEXT, mStops[stopId] + " (" + lineTxt + ")");
        intent.putExtra("stationId", stationIds[mLineId][stop]);
        intent.putExtra("lineId", mLineId);
        intent.putExtra("image", mImages[stopId]);
        intent.putExtra("favorite", favorite);
        mContext.startActivity(intent);
    }

    public static String getStation(int lineId, int stopId) {
        return stationIds[lineId][stopId];
    }
}
