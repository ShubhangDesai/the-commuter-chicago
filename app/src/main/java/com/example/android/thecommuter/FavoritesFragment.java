package com.example.android.thecommuter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.thecommuter.R;
import com.example.android.thecommuter.adapters.StopAdapter;
import com.example.android.thecommuter.managers.FavoritesManager;

import java.util.ArrayList;

/**
 * Created by Shubhang on 2/17/2015.
 */
public class FavoritesFragment extends Fragment {
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;

    @Override
    public void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(1).setChecked(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);
        }

        mDrawerLayout = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);
        FavoritesActivity.mDrawerLayout = mDrawerLayout;

        navigationView = (NavigationView) rootView.findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(1).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                int id = menuItem.getItemId();
                Intent intent;

                if (id == R.id.nav_home) {
                    intent = new Intent(getActivity(), MainActivity.class);
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

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);

        GridLayoutManager lm = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        rv.setLayoutManager(lm);

        FavoritesManager favoritesManager = new FavoritesManager(getActivity().getApplicationContext());
        favoritesManager.cleanFavorites();
        ArrayList<Integer> iconsArray = (favoritesManager.getImages());
        int[] icons = new int[iconsArray.size()];
        for (int i = 0; i < iconsArray.size(); i++) {
            icons[i] = iconsArray.get(i);
        }
        ArrayList<String> stopsArray = favoritesManager.getStations();
        String[] stops = new String[stopsArray.size()];
        for (int i = 0; i < stopsArray.size(); i++) {
            stops[i] = stopsArray.get(i);
        }

        if (stopsArray.size() == 0) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "You have no favorites", Toast.LENGTH_SHORT);
            toast.show();
        }

        StopAdapter sa = new StopAdapter(getActivity().getApplicationContext(), icons, stops, rv, -1);
        rv.setAdapter(sa);

        return rootView;
    }
}
