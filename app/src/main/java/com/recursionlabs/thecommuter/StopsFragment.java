package com.recursionlabs.thecommuter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recursionlabs.thecommuter.adapters.StopAdapter;

/**
 * Created by Shubhang on 2/17/2015.
 */
public class StopsFragment extends Fragment {
    //Red Line info
    public static int[] iconsRed = {R.drawable.image_howard, R.drawable.image_jarvis, R.drawable.image_morse, R.drawable.image_loyola,
    R.drawable.image_granville, R.drawable.image_thorndale, R.drawable.image_bryn_mawr, R.drawable.image_berwyn, R.drawable.image_argyle,
            R.drawable.image_lawrence,
    R.drawable.image_wilson, R.drawable.image_sheridan, R.drawable.image_addison_red, R.drawable.image_belmont_red, R.drawable.image_fullerton,
    R.drawable.image_north_clybourn, R.drawable.image_clark_division, R.drawable.image_chicago_red, R.drawable.image_grand_red,
            R.drawable.image_lake,
    R.drawable.image_monroe_red, R.drawable.image_jackson_red, R.drawable.image_harrison, R.drawable.image_roosewelt, R.drawable.image_cermak_chinatown,
    R.drawable.image_sox_35th, R.drawable.image_47th_red, R.drawable.image_garfield_red, R.drawable.image_63rd, R.drawable.image_69th,
    R.drawable.image_79th, R.drawable.image_87th, R.drawable.image_95th};
    public static String[] stopsRed = {"Howard", "Jarvis", "Morse", "Loyola", "Granville", "Thorndale", "Bryn Mawr", "Berwyn", "Argyle",
            "Lawrence",
    "Wilson", "Sheridon", "Addison", "Belmont", "Fullerton", "North/Clyborn", "Clark/Division", "Chicago", "Grand", "Lake", "Monroe",
    "Jackson", "Harrison", "Roosevelt", "Cermak-Chinatown", "Sox/35th", "47th", "Garfield", "63rd", "69th", "79th", "87th", "95th/Dan Ryan"};

    //Blue Line info
    public static int[] iconsBlue = {R.drawable.image_ohare, R.drawable.image_rosemont, R.drawable.image_cumberland, R.drawable.image_harlem_blue_ohare_branch, R.drawable.image_jefferson_park,
            R.drawable.image_montrose_blue, R.drawable.image_irving_park_blue, R.drawable.image_addison_blue, R.drawable.image_belmont_blue, R.drawable.image_logan_sqaure,
            R.drawable.image_california_blue_ohare_branch, R.drawable.image_western_blue_ohare_branch, R.drawable.image_damen_blue_ohare_branch, R.drawable.image_division, R.drawable.image_chicago_blue,
            R.drawable.image_grand_blue, R.drawable.image_clark_lake, R.drawable.image_washington_blue, R.drawable.image_monroe_blue, R.drawable.image_jackson_blue,
            R.drawable.image_lasalle, R.drawable.image_clinton_blue, R.drawable.image_uic_halsted, R.drawable.image_racine, R.drawable.image_illinois_medical_district,
            R.drawable.image_western_blue_forest_park, R.drawable.image_kedzie_homan_blue, R.drawable.image_pulaski_blue_forest_park_branch, R.drawable.image_cicero_blue,
            R.drawable.image_austin_blue,
            R.drawable.image_oak_park_blue, R.drawable.image_harlem_blue_forest_park_branch, R.drawable.image_forest_park};
    public static String[] stopsBlue = {"O'Hare", "Rosemont", "Cumberland", "Harlem (O'Hare)", "Jefferson Park", "Montrose", "Irving Park", "Addison",
    "Belmont", "Logan Square", "California", "Western (O'Hare)", "Damen", "Division", "Chicago", "Grand", "Clark/Lake", "Washington", "Monroe",
    "Jackson", "LaSalle", "Clinton", "UIC-Halsted", "Racine", "Illinois Medical District", "Western (Forest Park)", "Kedzie-Homam", "Pulaski", "Cicero", "Austin",
    "Oak Park", "Harlem (Forest Park)", "Forest Park"};

