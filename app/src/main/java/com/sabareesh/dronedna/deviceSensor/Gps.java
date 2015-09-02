package com.sabareesh.dronedna.deviceSensor;

import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.sabareesh.dronedna.models.GeoLocation;

/**
 * Created by sabareesh on 8/17/15.
 */
public class Gps {
    private static Gps _instance;
    LocationManager manager;
    private Location location;
    private double latitude;
    private double smoothingFactor=1;
    private int activeSatellites;
    private int visibleSatellites;
    private GpsStatus gpsStatus;

    public int getVisibleSatellites() {
        return visibleSatellites;
    }

    public int getActiveSatellites() {
        return activeSatellites;
    }

    private boolean updateRecently;
    private double longitude;
public GeoLocation getSmoothLocation(){
    return new GeoLocation(latitude,longitude);
}


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static Gps getInstance(){
        return _instance;
    }
    public static void setInstance(LocationManager manager){
        _instance=new Gps(manager);
    }

    public boolean isUpdateRecently() {
        return updateRecently;
    }

    private Gps(LocationManager manager){
        this.manager=manager;
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100,0,new MyLocationListener());
        manager.addGpsStatusListener(new GpsStatusListener());
        location=manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getSmoothingFactor() {
        return smoothingFactor;
    }

    public void setSmoothingFactor(double smoothingFactor) {
        this.smoothingFactor = smoothingFactor;
    }
    class GpsStatusListener implements GpsStatus.Listener{

        @Override
        public void onGpsStatusChanged(int event) {
            findSatellites();
        }
    }
    class MyLocationListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
         //   Log.d("gps",location.getAccuracy()+" "+location.getLatitude()+" "+location.getLongitude()+" "+location.getAltitude());
            setLocation(location);
            smoother();
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

    private void findSatellites() {
        int satellites = 0;
        int satellitesInFix = 0;
        int timetofix = manager.getGpsStatus(gpsStatus).getTimeToFirstFix();

        for (GpsSatellite sat : manager.getGpsStatus(gpsStatus).getSatellites()) {
            if(sat.usedInFix()) {
                satellitesInFix++;
            }
            satellites++;
        }
        activeSatellites=satellitesInFix;
        visibleSatellites=satellites;
    }

    private void smoother() {
    double latitudeDifff=location.getLatitude()-latitude;
        latitude+=latitudeDifff/smoothingFactor;
        double longitudeDiff=location.getLongitude()-longitude;
        longitude+=longitudeDiff/smoothingFactor;
    }
}
