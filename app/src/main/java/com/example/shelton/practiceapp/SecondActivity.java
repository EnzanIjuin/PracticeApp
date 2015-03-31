package com.example.shelton.practiceapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class SecondActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent i = getIntent();
        int item = i.getIntExtra("item", -1);
        Log.v("RAWR SA", "Got " + item);

        openFragment(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * 1. Calendar
     * 4. Map
     * 5. SMS
     */
    private void openFragment(int i) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch(i) {
            case 1:
                transaction.add(R.id.group, new CalendarFragment());
                break;
            case 4:
                transaction.add(R.id.group, new MyMapFragment());
                break;
            case 5:
                transaction.add(R.id.group, new SmsFragment());
                break;
            default:
                throw new UnsupportedOperationException();
        }
        transaction.commit();
    }
}
