package com.sabareesh.dronedna;
import android.content.Context;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabareesh.dronedna.Controller.BaseStationSignalProcessor;
import com.sabareesh.dronedna.DisplayUtils.Disp;
import com.sabareesh.dronedna.DisplayUtils.ViewSetter;
import com.sabareesh.dronedna.FlightWarmup.ConfigurationManager;
import com.sabareesh.dronedna.Looper.Looper;
import com.sabareesh.dronedna.deviceSensor.Gps;
import com.sabareesh.dronedna.deviceSensor.SensorMan;
import com.sabareesh.hotSpot.HotSpotManager;
import com.sabareesh.serverlib.DroneServer;

import java.io.IOException;


import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    ViewSetter viewSetup;
    private HotSpotManager hotSpotManager;


    /**
     * Called when the activity is first created. Here we normally initialize
     * our GUI.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupConfig();
        setContentView(R.layout.main);
        setupDeviceSensor();
        setViews();
        setDispUtil();
       setupServer();
        setupBaseStationSignalProcessor();



    }

    private void setupBaseStationSignalProcessor() {
        BaseStationSignalProcessor processor=new BaseStationSignalProcessor();
        Thread bthres=new Thread(processor);
        bthres.start();
    }

    private void setupServer() {
     try {
         hotSpotManager=new HotSpotManager(this);
         WifiConfiguration configuration=new WifiConfiguration();
   configuration.SSID="DroneDna";
         configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
         configuration.preSharedKey="hydrogen";

         final ObjectMapper mapper=new ObjectMapper();
         hotSpotManager.setWifiApState(configuration, true);




     } catch (NoSuchMethodException e) {
         e.printStackTrace();
     }
     try {
            DroneServer server=new DroneServer(10000);
            Thread th=new Thread(server);
            th.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setupDeviceSensor() {
        Gps.setInstance((LocationManager) getSystemService(Context.LOCATION_SERVICE));
        SensorMan.setInstance(this);
        Log.d("ss", SensorManager.getAltitude(1000f,951f)+" "+SensorManager.getAltitude(1000f,950f));
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
        viewSetup=ViewSetter.getInstance();
        viewSetup.setLedButton((ToggleButton) findViewById(R.id.ledButton));
        viewSetup.getSeekBarMap().put("throttle", (SeekBar) findViewById(R.id.throttle));
        viewSetup.getSeekBarMap().put("aileron", (SeekBar) findViewById(R.id.aileron));
        viewSetup.getSeekBarMap().put("elevator",(SeekBar) findViewById(R.id.elevator));
        viewSetup.getSeekBarMap().put("rudder",(SeekBar) findViewById(R.id.rudder));
        viewSetup.setArmer((ToggleButton) findViewById(R.id.arm));
        viewSetup.setControlSwitch((ToggleButton) findViewById(R.id.U));
        viewSetup.setAutoPilot((ToggleButton)findViewById(R.id.autoPilot));
        viewSetup.setEvents();


    }







}
