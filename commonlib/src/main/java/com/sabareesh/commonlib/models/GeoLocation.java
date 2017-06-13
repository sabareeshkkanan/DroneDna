package com.sabareesh.commonlib.models;

import android.location.Location;

import com.sabareesh.commonlib.Point;
/**
 * Created by sabareesh on 8/21/15.
 */
public class GeoLocation {
    private float pressure;
    private double altitude;
    private double latitude;
    private double longitude;
    private double gpsAltitude;

    public GeoLocation(double latitude, double longitude) {
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public GeoLocation(){

    }

    public void setLocation(Location location){
        latitude=location.getLatitude();
        longitude=location.getLongitude();
        gpsAltitude=location.getAltitude();
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getGpsAltitude() {
        return gpsAltitude;
    }

    public void setGpsAltitude(double gpsAltitude) {
        this.gpsAltitude = gpsAltitude;
    }
    public Point getPoint(){
        return new Point(latitude,longitude);
    }
}
