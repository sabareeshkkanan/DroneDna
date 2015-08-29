package com.sabareesh.dronedna.FlightWarmup;

import android.content.res.AssetManager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabareesh.dronedna.hardware.PinInformation;
import com.sabareesh.dronedna.hardware.SignalModelHandle;
import com.sabareesh.dronedna.models.FlightWarmSetting;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sabareesh on 8/16/15.
 */
public class ConfigurationManager {
    private static ConfigurationManager _manager;
   public ObjectMapper mapper=new ObjectMapper();
    AssetManager manager;
    List<PinInformation> pinInformationList;
    Map<String,Object> defaultValues;
    private Map<String,Object>  armingSetting;
    private FlightWarmSetting flightWarmSetting;

    public Map<String, Double> getControlModes() {
        return controlModes;
    }

    public void setControlModes(Map<String, Double> controlModes) {
        this.controlModes = controlModes;
    }

    private Map<String, Double> controlModes;

    public static ConfigurationManager get_manager() {
        return _manager;
    }

    public static void set_manager(ConfigurationManager _manager) {
        ConfigurationManager._manager = _manager;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public AssetManager getManager() {
        return manager;
    }

    public void setManager(AssetManager manager) {
        this.manager = manager;
    }

    public List<PinInformation> getPinInformationList() {
        return pinInformationList;
    }

    public void setPinInformationList(List<PinInformation> pinInformationList) {
        this.pinInformationList = pinInformationList;
    }

    public Map<String, Object> getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(Map<String, Object> defaultValues) {
        this.defaultValues = defaultValues;
    }

    public Map<String, Object> getArmingSetting() {
        return armingSetting;
    }

    public void setArmingSetting(Map<String, Object> armingSetting) {
        this.armingSetting = armingSetting;
    }

    public static ConfigurationManager getConfigManager(){
        return _manager;
    }
    public static void setConfigurationManager(AssetManager manager) throws IOException {
        _manager=new ConfigurationManager(manager);

    }

    private ConfigurationManager(AssetManager manager) throws IOException {
       this.manager=manager;
       loadConfigs();
        SignalModelHandle.CreateModel();
   }
    private void loadConfigs() throws IOException {
        String pinConfig=loadJSONFromAsset("pinConfig.json");
        String pinDefaultValues=loadJSONFromAsset("IdleSetting.json");
        String armingConfig=loadJSONFromAsset("ArmingSetting.json");
        String controlModesConfig=loadJSONFromAsset("controlModes.json");
        pinInformationList=mapper.readValue(pinConfig, mapper.getTypeFactory().constructCollectionType(List.class,PinInformation.class));
        defaultValues=mapper.readValue(pinDefaultValues, new TypeReference<HashMap<String,Object>>() {});
        armingSetting=mapper.readValue(armingConfig, new TypeReference<HashMap<String,Object>>() {});
        controlModes=mapper.readValue(controlModesConfig, new TypeReference<HashMap<String,Double>>() {});
        flightWarmSetting=mapper.readValue(loadJSONFromAsset("FlightWarmSetting.json"), FlightWarmSetting.class);

    }
    private void setSignalModel(){

    }
    private String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = manager.open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public FlightWarmSetting getFlightWarmSetting() {
        return flightWarmSetting;
    }
}
