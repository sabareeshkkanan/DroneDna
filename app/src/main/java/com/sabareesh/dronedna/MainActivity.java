package com.sabareesh.dronedna;
import com.sabareesh.dronedna.DisplayUtils.Disp;
import com.sabareesh.dronedna.DisplayUtils.ViewSetter;
import com.sabareesh.dronedna.FlightWarmup.ConfigurationManager;
import com.sabareesh.dronedna.Looper.*;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.ToggleButton;


import java.io.IOException;

import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;

public class MainActivity extends IOIOActivity {


    ViewSetter viewSetup;
    /**
     * Called when the activity is first created. Here we normally initialize
     * our GUI.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setViews();
        setDispUtil();
        setupConfig();


    }

    private void setupConfig() {

        try {
            ConfigurationManager.setConfigurationManager(getAssets());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDispUtil() {
        Disp.setInstance(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void setViews(){
        viewSetup=new ViewSetter();
        viewSetup.setLedButton((ToggleButton) findViewById(R.id.ledButton));
        viewSetup.getSeekBarMap().put("throttle", (SeekBar) findViewById(R.id.throttle));
        viewSetup.getSeekBarMap().put("aileron", (SeekBar) findViewById(R.id.aileron));
        viewSetup.getSeekBarMap().put("elevator",(SeekBar) findViewById(R.id.elevator));
        viewSetup.getSeekBarMap().put("rudder",(SeekBar) findViewById(R.id.rudder));
        viewSetup.setArmer((ToggleButton) findViewById(R.id.arm));
        viewSetup.setControlSwitch((ToggleButton)findViewById(R.id.U));
        viewSetup.setEvents();


    }


    /**
     * A method to create our IOIO thread.
     *
     * @see ioio.lib.util.AbstractIOIOActivity#createIOIOThread()
     */
    @Override
    protected IOIOLooper createIOIOLooper() {

        return new Looper();
    }




}
