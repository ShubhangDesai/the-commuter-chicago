package com.example.android.thecommuter.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Shubhang on 2/5/2015.
 */
public class SubwayProvider extends ContentProvider {
    SQLiteOpenHelper mTestDb = null;
    private static final int FIRST_CASE = 1;
    private static final String TAG = SubwayProvider.class.getSimpleName();

    private static UriMatcher matcher;
    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(SubwayContract.AUTHORITY, null, FIRST_CASE);
    }

    @Override
    public boolean onCreate() {
        mTestDb = new SubwayDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mTestDb.getReadableDatabase();
        Cursor rCursor;

        switch(matcher.match(uri)) {

            case FIRST_CASE: {
                rCursor = db.query(
                    SubwayContract.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                );
                break;
            }

            default: {
                rCursor = null;
            }
        }

        rCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return rCursor;
    }

    @Override
    public String getType(Uri uri) {
        String rUri;

        switch (matcher.match(uri)) {
            case FIRST_CASE: {
                rUri = "vnd.android.cursor.dir/" + SubwayContract.AUTHORITY;
            }

            default : {
                rUri = null;
            }
        }

        return rUri;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mTestDb.getWritableDatabase();

        switch(matcher.match(uri)) {

            case FIRST_CASE: {
                long id = db.insert(
                        SubwayContract.TABLE_NAME,
                        null,
                        values
                );

                Uri rUri = ContentUris.withAppendedId(SubwayContract.CONTENT_URI, id);
                getContext().getContentResolver().notifyChange(rUri, null);

                return rUri;

            }

            default: {
                return null;
            }
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mTestDb.getWritableDatabase();
        int rRows;

        switch (matcher.match(uri)) {
            case FIRST_CASE : {
                rRows = db.delete(SubwayContract.TABLE_NAME, selection, selectionArgs);
            }
            default: {
                rRows = 0;
            }
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
