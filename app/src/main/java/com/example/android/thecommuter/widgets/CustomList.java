package com.example.android.thecommuter.widgets;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.android.thecommuter.SubwayCursorAdapter;

/**
 * Created by Shubhang on 7/21/2015.
 */
public class CustomList extends LinearLayout {
    Context mContext;
    SubwayCursorAdapter mAdapter = null;
    DataSetObserver observer = new DataSetObserver() {
        @Override
        public void onChanged() {
            refreshViews();
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
        addViews();
    }

    public void addViews() {
        int i = 0;
        while (mAdapter.getCursor() != null && mAdapter.getCursor().moveToNext()) {
            View v = mAdapter.newView(mContext, mAdapter.getCursor(), this);
            addView(v, i);
            i++;
        }
    }

    public void refreshViews() {
        Cursor cursor = mAdapter.getCursor();

        removeAllViews();

        int j = 0;
        while (cursor.moveToNext()) {
            addView(mAdapter.newView(mContext, cursor, this), j);
            j++;
        }
    }
}
