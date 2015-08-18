package com.sabareesh.dronedna.hardware;

import java.io.Closeable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;

/**
 * Created by sabareesh on 8/16/15.
 */
public class PinManager {
    public Map<String,PinInstance> pinInstances;

    private IOIO ioio_;

    public PinManager(IOIO ioio, List<PinInformation> pins) throws ConnectionLostException {
        ioio_=ioio;
        pinInstances=new HashMap<>();

        setupPins(pins);
    }


    private void setupPins(List<PinInformation> pins) throws ConnectionLostException {

        for (PinInformation pin : pins) {
        pinInstances.put(pin.getName(),new PinInstance(ioio_,pin));
        }

    }


    public void updatePin(SignalModel model) throws ConnectionLostException {
        for (String key  : model.getControls().keySet()) {
            if(!pinInstances.containsKey(key))
                continue;
           PinInstance inst= pinInstances.get(key);
            inst.updateValue(model.getControls().get(key));
        }
    }
}
