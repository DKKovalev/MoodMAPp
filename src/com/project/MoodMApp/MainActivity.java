package com.project.MoodMApp;

import android.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.project.MoodMApp.assets.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {



    Note note;

    public static ArrayList<Note> list;

    ViewPager viewPager;
    CollectionPagerAdapter collectionPagerAdapter;
    ActionBar actionBar;
    String[] tabs = {"Map", "News"};

    private DatabaseHandler databaseHandler;

    LocationHandler locationHandler;

    public static FragmentManager fragmentManager;

    final private static int GET_NEW_MOOD = 0000;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        fragmentManager = getSupportFragmentManager();

        databaseHandler = new DatabaseHandler(this);
        //---------------------------
        //databaseHandler.formatDB();
        //---------------------------
        list = new ArrayList<Note>();

        list.addAll(databaseHandler.getAllNotes());
        NewsFragment.noteAdapter = new NoteAdapter(this, R.layout.list_item,list);

        viewPager = (ViewPager)findViewById(R.id.pager);
        actionBar = getActionBar();
        collectionPagerAdapter = new CollectionPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(collectionPagerAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for(String tab_title : tabs){
            actionBar.addTab(actionBar.newTab().setText(tab_title).setTabListener(this));
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                actionBar.setSelectedNavigationItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_actions,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent addNewMood = new Intent(this,AddActivity.class);
                startActivityForResult(addNewMood, GET_NEW_MOOD);


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case GET_NEW_MOOD:
                    String moody = data.getStringExtra("mood");
                    String comment = data.getStringExtra("comment");
                    String image = data.getStringExtra("image");

                    ArrayAdapter<Note> noteArrayAdapter = (ArrayAdapter<Note>) NewsFragment.noteListView.getAdapter();

                    note = new Note();
                    note.setMood(moody);
                    note.setComment(comment);

                    note.setImage(image);

                    long time = System.currentTimeMillis();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
                    Date date = new Date(time);
                    note.setDate(simpleDateFormat.format(date));

                    locationHandler = new LocationHandler(MainActivity.this);

                    if(locationHandler.canGetLocation()){
                        double lat = locationHandler.getLat();
                        double lng = locationHandler.getLng();

                        note.setLat(lat);
                        note.setLng(lng);

                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + lat + "\nLong: " + lng, Toast.LENGTH_LONG).show();
                    }else {

                        locationHandler.checkSettings();

                    }

                    noteArrayAdapter.add(note);
                    noteArrayAdapter.notifyDataSetChanged();

                    databaseHandler.addNote(note);
                break;

                default:
                     break;

            }

        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }
}


