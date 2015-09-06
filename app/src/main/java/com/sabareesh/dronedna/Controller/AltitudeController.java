package com.sabareesh.dronedna.Controller;
import android.util.Log;

import com.sabareesh.commonlib.ControllerStats;
import com.sabareesh.dronedna.FlightWarmup.ConfigurationManager;
import com.sabareesh.dronedna.helpers.GeometryHelper;
import com.sabareesh.dronedna.models.HomeLocation;

/**
 * Created by sabareesh on 8/18/15.
 */
public class AltitudeController extends Controller {

    private final ControllerStats stats;
    double currentAltitude;


    private final double idleThrottle;
    double desiredAltitude;
    public AltitudeController(){
       super();
        double config= (double) ConfigurationManager.getConfigManager().getDefaultValues().get("throttle");
        idleThrottle=config;
        pidController=new PIDController(1,0.5,0.1);
        pidController.enable();
        pidController.setInputRange(0, 1100);
        pidController.setOutputRange(0, 100);
        pidController.setBoundControl(false);
        pidController.setTolerance(0.1);
       stats=ControllerStats.getInstance();

    }
    public void setDesiredAltitude(double desiredAltitude) {
        this.desiredAltitude = desiredAltitude;
        pidController.setSetpoint(desiredAltitude);
    }


    @Override
    public void execute() {
        initialize();
        process();
    }
    private void process(){
        compute();
        double tFinal =idleThrottle+acceleration;
        signalModel.setPWMValue("throttle", tFinal);

        stats.setCurrentAltitude(currentAltitude);
        stats.setDesiredAltitude(desiredAltitude);
        stats.setAltitudeAcceleration(tFinal);

    }

    private void compute() {
        pidController.setInput(currentAltitude);
        double es=pidController.performPID()/100;
        stats.setCurrentPid(es);
        stats.setPreviousPid(previousPid);

        //Log.d("alt", "des " + desiredAltitude + " " + currentAltitude);
        double pidDiff=es-previousPid;
        previousPid=es;
        if(pidDiff>0.2)
            return;
        accelerationComputation(pidDiff);
        stats.setPidDiff(pidDiff);
        //double tFinal=idleThrottle+acceleration;
       // Log.d("pidController", "" + es + " " + pidDiff +" "+tFinal);


    }




    protected void initialize(){
        findCurrentAltitude();
    }
    private void findCurrentAltitude(){
        currentAltitude= GeometryHelper.round1D(HomeLocation.findAltitude(sensors.getPressure()));
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
        if(acceleration>0.2)
            acceleration=0.2;
        else if(acceleration<-0.2)
            acceleration=-0.2;
    }
}
