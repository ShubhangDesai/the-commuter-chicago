package com.recursionlabs.thecommuter;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.recursionlabs.thecommuter.widgets.CustomList;

/**
 * Created by Shubhang on 2/7/2015.
 */
public class ArrivalsFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_arrivals, container, false);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) rootView.findViewById(R.id.toolbar);
        String lineId = "";

        ((ImageView) rootView.findViewById(R.id.arrival_header)).setImageResource(getActivity().getIntent().getExtras().getInt("image"));

        long positionId = (long) getActivity().getIntent().getExtras().getInt("lineId");
        int primary = 0;
        int secondary = 0;
        if (positionId == 0) {
            primary = getActivity().getApplicationContext().getResources().getColor(R.color.red_primary);
            secondary = getActivity().getApplicationContext().getResources().getColor(R.color.red_primary_dark);
            lineId = "Red";
        }
        else if (positionId == 1) {
            primary = getActivity().getApplicationContext().getResources().getColor(R.color.blue_primary);
            secondary = getActivity().getApplicationContext().getResources().getColor(R.color.blue_primary_dark);
            lineId = "Blue";
        }
        else if (positionId == 2) {
            primary = getActivity().getApplicationContext().getResources().getColor(R.color.brown_primary);
            secondary = getActivity().getApplicationContext().getResources().getColor(R.color.brown_primary_dark);
            lineId = "Brn";
        }
        else if (positionId == 3) {
            primary = getActivity().getApplicationContext().getResources().getColor(R.color.green_primary);
            secondary = getActivity().getApplicationContext().getResources().getColor(R.color.green_primary_dark);
            lineId = "G";
        }
        else if (positionId == 4) {
            primary = getActivity().getApplicationContext().getResources().getColor(R.color.orange_primary);
            secondary = getActivity().getApplicationContext().getResources().getColor(R.color.orange_primary_dark);
            lineId = "Org";
        }
        else if (positionId == 5) {
            primary = getActivity().getApplicationContext().getResources().getColor(R.color.purple_primary);
            secondary = getActivity().getApplicationContext().getResources().getColor(R.color.purple_primary_dark);
            lineId = "P";
        }
        else if (positionId == 6) {
            primary = getActivity().getApplicationContext().getResources().getColor(R.color.pink_primary);
            secondary = getActivity().getApplicationContext().getResources().getColor(R.color.pink_primary_dark);
            lineId = "Pink";
        }
        else if (positionId == 7) {
            primary = getActivity().getApplicationContext().getResources().getColor(R.color.yellow_primary);
            secondary = getActivity().getApplicationContext().getResources().getColor(R.color.yellow_primary_dark);
            lineId = "Y";
        }


        if (toolbar != null) {
            if (getActivity().getIntent().getExtras() != null) {
                String stop = getActivity().getIntent().getExtras().getString(Intent.EXTRA_TEXT);
                CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
                collapsingToolbar.setTitle(stop);
                collapsingToolbar.setContentScrimColor(primary);
                if (android.os.Build.VERSION.SDK_INT >= 22)
                    getActivity().getWindow().setStatusBarColor(secondary);
                ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        String stationId = getActivity().getIntent().getExtras().getString("stationId");

        CustomList customList = (CustomList) rootView.findViewById(R.id.arrivals_list_view);
        customList.setContext(getActivity().getApplicationContext());
        customList.addViews(stationId, lineId);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.home) {
            int lineId = getActivity().getIntent().getExtras().getInt("lineId");
            Intent intent = new Intent(getActivity().getApplicationContext(), StopsActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, lineId);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}