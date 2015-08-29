package com.sabareesh.dronedna.deviceSensor;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by sabareesh on 8/17/15.
 */
public class Gps {
    private static Gps _instance;
    LocationManager manager;
    private Location location;
    public static Gps getInstance(){
        return _instance;
    }
    public static void setInstance(LocationManager manager){
        _instance=new Gps(manager);
    }

    public boolean isUpdateRecently() {
        return updateRecently;
    }

    private boolean updateRecently;
    private Gps(LocationManager manager){
        this.manager=manager;
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100,0,new MyLocationListener());
        location=manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    class MyLocationListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
         //   Log.d("gps",location.getAccuracy()+" "+location.getLatitude()+" "+location.getLongitude()+" "+location.getAltitude());
            setLocation(location);
            updateRecently=true;

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
