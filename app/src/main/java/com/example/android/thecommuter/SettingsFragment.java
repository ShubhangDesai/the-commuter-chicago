package com.example.android.thecommuter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.example.android.thecommuter.adapters.LineAdapter;
import com.example.android.thecommuter.location.AlarmReceiver;

import java.util.Calendar;

/**
 * Created by Shubhang on 2/16/2015.
 */
public class SettingsFragment extends Fragment {
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;

    @Override
    public void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(2).setChecked(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        mDrawerLayout = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);
        SettingsActivity.mDrawerLayout = mDrawerLayout;

        navigationView = (NavigationView) rootView.findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(2).setChecked(true);

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
                        } else if (id == R.id.nav_home) {
                            intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Settings");
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);
        }

        final Switch notificationSwitch = (Switch) rootView.findViewById(R.id.notification_switch);
        notificationSwitch.setChecked(PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext())
                                        .getBoolean("notification", true));
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("notification", isChecked);
                editor.commit();

                if (isChecked) {
                    Calendar c = Calendar.getInstance();
                    PendingIntent mAlarmSender = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 0,
                            new Intent(getActivity().getApplicationContext(), AlarmReceiver.class),
                            PendingIntent.FLAG_ONE_SHOT);
                    long firstTime = c.getTimeInMillis();
                    AlarmManager am = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    if (!sharedPreferences.getBoolean("running", false)) {
                        am.set(AlarmManager.RTC_WAKEUP, firstTime, mAlarmSender);
                    }
                }
            }
        });

        return rootView;
    }
}
