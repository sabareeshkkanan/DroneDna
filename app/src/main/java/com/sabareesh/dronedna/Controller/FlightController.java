package com.sabareesh.dronedna.Controller;


import android.util.Log;

import com.sabareesh.dronedna.FlightWarmup.ConfigurationManager;
import com.sabareesh.dronedna.FlightWarmup.SetupHome;
import com.sabareesh.dronedna.hardware.SignalModelHandle;

/**
 * Created by sabareesh on 8/22/15.
 */
public class FlightController implements Runnable {
    private boolean loop;
    private int loopSleepTime=500;
    private boolean armed;
    private  AltitudeController altitudeController;

private Thread looperThread;
    public FlightController(){
    altitudeController=new AltitudeController();
        altitudeController.setDesiredAltitude(228);
    }
    public void Start(){
        loop=true;
        looperThread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    looper();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        looperThread.start();


    }
    private void looper() throws InterruptedException {
        while (loop){
            altitudeController.run();
            Thread.sleep(loopSleepTime);
        }
    }

    @Override
    public void run() {
        initialize();
        Start();

    }
    private void initialize(){
        homeSetup();
        arm();
        defaultPosition();
    }

    private void defaultPosition() {
        SignalModelHandle.getModel().setControls(ConfigurationManager.get_manager().getDefaultValues());
        Log.d("arm", "Default position set");
    }

    private void arm() {
        try {
            Thread.sleep(1000);

        SignalModelHandle.getModel().setControls(ConfigurationManager.get_manager().getArmingSetting());

            Log.d("arm", "Armed");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void homeSetup() {
        SetupHome home=new SetupHome();
        home.run();
        Log.d("home setup", "complete");
    }

    public void Stop() {
        loop=false;
    }
}
