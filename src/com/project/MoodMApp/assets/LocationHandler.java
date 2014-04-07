package com.project.MoodMApp.assets;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

/**
 * Created by PsichO on 04.04.14.
 */
public class LocationHandler extends Service implements LocationListener {

    final Context context;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    Location location;

    double lat;
    double lng;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES_IN_METERS = 10;


    private static final long MIN_TIME_BW_UPDATES_IN_MINUTES = 1000 * 60 * 1;

    protected LocationManager locationManager;

    public LocationHandler(Context context){
        this.context = context;
        getLocation();
    }

    public Location getLocation(){
        try{
            locationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetworkEnabled){

            } else {
                this.canGetLocation = true;

                if(isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES_IN_MINUTES, MIN_DISTANCE_CHANGE_FOR_UPDATES_IN_METERS, this);
                    if(locationManager != null){
                       location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location != null){
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                        }
                    }
                }

                if(isGPSEnabled) {
                    if(location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES_IN_MINUTES, MIN_DISTANCE_CHANGE_FOR_UPDATES_IN_METERS,this);
                        if(locationManager != null){
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if(location != null){
                                lat = location.getLatitude();
                                lng = location.getLongitude();
                            }
                        }
                    }
                }

            }

        }  catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public double getLat(){
        if(location != null){
            lat = location.getLatitude();
        }

        return lat;
    }

    public double getLng(){
        if(location != null){
            lng = location.getLongitude();
        }

        return lng;
    }

    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(LocationHandler.this);
        }
    }

    public boolean canGetLocation(){
        return this.canGetLocation;
    }

    public void checkSettings(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("GPS settings");
        builder.setMessage("GPS is not enabled. Do you want to turn it on?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onProviderDisabled(String provider){
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
