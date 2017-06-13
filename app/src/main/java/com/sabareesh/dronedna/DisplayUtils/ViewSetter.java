package com.sabareesh.dronedna.DisplayUtils;

import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.ToggleButton;
import android.os.Handler;

import com.sabareesh.dronedna.Controller.FlightController;
import com.sabareesh.dronedna.FlightWarmup.ConfigurationManager;
import com.sabareesh.dronedna.hardware.SignalModelHandle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by sabareesh on 8/14/15.
 */
public  class ViewSetter {
    private ToggleButton ledButton;
    private Map<String,SeekBar> seekBarMap;
    private ToggleButton armer;
    private ToggleButton controlSwitch;
    int csStat=0;
    int cstahelper=1;
    Thread flightControllerThread;
    FlightController controller;
    private ToggleButton autoPilot;

    public Map<String, SeekBar> getSeekBarMap() {
        return seekBarMap;
    }

    public void setSeekBarMap(Map<String, SeekBar> seekBarMap) {
        this.seekBarMap = seekBarMap;
    }

    public ToggleButton getLedButton() {
        return ledButton;
    }

    public void setLedButton(ToggleButton ledButton) {
        this.ledButton = ledButton;
    }

    private static ViewSetter ourInstance = new ViewSetter();

    public static ViewSetter getInstance() {
        return ourInstance;
    }

    private ViewSetter(){
        seekBarMap=new HashMap<>();
        controller=new FlightController();
    }
    public void setEvents(){
        for (String key : seekBarMap.keySet()) {
            seekBarEvents(key);
        }

        buttonEvents();
    }
    private void seekBarEvents(final String key){
        SeekBar seekBar=seekBarMap.get(key);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double v = ((double) progress / (double) seekBar.getMax());
                SignalModelHandle.getModel().setPWMValue(key, v);
                Log.d("seekValue", v + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private void buttonEvents(){
        ledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();

                final Runnable r = new Runnable() {
                    public void run() {


                        SignalModelHandle.getModel().setDigital("led", ledButton.isChecked());
                        //Write(write);
                    }
                };
                handler.post(r);

            }
        });
        controlSwitch.setOnClickListener(new View.OnClickListener() {
            List<String> modes=new ArrayList<String>(){{add("gps");add("attitude");add("failSafe");add("manual");}};
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();

                final Runnable r = new Runnable() {
                    public void run() {
                        csStat+=cstahelper;
                        if(csStat>3)
                        {
                            cstahelper*=-1;
                            csStat+=cstahelper-1;
                        }
                        else if(csStat==0)
                            cstahelper*=-1;

                        controlSwitch.setText(modes.get(csStat));
                        SignalModelHandle.getModel().setPWMValue("U", (double) (ConfigurationManager.getConfigManager().getControlModes().get(modes.get(csStat))));
                        //Write(write);
                    }
                };
                handler.post(r);

            }
        });
        armer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();

                final Runnable r = new Runnable() {
                    public void run() {

                        if (armer.isChecked())
                            SignalModelHandle.getModel().setControls(ConfigurationManager.get_manager().getArmingSetting());
                        else
                            SignalModelHandle.getModel().setControls(ConfigurationManager.get_manager().getDefaultValues());
                        //Write(write);
                    }
                };
                handler.post(r);

            }
        });
        autoPilot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();

                final Runnable r = new Runnable() {
                    public void run() {

                        if (autoPilot.isChecked())
                            autoPilotOnEvent();
                        else
                        autoPilotStopEvent();
                        //Write(write);
                    }
                };
                handler.post(r);

            }
        });

    }
    public void autoPilotOnEvent(){
        setupFlightController();
    }
    public void autoPilotStopEvent(){
        controller.Stop();
    }
    private void setupFlightController() {

        flightControllerThread=new Thread(controller);
        flightControllerThread.start();
        //   handler.post(controller);

    }
    public void setArmer(ToggleButton armer) {
        this.armer = armer;
    }

    public ToggleButton getArmer() {
        return armer;
    }

    public void setControlSwitch(ToggleButton controlSwitch) {
        this.controlSwitch = controlSwitch;
    }

    public ToggleButton getControlSwitch() {
        return controlSwitch;
    }

    public void setAutoPilot(ToggleButton autoPilot) {
        this.autoPilot = autoPilot;
    }

    public ToggleButton getAutoPilot() {
        return autoPilot;
    }
}
