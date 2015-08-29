package com.sabareesh.dronedna.Controller;

import android.location.Location;
import android.util.Log;

import com.sabareesh.dronedna.FlightWarmup.ConfigurationManager;
import com.sabareesh.dronedna.deviceSensor.Gps;
import com.sabareesh.dronedna.deviceSensor.SensorMan;
import com.sabareesh.dronedna.hardware.SignalModel;
import com.sabareesh.dronedna.hardware.SignalModelHandle;
import com.sabareesh.dronedna.models.HomeLocation;

/**
 * Created by sabareesh on 8/18/15.
 */
public class AltitudeController extends Controller implements Runnable{

    double currentAltitude;

    private double previousPid;
    private double acceleration;
    private final double idleThrottle;
    double desiredAltitude;
    public AltitudeController(){
        location= Gps.getInstance().getLocation();
        sensors=SensorMan.getSensor();
        signalModel= SignalModelHandle.getModel();
        idleThrottle= (double) ConfigurationManager.getConfigManager().getDefaultValues().get("throttle");
        pidController=new PIDController(5,1,0.5);
        pidController.enable();
        pidController.setInputRange(0, 1100);
        pidController.setOutputRange(0, 100);
        pidController.setBoundControl(false);

    }
    public void setDesiredAltitude(double desiredAltitude) {
        this.desiredAltitude = desiredAltitude;
        pidController.setSetpoint(desiredAltitude);
    }


    @Override
    public void run() {
        initialize();
        process();
    }
    private void process(){

        double tFinal =getFinal();
        signalModel.setPWMValue("throttle", tFinal);

    }

    private double getFinal() {
        pidController.getInput(currentAltitude);
        double es=pidController.performPID()/100;
        Log.d("alt", "des " + desiredAltitude + " " + currentAltitude);
        double tValue=es-previousPid;
        if(acceleration>=0) {
            if (tValue > 0)
             acceleration += tValue;

            else
                acceleration = 0;
        }else {
            if(tValue<0)
                acceleration+=tValue;
            else
                acceleration=0;
        }
     if(acceleration>0.5)
            acceleration=0.5;
        else if(acceleration<-0.5)
         acceleration=-0.5;

        double tFinal=idleThrottle+acceleration;
        Log.d("pidController",""+es+" "+tValue+" "+tFinal);
        previousPid=es;
        return tFinal;
    }


    protected void initialize(){
        findCurrentAltitude();
    }
    private void findCurrentAltitude(){
        currentAltitude=HomeLocation.findAltitude(sensors.getPressure());
    }
}
