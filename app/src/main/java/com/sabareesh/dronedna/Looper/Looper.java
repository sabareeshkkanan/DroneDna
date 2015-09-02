package com.sabareesh.dronedna.Looper;

import com.sabareesh.dronedna.DisplayUtils.Disp;
import com.sabareesh.dronedna.FlightWarmup.ConfigurationManager;
import com.sabareesh.dronedna.hardware.PinManager;
import com.sabareesh.dronedna.hardware.SignalModel;
import com.sabareesh.dronedna.hardware.SignalModelHandle;


import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;

/**
 * Created by sabareesh on 8/14/15.
 */
/**
 * This is the thread on which all the IOIO activity happens. It will be execute
 * every time the application is resumed and aborted when it is paused. The
 * method setup() will be called right after a connection with the IOIO has
 * been established (which might happen several times!). Then, loop() will
 * be called repetitively until the IOIO gets disconnected.
 */
public class Looper extends BaseIOIOLooper {
    /** The on-board LED. */
    private PinManager pinManager;
    private SignalModel model;
    private ConfigurationManager configManager;
    public Looper(){

        configManager=ConfigurationManager.getConfigManager();
    }

    /**
     * Called every time a connection with IOIO has been established.
     * Typically used to open pins.
     *
     * @throws ConnectionLostException
     *             When IOIO connection is lost.
     *
     * @see ioio.lib.util.IOIOLooper
     */
    @Override
    protected void setup() throws ConnectionLostException {
        showVersions(ioio_, "IOIO connected!");
        pinManager=new PinManager(ioio_,configManager.getPinInformationList());

        model=SignalModelHandle.getModel();
        model.setControls(configManager.getDefaultValues());

    }

    /**
     * Called repetitively while the IOIO is connected.
     *
     * @throws ConnectionLostException
     *             When IOIO connection is lost.
     * @throws InterruptedException
     * 				When the IOIO thread has been interrupted.
     *
     * @see ioio.lib.util.IOIOLooper#loop()
     */
    @Override
    public void loop() throws ConnectionLostException, InterruptedException {
        pinManager.updatePin(model);
        Thread.sleep(100);
    }

    /**
     * Called when the IOIO is disconnected.
     *
     * @see ioio.lib.util.IOIOLooper#disconnected()
     */
    @Override
    public void disconnected() {

       Disp.getInstance().toast("IOIO disconnected");
    }

    /**
     * Called when the IOIO is connected, but has an incompatible firmware version.
     *
     * @see ioio.lib.util.IOIOLooper#incompatible(IOIO)
     */
    @Override
    public void incompatible() {
        showVersions(ioio_, "Incompatible firmware version!");
    }
    private void showVersions(IOIO ioio, String title) {
        Disp.getInstance().toast(String.format("%s\n" +
                        "IOIOLib: %s\n" +
                        "Application firmware: %s\n" +
                        "Bootloader firmware: %s\n" +
                        "Hardware: %s",
                title,
                ioio.getImplVersion(IOIO.VersionType.IOIOLIB_VER),
                ioio.getImplVersion(IOIO.VersionType.APP_FIRMWARE_VER),
                ioio.getImplVersion(IOIO.VersionType.BOOTLOADER_VER),
                ioio.getImplVersion(IOIO.VersionType.HARDWARE_VER)));
    }
}

