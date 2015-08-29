package com.sabareesh.dronedna.Controller;

import android.location.Location;

import com.sabareesh.dronedna.deviceSensor.SensorMan;
import com.sabareesh.dronedna.hardware.SignalModel;

/**
 * Created by sabareesh on 8/27/15.
 */
public abstract class Controller {
    protected Location location;
    protected SensorMan sensors;
    protected SignalModel signalModel;
    protected PIDController pidController;
    protected Thread loopingThread;
    protected abstract void initialize();
    public abstract void run();
    public void looper(){
        loopingThread=new Thread(new Runnable() {
            @Override
            public void run() {
                run();
            }
        });
        loopingThread.start();
    }

}
