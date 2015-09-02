package com.sabareesh.dronedna.deviceSensor;

import android.content.Context;
import android.hardware.GeomagneticField;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;

import java.math.BigDecimal;


public class SensorMan implements SensorEventListener {
	private SensorManager mSensorManager;
	Context mContext;
	float[] gravity = new float[3];
	float[] geomag = new float[3];
    float[] orientVals = new float[3];
    private double compassHeading;
    private double compassSmoothing =10;
    private double pressureSmoothing=5;
    public double getCompassHeading() {
        return compassHeading;
    }

    public float[] getGravity() {
        return gravity;
    }

    public void setGravity(float[] gravity) {
        this.gravity = gravity;
    }

    public float[] getGeomag() {
        return geomag;
    }

    public void setGeomag(float[] geomag) {
        this.geomag = geomag;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }
    private double declination;
    private float pressure;
	static SensorMan sensorMan;
	public static void setInstance(Context context){
sensorMan =new SensorMan(context);
	}
    public static SensorMan getSensor(){
        return sensorMan;
    }

	private SensorMan(Context context)
	{


		mContext=context;
		 mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
int updateSpeed=SensorManager.SENSOR_DELAY_GAME;
		 mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_GRAVITY),                updateSpeed);

		 mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_MAGNETIC_FIELD),
                 updateSpeed);
		mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_PRESSURE),updateSpeed);
        Location loc=Gps.getInstance().getLocation();
        GeomagneticField field=new GeomagneticField((float)loc.getLatitude(),(float)loc.getLongitude(),(float)loc.getAltitude(),System.currentTimeMillis() / 1000L);
	declination=field.getDeclination();
	}
	
	@Override
	public void onAccuracyChanged(android.hardware.Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		// if (event.SensorMan.getType() == SensorMan.TYPE_GYROSCOPE)
		float[] inR = new float[16];
		float[] I = new float[16];

		    // Gets the value of the SensorMan that has been changed
		    switch (event.sensor.getType()) {  
		      
		        case android.hardware.Sensor.TYPE_MAGNETIC_FIELD:
		            geomag = event.values.clone();
                    boolean success = SensorManager.getRotationMatrix(inR, I,
                            gravity, geomag);
                    if(success){
                        SensorManager.getOrientation(inR, orientVals);

                        updateCompassHeading(round(Math.toDegrees(orientVals[0]),1));


                    }

		            break; 
		            case android.hardware.Sensor.TYPE_GRAVITY:
		        	  gravity = event.values.clone();
			            break;
				case android.hardware.Sensor.TYPE_PRESSURE:

                    updatePressure(event.values[0]);

					break;
		    }

	}

    private void updateCompassHeading(double newValue) {
       newValue=newValue+declination;
        //convert to 0 to 360 from -180 to 180
        newValue=(newValue+360)%360;
        double difference=newValue - compassHeading;
        if(difference<50&&difference>-50)
        compassHeading += difference / compassSmoothing;
        else
            compassHeading=newValue;
    }
    private void updatePressure(double sensorValue){
        pressure+=(sensorValue-pressure)/pressureSmoothing;
    }

    public static float round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
        return bd.floatValue();
    }
}
