package com.sabareesh.dronedna.Controller;

import com.sabareesh.commonlib.RequestQueue;
import com.sabareesh.commonlib.models.ControlData;
import com.sabareesh.dronedna.DisplayUtils.ViewSetter;
import com.sabareesh.dronedna.hardware.SignalModel;
import com.sabareesh.dronedna.hardware.SignalModelHandle;

/**
 * Created by sabareesh on 10/21/15.
 */
public class BaseStationSignalProcessor implements Runnable {
    private SignalModel signalModel;
    public BaseStationSignalProcessor(){
        signalModel= SignalModelHandle.getModel();
    }
    private void process(){
        while (RequestQueue.getInstance().getSize()>0){
            ControlData data=RequestQueue.getInstance().dequeue();
            switch (data.getValueType()){
                case "pwm":
                    signalModel.setPWMValue(data.getName(),data.getValue());
                    break;
                case "digital":
                    signalModel.setDigital(data.getName(),(data.getValue()==1?true:false));
                    break;
                case "custom":
                    handleCustom(data);
                    break;
            }
        }
    }

    private void handleCustom(ControlData data) {
        switch (data.getName()){
            case "autoPilot":
                handleAutoPilot(data);
                break;
        }
    }

    private void handleAutoPilot(ControlData data) {
        if(data.getValue()==1)
        ViewSetter.getInstance().autoPilotOnEvent();
        else
            ViewSetter.getInstance().autoPilotStopEvent();
    }

    @Override
    public void run() {
        while (true){
            try {
                process();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
