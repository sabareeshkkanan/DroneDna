package com.sabareesh.dronedna.hardware;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Objects;

/**
 * Created by sabareesh on 8/14/15.
 */
public class SignalModel {

    private Map<String,Object> controls;

    public SignalModel(){
    controls=new HashMap<>();
    }

    public void  setControls(Map<String,Object> controls){
        for (String key : controls.keySet()) {
            this.controls.put(key,controls.get(key));
        }
        //this.controls=controls;
    }
    public Map<String, Object> getControls() {
        return controls;
    }
    public void setPWMValue(String key,double percent){
        controls.put(key,percent);
    }

    public void setDigital(String key, boolean checked) {
        controls.put(key,checked);
    }
}
