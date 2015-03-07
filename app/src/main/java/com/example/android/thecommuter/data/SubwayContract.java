package com.example.android.thecommuter.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Shubhang on 2/5/2015.
 */
public class SubwayContract implements BaseColumns {
    public static final String AUTHORITY = "com.example.android.thecommuter.data.SubwayProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String TABLE_NAME = "cta_comm_db";
    public static final String _ID = "_id";
    public static final String ROUTE_IMG = "route_img";
    public static final String FINAL_STATION = "final";
    public static final String ARRIVAL_TIME = "arrivals";
}
