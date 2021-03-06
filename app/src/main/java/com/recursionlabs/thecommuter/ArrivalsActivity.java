package com.recursionlabs.thecommuter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Shubhang on 2/17/2015.
 */
public class ArrivalsActivity extends ActionBarActivity {
    public static boolean running = false;
    boolean favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        running = true;
        setContentView(R.layout.activity_arrivals);
        favorite = getIntent().getExtras().getBoolean("favorite");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ArrivalsFragment())
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
        } else {
            if (!favorite) {
                long lineId = (long) getIntent().getExtras().getInt("lineId");
                Intent intent = new Intent(this, StopsActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, lineId);
                startActivity(intent);
                return true;
            } else {
                Intent intent = new Intent(this, FavoritesActivity.class);
                startActivity(intent);
                return true;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        running = false;
    }
}
