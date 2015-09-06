package com.sabareesh.dronedna.Controller;

import android.location.Location;

import com.sabareesh.dronedna.deviceSensor.Gps;
import com.sabareesh.dronedna.deviceSensor.SensorMan;
import com.sabareesh.dronedna.hardware.SignalModel;
import com.sabareesh.dronedna.hardware.SignalModelHandle;

/**
 * Created by sabareesh on 8/27/15.
 */
public abstract class Controller {
    protected Location location;
    protected SensorMan sensors;
    protected SignalModel signalModel;
    protected PIDController pidController;
    protected Thread loopingThread;
    protected double acceleration;

    protected double previousPid;
    protected boolean loop;

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }


    public Controller(){
        location= Gps.getInstance().getLocation();
        sensors=SensorMan.getSensor();
        signalModel= SignalModelHandle.getModel();
    }
    public void startLoop(){
        setLoop(true);
        loopingThread=new Thread(new Runnable() {
            @Override
            public void run() {

                while (isLoop())
                {

                    try {
                        execute();
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        loopingThread.start();
    }
    public abstract void execute();

    public double getAcceleration(){
        return acceleration;
    }

}
