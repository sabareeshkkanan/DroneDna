package com.sabareesh.dronedna.Controller;

import android.util.Log;

import com.sabareesh.dronedna.deviceSensor.Gps;
import com.sabareesh.dronedna.helpers.GeometryHelper;

/**
 * Created by sabareesh on 8/29/15.
 */
public class LatitudeController extends Controller {
    private double latitude;
    private double desiredLatitude;

    public  LatitudeController(){
        pidController=new PIDController(100000,1000,10);
        pidController.enable();
        pidController.setInputRange(-90, 90);
        pidController.setOutputRange(-50,50);
      //  pidController.setBoundControl(false);
        pidController.setTolerance(0.0000001);

    }

    @Override
    public void execute() {
        initialize();
        process();
    }

    private void process() {
        compute();
    }

    private void compute() {
        pidController.setInput(latitude);
        double current=pidController.performPID()/100;

        acceleration=GeometryHelper.round(current);

   /*
        double pidDiff=current-previousPid;
        previousPid=current;
        pidDiff= GeometryHelper.round(pidDiff);
        if(pidDiff>0.5)
            return;
        else if (pidDiff<-0.5)
            return;
        accelerationComputation(pidDiff);*/
   //     Log.d("lattitudePID"," accuracy "+Gps.getInstance().getLocation().getAccuracy()+" active "+Gps.getInstance().getActiveSatellites()+" in "+latitude+" des "+desiredLatitude+" curr "+current+" prev "+previousPid+" diff "+pidDiff+" acc "+acceleration);

        Log.d("lattitudePID"," accuracy "+Gps.getInstance().getLocation().getAccuracy()+" active "+Gps.getInstance().getActiveSatellites()+" in "+latitude+" des "+desiredLatitude+" curr "+current+" acc "+acceleration);
    }

    protected void initialize() {

        latitude= Gps.getInstance().getSmoothLocation().getLatitude();

    }


    public double getDesiredLatitude() {
        return desiredLatitude;
    }

    public void setDesiredLatitude(double desiredLatitude) {
        this.desiredLatitude = desiredLatitude;
        pidController.setSetpoint(desiredLatitude);
    }
    protected void accelerationComputation(double pidDiff) {

    }
}
