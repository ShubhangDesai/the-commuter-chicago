package com.example.android.thecommuter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.thecommuter.R;
import com.example.android.thecommuter.location.Loc;
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
    int[] mImages;

    private static Loc[][] locations = {//Red stops
            new Loc[] {new Loc(42.019161,-87.673093), new Loc(42.015876,-87.669092), new Loc(42.00819,-87.66595),
                       new Loc(42.001076,-87.660974), new Loc(41.993731,-87.659148), new Loc(41.990133,-87.659082),
                       new Loc(41.983572,-87.658862), new Loc(41.977833,-87.658683), new Loc(41.973365,-87.658561),
                       new Loc(41.969139,-87.658493), new Loc(41.965285,-87.657965), new Loc(41.953883,-87.655269),
                       new Loc(41.947462,-87.653636), new Loc(41.939562,-87.653345), new Loc(41.925051,-87.652866),
                       new Loc(41.910655,-87.649177), new Loc(41.90392,-87.631412), new Loc(41.896679,-87.6282),
                       new Loc(41.891665,-87.628021), new Loc(41.884809,-87.627813), new Loc(41.880745,-87.627696),
                       new Loc(41.878153,-87.627596), new Loc(41.874039,-87.627479), new Loc(41.867368,-87.627402),
                       new Loc(41.853206,-87.630968), new Loc(41.831191,-87.630636), new Loc(41.810318,-87.63094),
                       new Loc(41.79542,-87.631157), new Loc(41.780536,-87.630952), new Loc(41.768367,-87.625724),
                       new Loc(41.750419,-87.625112), new Loc(41.735372,-87.624717), new Loc(41.722596,-87.624391)}
    };

    FavoriteOnClickListener(Context context, RecyclerView recyclerView, int lineId, String[] stops, int[] images) {
        mContext = context;
        mRecyclerView = recyclerView;
        mLineId = lineId;
        mStops = stops;
        favoritesManager = new FavoritesManager(context);
        favorite = false;
        mImages = images;
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
                favoritesManager.addFavorite(mLineId, mStops[stopId], mImages[stopId], favoritesManager
                        .getStationIds().get(stopId), favoritesManager.getLocations().get(stopId));
            } else {
                favoritesManager.addFavorite(mLineId, mStops[stopId], mImages[stopId], StopOnClickListener.getStation
                        (mLineId,stopId), locations[mLineId][stopId]);
            };
            toast = Toast.makeText(mContext, "Added to favorites", Toast.LENGTH_SHORT);
        } else {
            favoritesManager.setFavorite(mLineId, stop, false);
            ((ImageView) v).setImageResource(R.drawable.ic_star);
            if (favorite) {
                favoritesManager.removeFavorite(mLineId, mStops[stopId], mImages[stopId], favoritesManager
                        .getStationIds().get(stopId));
            } else {
                favoritesManager.removeFavorite(mLineId, mStops[stopId], mImages[stopId], StopOnClickListener.getStation
                        (mLineId,stopId));
            }
            toast = Toast.makeText(mContext, "Removed from favorites", Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
