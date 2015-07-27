package com.example.android.thecommuter.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.thecommuter.R;
import com.example.android.thecommuter.data.SubwayContract;

/**
 * Created by Shubhang on 7/21/2015.
 */
public class SubwayCursorAdapter extends SimpleCursorAdapter {
    Context mContext;
    int mLayout;

    public SubwayCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int id) {
        super(context, layout, c, from, to, id);
        mContext = context;
        mLayout = layout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(mLayout, parent, false);

        TextView id = (TextView) view.findViewById(R.id._id);
        TextView dest = (TextView) view.findViewById(R.id.arrival_dest);
        TextView delay = (TextView) view.findViewById(R.id.delay);
        TextView time = (TextView) view.findViewById(R.id.arrival_time);

        id.setText(cursor.getString(cursor.getColumnIndex(SubwayContract._ID)));
        dest.setText(cursor.getString(cursor.getColumnIndex(SubwayContract.FINAL_STATION)));
        time.setText(cursor.getString(cursor.getColumnIndex(SubwayContract.ARRIVAL_TIME)));
        delay.setText(cursor.getString(cursor.getColumnIndex(SubwayContract.DELAY)));
        if (delay.getText().toString().equals("On Time")) {
            delay.setTextColor(mContext.getResources().getColor(R.color.green));
        } else {
            delay.setTextColor(mContext.getResources().getColor(R.color.red));
        }

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView id = (TextView) view.findViewById(R.id._id);
        TextView dest = (TextView) view.findViewById(R.id.arrival_dest);
        TextView delay = (TextView) view.findViewById(R.id.delay);
        TextView time = (TextView) view.findViewById(R.id.arrival_time);

        id.setText(cursor.getString(cursor.getColumnIndex(SubwayContract._ID)));
        dest.setText(cursor.getString(cursor.getColumnIndex(SubwayContract.FINAL_STATION)));
        time.setText(cursor.getString(cursor.getColumnIndex(SubwayContract.ARRIVAL_TIME)));
        delay.setText(cursor.getInt(cursor.getColumnIndex(SubwayContract.DELAY)));
    }
}