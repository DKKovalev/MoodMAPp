package com.project.MoodMApp;

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

/**
 * Created by PsichO on 05.04.2014.
 */
public class MyMapFragment extends Fragment {

    ArrayList<Note> notes;

    ArrayList<HashMap<String, Double>> mapCoords = new ArrayList<HashMap<String, Double>>();
    ArrayList<HashMap<String, String>> mapData = new ArrayList<HashMap<String, String>>();

    DatabaseHandler databaseHandler;

    GoogleMap map;

    String lat;
    String lng;
    String title;
    String comment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, null, false);

        setupMapIfNeeded();

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

        databaseHandler = new DatabaseHandler(getActivity());
        notes = databaseHandler.getAllNotes();

        for (int i = 0; i < notes.size(); i++) {

            LatLng latLng = new LatLng(notes.get(i).getLat(), notes.get(i).getLng());

            Marker marker = map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(notes.get(i).getMood()));

            HashMap<Marker, String> markers = new HashMap<Marker, String>();
            markers.put(marker, title);

            /*HashMap<String, Double> coordsHelper = new HashMap<String, Double>();
            coordsHelper.put(lat, notes.get(i).getLat());
            coordsHelper.put(lng, notes.get(i).getLng());

            mapCoords.add(coordsHelper);

            HashMap<String, String> dataHelper = new HashMap<String, String>();
            dataHelper.put(title, notes.get(i).getMood());
            dataHelper.put(comment, notes.get(i).getComment());

            mapData.add(dataHelper);

            for (HashMap<String, Double> hashMap : mapCoords) {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(hashMap.get(lat),
                                hashMap.get(lng)))
                        .title("Another marker"));
            }*/
        }
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
