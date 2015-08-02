package com.recursionlabs.thecommuter;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Shubhang on 7/30/2015.
 */
public class MetaData {
    private static Boolean NOTIFICATION_SERVICE_RUNNING = false;
    Context mContext;

    public MetaData(Context context) {
        mContext = context;
        read();
    }

    public void setRunning(boolean set) {
        NOTIFICATION_SERVICE_RUNNING = set;
        write();
    }

    public boolean getRunning() {
        return NOTIFICATION_SERVICE_RUNNING;
    }

    private void write() {
        try {
            FileOutputStream fos = mContext.openFileOutput("REMOVE_FILE", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(NOTIFICATION_SERVICE_RUNNING);
        } catch (Exception e) {

        }
    }

    private void read() {
        try {
            FileInputStream fis = mContext.openFileInput("LINES_FILE");
            ObjectInputStream ois = new ObjectInputStream(fis);
            NOTIFICATION_SERVICE_RUNNING = (boolean) ois.readObject();
            String test = "test";
        } catch (Exception e) {

        }
    }
}
