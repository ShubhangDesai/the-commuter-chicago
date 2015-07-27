package com.example.android.thecommuter.widgets;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.thecommuter.R;
import com.example.android.thecommuter.adapters.SubwayCursorAdapter;

import java.util.ArrayList;

/**
 * Created by Shubhang on 7/21/2015.
 */
public class CustomList extends LinearLayout {
    Context mContext;
    SubwayCursorAdapter mAdapter = null;
    DataSetObserver observer = new DataSetObserver() {
        @Override
        public void onChanged() {
            addViews();
        }

        @Override
        public void onInvalidated() {
            removeAllViews();
        }
    };

    public CustomList(Context context, AttributeSet attrs) { super(context, attrs); }

    public CustomList(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setAdapter(SubwayCursorAdapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(observer);
        }
        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(observer);
        }
        //addViews();
    }

    public void addViews() {
        Cursor cursor = mAdapter.getCursor();
        ArrayList<View> views = new ArrayList<>();

        removeAllViews();

        while (cursor != null && cursor.moveToNext()) {
            views.add(mAdapter.newView(mContext, cursor, this));
            //mAdapter.newView(mContext, cursor, this).findViewById(R.id.arrival_dest).toString();
        }

        views = sort(views);

        String station = "";
        final LayoutInflater inflater = LayoutInflater.from(mContext);

        for (int i = 0; i < views.size(); i++) {
            String text = ((TextView) views.get(i).findViewById(R.id.arrival_dest)).getText().toString();
            if (!text.equals(station)) {
                station = text;
                View header = inflater.inflate(R.layout.arrival_header, this, false);
                ((TextView) header.findViewById(R.id.header_text)).setText(text);
                ((TextView) header.findViewById(R.id.header_text)).setTextColor(getResources().getColor(R.color.abc_secondary_text_material_light));
                addView(header);
            }
            addView(views.get(i));
        }
    }

    public ArrayList<View> sort(ArrayList<View> views) {
        boolean sorted = false;
        int lastStationIndex = 0;
        String lastStationName;

        while(!sorted && views.size() > 0) {
            lastStationName = ((TextView) views.get(lastStationIndex).findViewById(R.id.arrival_dest)).getText().toString();
            //lastStationName = names.get(lastStationIndex);
            int temp = lastStationIndex+1;
            for (int i = temp; i < views.size(); i++) {
                if (((TextView) views.get(i).findViewById(R.id.arrival_dest)).getText().toString().equals(lastStationName)) {
                //if (names.get(i).equals(lastStationName)) {
                    lastStationIndex++;
                    views.add(lastStationIndex, views.get(i));
                    views.remove(i+1);
                }
            }
            lastStationIndex++;
            if (lastStationIndex == views.size()) {
                sorted = true;
            }
        }

        return views;
    }
}
