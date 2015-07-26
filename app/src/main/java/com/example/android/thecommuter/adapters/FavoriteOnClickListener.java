package com.example.android.thecommuter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.thecommuter.R;
import com.example.android.thecommuter.managers.FavoritesManager;

/**
 * Created by Shubhang on 7/23/2015.
 */
public class FavoriteOnClickListener implements View.OnClickListener {
    Context mContext;
    int stopId;
    int mLineId;
    String[] mStops;
    RecyclerView mRecyclerView;
    FavoritesManager favoritesManager;
    boolean favorite;

    FavoriteOnClickListener(Context context, RecyclerView recyclerView, int lineId, String[] stops) {
        mContext = context;
        mRecyclerView = recyclerView;
        mLineId = lineId;
        mStops = stops;
        favoritesManager = new FavoritesManager(context);
        favorite = false;
    }

    @Override
    public void onClick(View v) {
        int id = mLineId;
        boolean test = favorite;
        View view = (View) v.getParent().getParent().getParent().getParent();
        stopId = mRecyclerView.getChildPosition(view);
        int stop;
        if (mLineId == -1 || favorite) {
            mLineId = favoritesManager.getLines().get(mRecyclerView.getChildPosition(view));
            stop = favoritesManager.getStation(mStops[stopId], mLineId);
            favorite = true;
        } else {
            stop = stopId;
        }
        Toast toast;
        if (!favoritesManager.isFavorite(mLineId, stop)) {
            favoritesManager.setFavorite(mLineId, stop, true);
            ((ImageView) v).setImageResource(R.drawable.ic_star_gold);
            if (favorite) {
                favoritesManager.addFavorite(mLineId, mStops[stopId], R.drawable.howard_small, favoritesManager
                        .getStationIds().get(stopId));
            } else {
                favoritesManager.addFavorite(mLineId, mStops[stopId], R.drawable.howard_small, StopOnClickListener.getStation
                        (mLineId,stopId));
            };
            toast = Toast.makeText(mContext, "Added to favorites", Toast.LENGTH_SHORT);
        } else {
            favoritesManager.setFavorite(mLineId, stop, false);
            ((ImageView) v).setImageResource(R.drawable.ic_star);
            if (favorite) {
                favoritesManager.removeFavorite(mLineId, mStops[stopId], R.drawable.howard_small, favoritesManager
                        .getStationIds().get(stopId));
            } else {
                favoritesManager.removeFavorite(mLineId, mStops[stopId], R.drawable.howard_small, StopOnClickListener.getStation
                        (mLineId,stopId));
            }
            toast = Toast.makeText(mContext, "Removed from favorites", Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
