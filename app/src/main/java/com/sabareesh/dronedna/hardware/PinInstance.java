package com.sabareesh.dronedna.hardware;



import ioio.lib.api.Closeable;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;

/**
 * Created by sabareesh on 8/16/15.
 */
public class PinInstance {
    PinInformation information;
    Closeable instance;
    public PinInstance(IOIO ioio_,PinInformation pinInfo) throws ConnectionLostException {
        information=pinInfo;
        switch (pinInfo.getPinType()){
            case pwm:
                addPwmPin(ioio_);
                break;
            case digital:
                addDigitalPin(ioio_);
                break;
        }
    }

    public PinInformation getInformation() {
        return information;
    }

    public Closeable getInstance() {
        return instance;
    }

    private void addDigitalPin(IOIO ioio_) throws ConnectionLostException {
        instance= (Closeable) ioio_.openDigitalOutput(information.getPinNumber());
    }

    private void addPwmPin(IOIO ioio_) throws ConnectionLostException {
        PwmOutput pin=  ioio_.openPwmOutput(information.getPinNumber(),information.getFreqency());
        if(information.isDutyCycleFlag())
            pin.setDutyCycle(information.getDutyCycle());
        information.setRange(information.getMax()-information.getMin());
        instance= (Closeable) pin;

    }

    public void updateValue(Object value) throws ConnectionLostException {
        switch (information.getPinType()){
            case pwm:

                setPwmPinValue((double)value);
                break;
            case digital:
                setDigitalValue((boolean)value);
                break;

        }
    }
    private void setPwmPinValue(double value) throws ConnectionLostException {
        PwmOutput pin=(PwmOutput)instance;
        int processedValue = (int) ((value*information.getRange())+information.getMin());
        if(processedValue<=information.getMax())
        pin.setPulseWidth(processedValue);

    }
    private void setDigitalValue(boolean value) throws ConnectionLostException {
        DigitalOutput pin=(DigitalOutput)instance;
        pin.write(value);
    }


}
