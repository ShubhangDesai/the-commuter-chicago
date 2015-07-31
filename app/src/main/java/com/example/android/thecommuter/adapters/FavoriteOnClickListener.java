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

    private static Loc[][] locations = {
            //Red locations
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
                       new Loc(41.750419,-87.625112), new Loc(41.735372,-87.624717), new Loc(41.722596,-87.624391)},
            //Blue locations
            new Loc[] {new Loc(41.981127,-87.900876), new Loc(41.983507,-87.859388), new Loc(41.984246,-87.838028),
                       new Loc(41.87349,-87.806961), new Loc(48.970833,-87.761111), new Loc(41.961539,-87.743574),
                       new Loc(41.952964,-87.729263), new Loc(41.9475,-87.719167), new Loc(41.939167,-87.711667),
                       new Loc(41.929722,-87.708611), new Loc(41.921944,-87.696944), new Loc(41.916111,-87.6875),
                       new Loc(41.909722,-87.6775), new Loc(41.903333,-87.666389), new Loc(41.896111,-87.655278),
                       new Loc(41.891111,-87.6475), new Loc(41.885767,-87.630886), new Loc(41.883164,-87.62944),
                       new Loc(41.880703,-87.629378), new Loc(41.878183,-87.629296), new Loc(41.875568,-87.631722),
                       new Loc(41.875539,-87.640984), new Loc(41.875474,-87.649707), new Loc(41.87592,-87.659458),
                       new Loc(41.875706,-87.673932), new Loc(41.875478,-87.688436), new Loc(41.874341,-87.70604),
                       new Loc(41.873797,-87.725663), new Loc(41.871574,-87.745154), new Loc(41.870851,-87.776812),
                       new Loc(41.872108,-87.791602), new Loc(41.982456,-87.80705), new Loc(41.874257,-87.817318)},
            //Brown locations
            new Loc[] {new Loc(41.967628,-87.712934), new Loc(41.966099,-87.709426), new Loc(41.966155,-87.702099),
                       new Loc(41.966208,-87.694720), new Loc(41.966259,-87.688448), new Loc(41.966398,-87.679004),
                       new Loc(41.961650,-87.675162), new Loc(41.954387,-87.674956), new Loc(41.946972,-87.674728),
                       new Loc(41.943722,-87.670858), new Loc(41.943832,-87.663383), new Loc(41.939562,-87.653345),
                       new Loc(41.936203,-87.653239), new Loc(41.932748,-87.653155), new Loc(41.925051,-87.652866),
                       new Loc(41.918234,-87.652659), new Loc(41.910397,-87.638631), new Loc(41.896467,-87.635833),
                       new Loc(41.888675,-87.633966), new Loc(41.882541,-87.633824), new Loc(41.878752,-87.633703),
                       new Loc(41.8768,-87.631739), new Loc(41.876862,-87.628196), new Loc(41.8795,-87.6261),
                       new Loc(41.884431,-87.626149), new Loc(41.88574,-87.627835), new Loc(41.885767,-87.630886)},
            //Green locations
            new Loc[] {new Loc(41.88706,-87.80486), new Loc(41.886784,-87.794323), new Loc(41.886955,-87.784628),
                       new Loc(41.887293,-87.774135), new Loc(41.887389,-87.76565), new Loc(41.887163,-87.754986),
                       new Loc(41.886519,-87.744698), new Loc(41.885412,-87.725404), new Loc(41.884904,-87.716523),
                       new Loc(41.884321,-87.706155), new Loc(41.88422,-87.696234), new Loc(41.885269,-87.666969),
                       new Loc(41.8856,-87.6522), new Loc(41.885678,-87.641782), new Loc(41.885767,-87.630886),
                       new Loc(41.88574,-87.627835), new Loc(41.884431,-87.626149), new Loc(41.8795,-87.6261),
                       new Loc(41.867368,-87.627402), new Loc(41.8531548,-87.626423), new Loc(41.831677,-87.625826),
                       new Loc(41.821732,-87.621371), new Loc(41.816462,-87.619021), new Loc(41.80940,-87.61909),
                       new Loc(41.80220,-87.61903), new Loc(41.79454,-87.61835)},
            //Orange locations
            new Loc[] {new Loc(41.78661,-87.737875), new Loc(41.799756,-87.724493), new Loc(41.804236,-87.704406),
                       new Loc(41.804546,-87.684019), new Loc(41.829353,-87.680622), new Loc(41.839234,-87.665317),
                       new Loc(41.84678,-87.648088), new Loc(41.867368,-87.627402), new Loc(41.876862,-87.628196),
                       new Loc(41.8768,-87.631739), new Loc(41.878752,-87.633703), new Loc(41.882541,-87.633824),
                       new Loc(41.885767,-87.630886), new Loc(41.88574,-87.627835), new Loc(41.884431,-87.626149),
                       new Loc(41.8795,-87.6261)},
            //Purple locations
            new Loc[] {new Loc(42.07379,-87.69056), new Loc(42.063950,-87.685742), new Loc(42.058088,-87.683196),
                       new Loc(42.054136,-87.683604), new Loc(42.04771,-87.68363), new Loc(42.04187,-87.68237),
                       new Loc(42.03333,-87.67925), new Loc(42.02792,-87.67919), new Loc(42.019161,-87.673093),
                       new Loc(41.965285,-87.657965), new Loc(41.953883,-87.655269), new Loc(41.947462,-87.653636),
                       new Loc(41.939562,-87.653345), new Loc(41.936203,-87.653239), new Loc(41.932748,-87.653155),
                       new Loc(41.925051,-87.652866), new Loc(41.918234,-87.652659), new Loc(41.910397,-87.638631),
                       new Loc(41.896467,-87.635833), new Loc(41.888675,-87.633966), new Loc(41.885767,-87.630886),
                       new Loc(41.88574,-87.627835), new Loc(41.884431,-87.626149), new Loc(41.8795,-87.6261),
                       new Loc(41.876862,-87.628196), new Loc(41.8768,-87.631739), new Loc(41.878752,-87.633703),
                       new Loc(41.882541,-87.633824)},
            //Pink locations
            new Loc[] {new Loc(41.851663,-87.759598), new Loc(41.85182,-87.745336), new Loc(41.853751,-87.733258),
                       new Loc(41.853732,-87.724311), new Loc(41.853839,-87.714842), new Loc(41.853964,-87.705408),
                       new Loc(41.854109,-87.694774), new Loc(41.854225,-87.685129), new Loc(41.854517,-87.675975),
                       new Loc(41.857955,-87.669178), new Loc(41.871551,-87.66953), new Loc(41.885269,-87.666969),
                       new Loc(41.8856,-87.6522), new Loc(41.885678,-87.641782), new Loc(41.885767,-87.630886),
                       new Loc(41.88574,-87.627835), new Loc(41.884431,-87.626149), new Loc(41.8795,-87.6261),
                       new Loc(41.876862,-87.628196), new Loc(41.8768,-87.631739), new Loc(41.878752,-87.633703),
                       new Loc(41.882541,-87.633824)},
            //Yellow locations
            new Loc[] {new Loc(42.040420,-87.752168), new Loc(42.0273,-87.7476), new Loc(42.019161,-87.673093)}
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
