package com.example.android.thecommuter;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import com.example.android.thecommuter.data.SubwayContract;
import com.example.android.thecommuter.widgets.CustomList;

/**
 * Created by Shubhang on 2/7/2015.
 */
public class ArrivalsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private final int URL_LOADER = 0;
    private String[] mProjections = {SubwayContract._ID, SubwayContract.ROUTE_IMG, SubwayContract.FINAL_STATION, SubwayContract.ARRIVAL_TIME};
    private int[] mTo = {R.id._id, R.id.arrival_icon, R.id.arrival_dest, R.id.arrival_time};
    private SubwayCursorAdapter mAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_arrivals, container, false);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) rootView.findViewById(R.id.toolbar);


        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            if (getActivity().getIntent().getExtras() != null) {
                String stop = getActivity().getIntent().getExtras().getString(Intent.EXTRA_TEXT);
                toolbar.setTitle(stop);
                ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            }
        }

        getLoaderManager().initLoader(URL_LOADER, null, ArrivalsFragment.this);

        CustomList customList = (CustomList) rootView.findViewById(R.id.arrivals_list_view);
        customList.setContext(getActivity().getApplicationContext());
        mAdapter = new SubwayCursorAdapter(
                rootView.getContext(),
                R.layout.arrivals_list_item,
                null,
                mProjections,
                mTo,
                0
        );
        customList.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch(id) {
            case (URL_LOADER): {
                return new CursorLoader(
                        getActivity(),
                        SubwayContract.CONTENT_URI,
                        mProjections,
                        null,
                        null,
                        null
                );
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) { mAdapter.swapCursor(data); }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { mAdapter.swapCursor(null); }
}