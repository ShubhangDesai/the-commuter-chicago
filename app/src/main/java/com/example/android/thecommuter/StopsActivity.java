package com.example.android.thecommuter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Shubhang on 2/17/2015.
 */
public class StopsActivity extends ActionBarActivity {
    public static boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        running = true;
        setContentView(R.layout.activity_stops);

        long positionId = -1;
        if (getIntent().getExtras() != null) {
            positionId = getIntent().getExtras().getLong(Intent.EXTRA_TEXT);
        }

        String line = "";
        if (positionId == 0) { line = "Red Line"; }
        else if (positionId == 1) { line = "Blue Line"; }
        else if (positionId == 2) { line = "Brown Line"; }
        else if (positionId == 3) { line = "Green Line"; }
        else if (positionId == 4) { line = "Orange Line"; }
        else if (positionId == 5) { line = "Purple Line"; }
        else if (positionId == 6) { line = "Pink Line"; }
        else if (positionId == 7) { line = "Yellow Line"; }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(line);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new StopsFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        running = false;
    }
}
