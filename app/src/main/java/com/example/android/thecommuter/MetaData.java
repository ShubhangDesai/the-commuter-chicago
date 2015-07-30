package com.example.android.thecommuter;

/**
 * Created by Shubhang on 7/30/2015.
 */
public class MetaData {
    private static boolean NOTIFICATION_SERVICE_RUNNING = false;

    public MetaData() {
    }

    public void setRunning(boolean set) {
        NOTIFICATION_SERVICE_RUNNING = set;
    }

    public boolean getRunning() {
        return NOTIFICATION_SERVICE_RUNNING;
    }
}
