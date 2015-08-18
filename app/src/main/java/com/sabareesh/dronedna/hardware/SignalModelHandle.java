package com.sabareesh.dronedna.hardware;

/**
 * Created by sabareesh on 8/14/15.
 */
public class SignalModelHandle {
    private static SignalModel model;

    public static SignalModel getModel() {
        return model;
    }

    public static SignalModel CreateModel() {
        model=new SignalModel();
        return model;
    }
}
