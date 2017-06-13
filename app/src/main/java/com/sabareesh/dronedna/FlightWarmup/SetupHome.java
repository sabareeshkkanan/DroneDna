package com.sabareesh.dronedna.FlightWarmup;

import android.hardware.SensorManager;
import android.location.Location;
import android.util.Log;

import com.sabareesh.dronedna.deviceSensor.Gps;
import com.sabareesh.dronedna.deviceSensor.SensorMan;
import com.sabareesh.commonlib.models.GeoLocation;
import com.sabareesh.dronedna.models.HomeLocation;

/**
 * Created by sabareesh on 8/22/15.
 */
public class SetupHome implements Runnable {
    private Gps gps;

    @Override
    public void run() {
        gps=Gps.getInstance();
        try {
            waitAndAnalyze();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void waitAndAnalyze() throws InterruptedException {
        Location location=gps.getLocation();
        while (location.getAccuracy()>ConfigurationManager.get_manager().getFlightWarmSetting().getHomeLocationAccuracy()||!gps.isUpdateRecently()){
            Thread.sleep(500);

            location=gps.getLocation();
            Log.d("waitAndAnalyze", "Gps Accuracy " + location.getAccuracy());
        }
        GeoLocation geoLocation=new GeoLocation();
        geoLocation.setLocation(location);

        float pres=SensorMan.getSensor().getPressure();
        double pressAccuracy=location.getAltitude()- SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE,pres);
        if(pressAccuracy<0)
            pressAccuracy*=-1;
        while (pressAccuracy>100){
            pres=SensorMan.getSensor().getPressure();
            pressAccuracy=location.getAltitude()- SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE,pres);
            if(pressAccuracy<0)
                pressAccuracy*=-1;
            Log.d("homePressureCheck"," optimizing"+pres);
            Thread.sleep(100);
        }
        Log.d("homePressureCheck",""+pres);
        geoLocation.setPressure(pres);
        HomeLocation.setLocation(geoLocation);
        HomeLocation.setGeoAltitude();
    }
}
