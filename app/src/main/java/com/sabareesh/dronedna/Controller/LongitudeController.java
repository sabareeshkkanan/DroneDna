package com.sabareesh.dronedna.Controller;

import com.sabareesh.dronedna.deviceSensor.Gps;
import com.sabareesh.dronedna.helpers.GeometryHelper;

/**
 * Created by sabareesh on 8/29/15.
 */
public class LongitudeController extends Controller {
    private double longitude;
    private double desiredLongitude;
    public LongitudeController(){
        pidController=new PIDController(100000,1000,10);
        pidController.enable();
        pidController.setInputRange(-180, 180);
        pidController.setOutputRange(-50, 50);
       // pidController.setBoundControl(false);
        pidController.setTolerance(0.0000001);

    }

    @Override
    public void execute() {
        initialize();
        process();
    }

    private void process() {
        computeDesiredAccelerationPid();

    }


    private void computeDesiredAccelerationPid(){
        pidController.setInput(longitude);
        double current=pidController.performPID()/100;
        acceleration=GeometryHelper.round(current);
 /*
        double pidDiff=current-previousPid;
        pidDiff= GeometryHelper.round(pidDiff); previousPid=current;
        if(pidDiff>0.5)
            return;
        else if (pidDiff<-0.5)
            return;
        accelerationComputation(pidDiff);
   */

    }

    private void realize(){

    }




    protected void initialize() {
        longitude= Gps.getInstance().getSmoothLocation().getLongitude();

    }

    public double getDesiredLongitude() {
        return desiredLongitude;
    }

    public void setDesiredLongitude(double desiredLongitude) {
        pidController.setSetpoint(desiredLongitude);
        this.desiredLongitude = desiredLongitude;
    }
    protected void accelerationComputation(double pidDiff) {

    }
}
