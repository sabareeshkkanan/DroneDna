package com.sabareesh.dronedna.models;

import android.hardware.SensorManager;

import com.sabareesh.commonlib.models.GeoLocation;
import com.sabareesh.dronedna.WebService.GeoAltitude;

import java.io.IOException;

/**
 * Created by sabareesh on 8/21/15.
 */
public class HomeLocation {
    private static GeoLocation location;

    public static GeoLocation getLocation() {
        return location;
    }

    public static void setLocation(GeoLocation location) {
        HomeLocation.location = location;
    }

    public static void setGeoAltitude() {

            try {
                location.setAltitude(GeoAltitude.getAltitude(location.getLatitude(), location.getLongitude()));
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    public static double findAltitude(double currentPressure){
        return SensorManager.getAltitude(location.getPressure(),(float)currentPressure)+location.getAltitude();
    }
}
