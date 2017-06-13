package com.sabareesh.serverlib;

import com.sabareesh.commonlib.models.ControlData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabareesh on 10/21/15.
 */
public class ReceivingStructure {
    private List<ControlData> signal;
    private String ack;

    public List<ControlData> getSignal() {
        return signal;
    }

    public void setSignal(List<ControlData> signal) {
        this.signal = signal;
    }

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }
    public ReceivingStructure(){
        signal=new ArrayList<>();
    }
    public void addSignal(ControlData data){
        signal.add(data);
    }
}
