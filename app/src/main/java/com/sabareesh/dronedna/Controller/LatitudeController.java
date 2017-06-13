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
        pidController=new PIDController(1000000,1000,0.5);
        pidController.enable();
        pidController.setInputRange(-90, 90);
      //  pidController.setOutputRange(-50,50);
        pidController.setBoundControl(false);
        pidController.setTolerance(0.00000001);

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

        double pidDiff=current-previousPid;

        pidDiff= GeometryHelper.round(pidDiff); previousPid=current;
        if(pidDiff>0.5)
            return;
        else if (pidDiff<-0.5)
            return;

        if(pidDiff==0)
            return;

        accelerationComputation(pidDiff);

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

        if(acceleration>=0) {
            if (pidDiff > 0)
                acceleration += pidDiff;

            else
            {
                acceleration = 0;
                acceleration+=pidDiff;
            }
        }else {
            if(pidDiff<0)
                acceleration+=pidDiff;
            else
            {
                acceleration=0;
                acceleration+=pidDiff;
            }
        }
        if(acceleration>0.5)
            acceleration=0.5;
        else if(acceleration<-0.5)
            acceleration=-0.5;
    }
}