    //Brown Line info
    public static int[] iconsBrown = {R.drawable.image_kimball, R.drawable.image_kedzie_brown, R.drawable.image_francisco, R.drawable.image_rockwell,
            R.drawable.image_western_brown, R.drawable.image_damen_brown, R.drawable.image_montrose_brown, R.drawable.image_irving_park_brown, R.drawable.image_addison_brown,
            R.drawable.image_paulina, R.drawable.image_south_port, R.drawable.image_belmont_blue, R.drawable.image_wellington, R.drawable.image_diversey,
            R.drawable.image_fullerton, R.drawable.image_armitage, R.drawable.image_sedgwick, R.drawable.image_chicago_brown, R.drawable.image_merchandise_mart,
            R.drawable.image_washington_wells, R.drawable.image_quincy, R.drawable.image_lasalle_van_buren, R.drawable.image_harold_washington_library_state_van_buren, R.drawable.image_adams_wabash,
            R.drawable.image_madison_wabash, R.drawable.image_randolph_wabash, R.drawable.image_state_lake, R.drawable.image_clark_lake};
    public static String[] stopsBrown = {"Kimball","Kedzie", "Francisco", "Rockwell", "Western", "Damen", "Montrose", "Irving Park", "Addison",
    "Paulina", "Southport", "Belmont", "Wellington", "Diversey", "Fullerton", "Armitage", "Sedgwick", "Chicago", "Merchandise Mart",
    "Washington/Wells", "Quincy/Wells", "LaSalle/Van Buren", "Library/Van Buren", "Adams/Wabash", "Madison/Wabash",
    "Randolph/Wabash", "State/Lake", "Clark/Lake"};

    //Green Line info
    public static int[] iconsGreen = {R.drawable.image_harlem_green, R.drawable.image_oak_park_green, R.drawable.image_ridgeland, R.drawable.image_austin_green, R.drawable.image_central_green,
            R.drawable.image_laramie, R.drawable.image_cicero_green, R.drawable.image_pulaski_green, R.drawable.image_conservatory, R.drawable.image_kedzie_green,
            R.drawable.image_california_green, R.drawable.image_ashland_green_pink, R.drawable.image_morgan, R.drawable.image_clinton_green, R.drawable.image_clark_lake,
            R.drawable.image_state_lake, R.drawable.image_randolph_wabash, R.drawable.image_madison_wabash, R.drawable.image_adams_wabash, R.drawable.image_roosewelt,
            R.drawable.image_35th_bronzeville_iit, R.drawable.image_indiana, R.drawable.image_43rd_street, R.drawable.image_47th_green, R.drawable.image_51st,
            R.drawable.image_garfield_green, R.drawable.image_king_drive, R.drawable.image_cottage_grove, R.drawable.image_halsted_green, R.drawable.image_ashland_63};
    public static String[] stopsGreen = {"Harlem/Lake", "Oak Park", "Ridgeland", "Austin", "Central", "Laramie", "Cicero", "Pulaski", "Conservatory",
    "Kedzie", "California", "Ashland", "Morgan", "Clinton", "Clark/Lake","State/Lake", "Randolph/Wabash", "Madison/Wabash", "Adams/Wabash",
    "Roosevelt", "35th-Bronzeville-IIT", "Indiana", "43rd", "47th", "51st", "Garfield", "King Drive", "Cottage Grove",
    "Halsted", "Ashland/63rd"};

