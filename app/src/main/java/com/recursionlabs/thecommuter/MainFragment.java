package com.recursionlabs.thecommuter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.recursionlabs.thecommuter.adapters.LineAdapter;

/**
 * Created by Shubhang on 2/16/2015.
 */
public class MainFragment extends Fragment {
    int[] images = {R.drawable.image_red_line, R.drawable.image_blue_line, R.drawable.image_brown_line, R.drawable.image_green_line, R.drawable.image_orange_line,
                R.drawable.image_purple_line, R.drawable.image_pink_line, R.drawable.image_yellow_line};
    String[] lines = {"Red Line", "Blue Line", "Brown Line", "Green Line", "Orange Line", "Purple Line", "Pink Line", "Yellow Line"};

    ListView list;
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;

    @Override
    public void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mDrawerLayout = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);
        MainActivity.mDrawerLayout = mDrawerLayout;

        navigationView = (NavigationView) rootView.findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        int id = menuItem.getItemId();
                        Intent intent;

                        if (id == R.id.nav_favorites) {
                            intent = new Intent(getActivity(), FavoritesActivity.class);
                            startActivity(intent);
                        } else if (id == R.id.nav_about) {
                            intent = new Intent(getActivity(), AboutActivity.class);
                            startActivity(intent);
                        } else if (id == R.id.nav_settings) {
                            intent = new Intent(getActivity(), SettingsActivity.class);
                            startActivity(intent);
                        }

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Lines");
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);
        }

        LineAdapter adapter = new LineAdapter(getActivity().getApplicationContext(), images, lines);
        list = (ListView) rootView.findViewById(R.id.lines_list_view);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long positionId = parent.getItemIdAtPosition(position);
                Intent intent = new Intent(getActivity(), StopsActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, positionId);

                startActivity(intent);
            }
        });

        /*
        Button button = (Button) rootView.findViewById(R.id.loc_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker gps = new GPSTracker(getActivity().getApplicationContext());

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    // \n is for new line
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
                            Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
            }
        });
        */

        return rootView;
    }
}
