package com.example.android.thecommuter.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.android.thecommuter.location.AlarmReceiver;
import com.example.android.thecommuter.StopsFragment;
import com.example.android.thecommuter.location.Loc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Shubhang on 7/23/2015.
 */
public class FavoritesManager {
    private static ArrayList<Integer> mLines = new ArrayList<>();
    private static ArrayList<String> mStations = new ArrayList<>();
    private static ArrayList<Integer> mImages = new ArrayList<>();
    private static ArrayList<String> mStationIds = new ArrayList<>();
    private static HashMap<Integer, HashMap<Integer, Boolean>> mFavorites = new HashMap();
    private static ArrayList<Boolean> mRemove = new ArrayList();
    private static ArrayList<Loc> mLocations = new ArrayList();
    Context mContext;

    public FavoritesManager(Context context) {
        mContext = context;
        mLocations.add(0, new Loc(40.461979, -88.930405));
        read();
    }

    public void addFavorite(int line, String station, int image, String stationId) {
        mLines.add(line);
        mStations.add(station);
        mImages.add(image);
        mStationIds.add(stationId);
        mRemove.add(false);
        write();

        Calendar c = Calendar.getInstance();
        PendingIntent mAlarmSender = PendingIntent.getBroadcast(mContext, 0,
                new Intent(mContext, AlarmReceiver.class),
                PendingIntent.FLAG_ONE_SHOT);
        long firstTime = c.getTimeInMillis();
        AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, firstTime, mAlarmSender);
    }

    public void removeFavorite(int line, String station, int image, String stationId) {
        int id = 0;
        for (int i = 0; i < mStationIds.size(); i++) {
            if (mLines.get(i) == line && mStationIds.get(i).equals(stationId) && !mRemove.get(i)) {
                id = i;
                break;
            }
        }
        mRemove.set(id, true);
        write();
    }

    public ArrayList<Integer> getLines() { return mLines; }

    public ArrayList<String> getStations() { return mStations; }

    public ArrayList<Integer> getImages() { return mImages; }

    public ArrayList<String> getStationIds() { return mStationIds; }

    public void setFavorite(int lineId, int stopId, boolean set) {
        if (mFavorites.get(lineId) == null) {
            mFavorites.put(lineId, new HashMap<Integer, Boolean>());
        }
        mFavorites.get(lineId).put(stopId, set);
        write();
    }

    public boolean isFavorite(int lineId, int stopId) {
        HashMap<Integer, Boolean> test1 = mFavorites.get(lineId);
        boolean test2;
        if (test1 != null && test1.get(stopId) != null) {
            test2 = test1.get(stopId);
        }
        if (mFavorites.get(lineId) != null && mFavorites.get(lineId).get(stopId) != null)
            return mFavorites.get(lineId).get(stopId);
        return false;
    }

    public void cleanFavorites() {
        int i = 0;
        boolean check = true;
        while(check) {
            int size = mRemove.size();
            if (i < size) {
                if (mRemove.get(i)) {
                    mLines.remove(i);
                    mStations.remove(i);
                    mImages.remove(i);
                    mStationIds.remove(i);
                    mRemove.remove(i);
                    i--;
                }
                i++;
            } else {
                check = false;
            }
        }
        write();
    }

    public int getStation(String stop, int lineId) {
        String[] stations;

        if (lineId == 0) {
            stations = StopsFragment.stopsRed;
        } else if (lineId == 1) {
            stations = StopsFragment.stopsBlue;
        } else if (lineId == 2) {
            stations = StopsFragment.stopsBrown;
        } else if (lineId == 3) {
            stations = StopsFragment.stopsGreen;
        } else if (lineId == 4) {
            stations = StopsFragment.stopsOrange;
        } else if (lineId == 5) {
            stations = StopsFragment.stopsPurple;
        } else if (lineId == 6) {
            stations = StopsFragment.stopsPink;
        } else {
            stations = StopsFragment.stopsYellow;
        }


        for (int i = 0; i < stations.length; i++) {
            if (stations[i].equals(stop)) {
                return i;
            }
        }
        return -1;
    }

    public static ArrayList<Loc> getLocations() {
        return mLocations;
    }

    public static ArrayList<Boolean> getRemove() {
        return mRemove;
    }

    private void write() {
        try {
            FileOutputStream fos = mContext.openFileOutput("LINES_FILE", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mLines);

            fos = mContext.openFileOutput("STATIONS_FILE", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(mStations);

            fos = mContext.openFileOutput("IMAGES_FILE", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(mImages);

            fos = mContext.openFileOutput("STATION_IDS_FILE", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(mStationIds);

            fos = mContext.openFileOutput("FAVORITES_FILE", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(mFavorites);

            fos = mContext.openFileOutput("REMOVE_FILE", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(mRemove);

            oos.close();
        } catch (Exception e) {

        }
    }

    private void read() {
        try {
            FileInputStream fis = mContext.openFileInput("LINES_FILE");
            ObjectInputStream ois = new ObjectInputStream(fis);
            mLines = (ArrayList<Integer>) ois.readObject();

            fis = mContext.openFileInput("STATIONS_FILE");
            ois = new ObjectInputStream(fis);
            mStations = (ArrayList<String>) ois.readObject();

            fis = mContext.openFileInput("IMAGES_FILE");
            ois = new ObjectInputStream(fis);
            mImages = (ArrayList<Integer>) ois.readObject();

            fis = mContext.openFileInput("STATION_IDS_FILE");
            ois = new ObjectInputStream(fis);
            mStationIds = (ArrayList<String>) ois.readObject();

            fis = mContext.openFileInput("FAVORITES_FILE");
            ois = new ObjectInputStream(fis);
            mFavorites = (HashMap<Integer, HashMap<Integer, Boolean>>) ois.readObject();

            fis = mContext.openFileInput("REMOVE_FILE");
            ois = new ObjectInputStream(fis);
            mRemove = (ArrayList<Boolean>) ois.readObject();

            ois.close();
        } catch (Exception e) {
            //nothing
        }
    }

    public boolean isEmpty() {
        if (mLines.isEmpty()) {
            return true;
        } else {
            for (boolean b : mRemove) {
                if (!b) {
                    return false;
                }
            }
            return true;
        }
    }
}
