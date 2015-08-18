package com.sabareesh.dronedna.hardware;

/**
 * Created by sabareesh on 8/15/15.
 */
public class PinInformation {
    public boolean isDutyCycleFlag() {
        return dutyCycleFlag;
    }

    public void setDutyCycleFlag(boolean dutyCycleFlag) {
        this.dutyCycleFlag = dutyCycleFlag;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public enum PinTypes {
        pwm,digital
    }
    private String name;
    private int pinNumber;
    private float dutyCycle;
    private int freqency;
    private PinTypes pinType;
    private boolean dutyCycleFlag;
    private int max;
    private int min;
    private int  range;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public float getDutyCycle() {
        return dutyCycle;
    }

    public void setDutyCycle(float dutyCycle) {
        this.dutyCycle = dutyCycle;
    }

    public int getFreqency() {
        return freqency;
    }

    public void setFreqency(int freqency) {
        this.freqency = freqency;
    }

    public PinTypes getPinType() {
        return pinType;
    }

    public void setPinType(PinTypes pinType) {
        this.pinType = pinType;
    }


}
