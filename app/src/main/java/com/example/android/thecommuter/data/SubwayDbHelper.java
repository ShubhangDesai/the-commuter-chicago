package com.example.android.thecommuter.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shubhang on 2/5/2015.
 */
public class SubwayDbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    public static final String DB_NAME = "cta_comm_db";

    public SubwayDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TEST_TABLE =
                "CREATE TABLE " + SubwayContract.TABLE_NAME + " (" +
                        SubwayContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        SubwayContract.ROUTE_IMG + " INTEGER NOT NULL, " +
                        SubwayContract.FINAL_STATION + " TEXT NOT NULL, " +
                        SubwayContract.ARRIVAL_TIME + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_TEST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
