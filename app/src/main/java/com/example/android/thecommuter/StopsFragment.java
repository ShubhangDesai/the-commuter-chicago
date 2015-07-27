package com.example.android.thecommuter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.thecommuter.adapters.StopAdapter;

/**
 * Created by Shubhang on 2/17/2015.
 */
public class StopsFragment extends Fragment {
    //Red Line info
    public static int[] iconsRed = {R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
    R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
    R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small};
    public static String[] stopsRed = {"Howard", "Jarvis", "Morse", "Loyola", "Granville", "Thorndale", "Bryn Mawr", "Berwyn", "Argyle", "Lawrence",
    "Wilson", "Sheridon", "Addison", "Belmont", "Fullerton", "North/Clyborn", "Clark/Division", "Chicago", "Grand", "Lake", "Monroe",
    "Jackson", "Harrison", "Roosevelt", "Cermak-Chinatown", "Sox/35th", "47th", "Garfield", "63rd", "69th", "79th", "87th", "95th/Dan Ryan"};

    //Blue Line info
    public static int[] iconsBlue = {R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small};
    public static String[] stopsBlue = {"O'Hare", "Rosemont", "Cumberland", "Harlem (O'Hare)", "Jefferson Park", "Montrose", "Irving Park", "Addison",
    "Belmont", "Logan Square", "California", "Western (O'Hare)", "Damen", "Division", "Chicago", "Grand", "Clark/Lake", "Washington", "Monroe",
    "Jackson", "LaSalle", "Clinton", "UIC-Halsted", "Racine", "Illinois Medical District", "Western (Forest Park)", "Kedzie-Homam", "Pulaski", "Cicero", "Austin",
    "Oak Park", "Harlem (Forest Park)", "Forest Park"};

    //Brown Line info
    public static int[] iconsBrown = {R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small};
    public static String[] stopsBrown = {"Kimball","Kedzie", "Francisco", "Rockwell", "Western", "Damen", "Montrose", "Ivring Park", "Addison",
    "Paulina", "Southport", "Belmont", "Wellington", "Diversey", "Fullerton", "Armitage", "Sedgwick", "Chicago", "Merchandise Mart",
    "Washington/Wells", "Quincy/Wells", "LaSalle/Van Buren", "Library/Van Buren", "Adams/Wabash", "Madison/Wabash",
    "Randolph/Wabash", "State/Lake", "Clark/Lake"};

    //Green Line info
    public static int[] iconsGreen = {R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small};
    public static String[] stopsGreen = {"Harlem/Lake", "Oak Park", "Ridgeland", "Austin", "Central", "Laramie", "Cicero", "Pulaski", "Conservatory",
    "Kedzie", "California", "Ashland", "Morgan", "Clinton", "Clark/Lake","State/Lake", "Randolph/Wabash", "Madison/Wabash", "Adams/Wabash",
    "Roosevelt", "35th-Bronzeville-IIT", "Indiana", "43rd", "47th", "51st", "Garfield", "King Drive", "Cottage Grove",
    "Halsted", "Ashland/63rd"};

    //Orange Line info
    public static int[] iconsOrange = {R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small};
    public static String[] stopsOrange = {"Midway", "Pulaski", "Kedzie", "Western", "35th/Archer", "Ashland", "Halsted", "Roosevelt", "Library/Van Buren",
    "LaSalle/Van Buren", "Quincy/Wells", "Washington/Wells", "Clark/Lake", "State/Lake", "Randolph/Wabash", "Madison/Wabash", "Adams/Wabash"};

    //Purple Line info
    public static int[] iconsPurple = {R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small};
    public static String[] stopsPurple = {"Linden", "Central", "Noyes", "Foster", "Davis", "Dempster", "Main", "South Blvd", "Howard", "Belmont",
    "Wellington", "Diversey", "Fullerton", "Armitage", "Sedgwick", "Chicago", "Merchandise Mart", "Clark/Lake", "State/Lake", "Randolph/Wabash",
    "Madison/Wabash", "Adams/Wabash", "Library/Van Buren", "LaSalle/Van Buren", "Quincy/Wells", "Washington/Wells"};

    //Pink Line info
    public static int[] iconsPink = {R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small,
            R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small};
    public static String[] stopsPink = {"54th/Cermak", "Cicero", "Kostner", "Pulaski", "Central Park", "Kedzie", "California", "Western", "Damen",
    "18th", "Polk", "Ashland", "Morgan", "Clinton", "Clark/Lake", "State/Lake", "Randolph/Wabash", "Madison/Wabash", "Library/Van Buren",
    "Adams/Wabash", "LaSalle/Van Buren", "Quincy/Wells", "Washington/Wells"};

    //Yellow Line info
    public static int[] iconsYellow = {R.drawable.howard_small, R.drawable.howard_small, R.drawable.howard_small};
    public static String[] stopsYellow = {"Skokie", "Oakton-Skokie", "Howard"};

    int[] icons;
    String[] stops;
    RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final long lineId;
        Intent intent = getActivity().getIntent();
        lineId = intent.getExtras().getLong(Intent.EXTRA_TEXT);

        View rootView = inflater.inflate(R.layout.fragment_stops, container, false);

        if (lineId == 0) {
            icons = iconsRed;
            stops = stopsRed;
        } else if (lineId == 1) {
            icons = iconsBlue;
            stops = stopsBlue;
        } else if (lineId == 2) {
            icons = iconsBrown;
            stops = stopsBrown;
        } else if (lineId == 3) {
            icons = iconsGreen;
            stops = stopsGreen;
        } else if (lineId == 4) {
            icons = iconsOrange;
            stops = stopsOrange;
        } else if (lineId == 5) {
            icons = iconsPurple;
            stops = stopsPurple;
        } else if (lineId == 6) {
            icons = iconsPink;
            stops = stopsPink;
        } else if (lineId == 7) {
            icons = iconsYellow;
            stops = stopsYellow;
        } else {
            icons = new int[]{R.drawable.white_circle};
            stops = new String[]{""};
        }

        rv = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);

        GridLayoutManager lm = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        rv.setLayoutManager(lm);

        StopAdapter sa = new StopAdapter(getActivity().getApplicationContext(), icons, stops, rv, (int) lineId);
        rv.setAdapter(sa);

        return rootView;
    }
}
