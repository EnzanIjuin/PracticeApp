package com.example.shelton.practiceapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements  ListViewFragment.OnFragmentInteractionListener {

    FragmentManager manager;
    ListViewFragment listViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getFragmentManager();

        listViewFragment = (ListViewFragment) manager.findFragmentById(R.id.frag_listview);
        listViewFragment.setmListener(this);
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

    @Override
    public void onFragmentInteraction(int i) {
        Intent intent = new Intent(this, SecondActivity.class);
        switch (i) {
            case 0:
                intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                break;
            default:
                intent.putExtra("item", i);
                break;
        }
        if(intent != null && intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else if(intent != null && intent.resolveActivity(getPackageManager()) == null){
            Log.v("RAWR MA", "No activity found for this Intent.");
        }
    }
}
