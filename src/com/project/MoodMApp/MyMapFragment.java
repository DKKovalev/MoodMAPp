package com.project.MoodMApp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.MoodMApp.assets.DatabaseHandler;
import com.project.MoodMApp.assets.Note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by PsichO on 05.04.2014.
 */
public class MyMapFragment extends Fragment {

    ArrayList<Note> notes;

    DatabaseHandler databaseHandler;

    IntentFilter intentFilter;

    GoogleMap map;

    HashMap<Marker, String> markers;

    public final static String UPDATE_MAP = "com.project.MoodMApp";

    String title;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Double lat = intent.getExtras().getDouble("lat");
            Double lng = intent.getExtras().getDouble("lng");

            LatLng latLng = new LatLng(lat, lng);

            Marker marker = map.addMarker(new MarkerOptions()
                    .position(latLng)
                    );



            markers = new HashMap<Marker, String>();
            markers.put(marker, title);



        }



    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, null, false);

        setupMapIfNeeded();

        setupMap();

        intentFilter = new IntentFilter();

        intentFilter.addAction(UPDATE_MAP);

        getActivity().registerReceiver(broadcastReceiver, intentFilter);


        return view;
    }

    private void setupMapIfNeeded(){

        if(map == null) {
            map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            if(map != null){
                setupMap();
            }
        }
    }

    private void setupMap() {

        Iterator iterator = markers.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();


        }

        /*databaseHandler = new DatabaseHandler(getActivity());
        notes = databaseHandler.getAllNotes();



        for (int i = 0; i < notes.size(); i++) {

            LatLng latLng = new LatLng(notes.get(i).getLat(), notes.get(i).getLng());

            Marker marker = map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(notes.get(i).getMood()));



            HashMap<Marker, String> markers = new HashMap<Marker, String>();
            markers.put(marker, title);
        }*/
    }

    @Override
    public void onResume() {
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        if(map != null){

            setupMap();
        }

        if(map == null) {
            map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }

            if(map != null){
                setupMap();
            }
        }
    }
