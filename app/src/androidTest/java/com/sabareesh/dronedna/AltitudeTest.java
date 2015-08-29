package com.sabareesh.dronedna;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.sabareesh.dronedna.WebService.GeoAltitude;

import java.io.IOException;

/**
 * Created by sabareesh on 8/22/15.
 */
public class AltitudeTest  extends InstrumentationTestCase {
    public void test_altitude() throws IOException {

        Log.d("Altitude",GeoAltitude.getAltitude(39.7391536,-104.9847034)+"");
    }
}