    //Orange Line info
    public static int[] iconsOrange = {R.drawable.image_midway, R.drawable.image_pulaski_orange, R.drawable.image_kedzie_orange, R.drawable.image_western_orange, R.drawable.image_35th_archer,
            R.drawable.image_ashland_orange, R.drawable.image_halsted_orange, R.drawable.image_roosewelt, R.drawable.image_harold_washington_library_state_van_buren, R.drawable.image_lasalle_van_buren,
            R.drawable.image_quincy, R.drawable.image_washington_wells, R.drawable.image_clark_lake, R.drawable.image_state_lake, R.drawable.image_randolph_wabash,
            R.drawable.image_madison_wabash, R.drawable.image_adams_wabash};
    public static String[] stopsOrange = {"Midway", "Pulaski", "Kedzie", "Western", "35th/Archer", "Ashland", "Halsted", "Roosevelt", "Library/Van Buren",
    "LaSalle/Van Buren", "Quincy/Wells", "Washington/Wells", "Clark/Lake", "State/Lake", "Randolph/Wabash", "Madison/Wabash", "Adams/Wabash"};

    //Purple Line info
    public static int[] iconsPurple = {R.drawable.image_linden, R.drawable.image_central_purple, R.drawable.image_noyes, R.drawable.image_foster, R.drawable.image_davis,
            R.drawable.image_dempster, R.drawable.image_main, R.drawable.image_south_boulevard, R.drawable.image_howard, R.drawable.image_belmont_blue,
            R.drawable.image_wellington, R.drawable.image_diversey, R.drawable.image_fullerton, R.drawable.image_armitage, R.drawable.image_sedgwick,
            R.drawable.image_chicago_brown, R.drawable.image_merchandise_mart, R.drawable.image_clark_lake, R.drawable.image_state_lake, R.drawable.image_randolph_wabash,
            R.drawable.image_madison_wabash, R.drawable.image_adams_wabash, R.drawable.image_harold_washington_library_state_van_buren, R.drawable.image_lasalle_van_buren, R.drawable.image_quincy,
            R.drawable.image_washington_wells};
    public static String[] stopsPurple = {"Linden", "Central", "Noyes", "Foster", "Davis", "Dempster", "Main", "South Blvd", "Howard", "Belmont",
    "Wellington", "Diversey", "Fullerton", "Armitage", "Sedgwick", "Chicago", "Merchandise Mart", "Clark/Lake", "State/Lake", "Randolph/Wabash",
    "Madison/Wabash", "Adams/Wabash", "Library/Van Buren", "LaSalle/Van Buren", "Quincy/Wells", "Washington/Wells"};

    //Pink Line info
    public static int[] iconsPink = {R.drawable.image_54_cermak, R.drawable.image_cicero_pink, R.drawable.image_kostner, R.drawable.image_pulaski_pink, R.drawable.image_central_park,
            R.drawable.image_kedzie_pink, R.drawable.image_california_pink, R.drawable.image_western_pink, R.drawable.image_damen_pink, R.drawable.image_18th,
            R.drawable.image_polk, R.drawable.image_ashland_green_pink, R.drawable.image_morgan, R.drawable.image_clinton_green, R.drawable.image_clark_lake,
            R.drawable.image_state_lake, R.drawable.image_randolph_wabash, R.drawable.image_madison_wabash, R.drawable.image_harold_washington_library_state_van_buren, R.drawable.image_adams_wabash,
            R.drawable.image_lasalle_van_buren, R.drawable.image_quincy, R.drawable.image_washington_wells};
    public static String[] stopsPink = {"54th/Cermak", "Cicero", "Kostner", "Pulaski", "Central Park", "Kedzie", "California", "Western", "Damen",
    "18th", "Polk", "Ashland", "Morgan", "Clinton", "Clark/Lake", "State/Lake", "Randolph/Wabash", "Madison/Wabash", "Library/Van Buren",
    "Adams/Wabash", "LaSalle/Van Buren", "Quincy/Wells", "Washington/Wells"};

    //Yellow Line info
    public static int[] iconsYellow = {R.drawable.image_demster_skokie, R.drawable.image_oakton_skokie, R.drawable.image_howard};
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
