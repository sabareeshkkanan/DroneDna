package com.sabareesh.dronedna;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sabareesh.dronedna.FlightWarmup.ConfigurationManager;
import com.sabareesh.dronedna.hardware.PinInformation;

import junit.framework.Assert;



/**
 * Created by sabareesh on 8/16/15.
 */
public class ConfigManagerTest extends InstrumentationTestCase {



    public void test() throws JsonProcessingException {
        ConfigurationManager tt=ConfigurationManager.get_manager();
        PinInformation info=new PinInformation();
        info.setName("throttle");
        info.setDutyCycle(0.5f);
        info.setFreqency(50);
        info.setPinNumber(14);
        info.setPinType(PinInformation.PinTypes.pwm);
        Log.d("warn",tt.mapper.writeValueAsString(info));
        Assert.assertEquals(true,true);
    }
}
