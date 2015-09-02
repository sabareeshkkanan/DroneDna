package com.sabareesh.dronedna.Controller;
import android.util.Log;
import com.sabareesh.dronedna.FlightWarmup.ConfigurationManager;
import com.sabareesh.dronedna.models.HomeLocation;

/**
 * Created by sabareesh on 8/18/15.
 */
public class AltitudeController extends Controller {

    double currentAltitude;


    private final double idleThrottle;
    double desiredAltitude;
    public AltitudeController(){
       super();
        idleThrottle= (double) ConfigurationManager.getConfigManager().getDefaultValues().get("throttle");
        pidController=new PIDController(5,1,0.5);
        pidController.enable();
        pidController.setInputRange(0, 1100);
        pidController.setOutputRange(0, 100);
        pidController.setBoundControl(false);

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

    }

    private void compute() {
        pidController.setInput(currentAltitude);
        double es=pidController.performPID()/100;
        //Log.d("alt", "des " + desiredAltitude + " " + currentAltitude);
        double pidDiff=es-previousPid;
        accelerationComputation(pidDiff);

        //double tFinal=idleThrottle+acceleration;
       // Log.d("pidController", "" + es + " " + pidDiff +" "+tFinal);
        previousPid=es;

    }




    protected void initialize(){
        findCurrentAltitude();
    }
    private void findCurrentAltitude(){
        currentAltitude=HomeLocation.findAltitude(sensors.getPressure());
    }
    protected void accelerationComputation(double pidDiff) {
        if(acceleration>=0) {
            if (pidDiff > 0)
                acceleration += pidDiff;

            else
                acceleration = 0;
        }else {
            if(pidDiff<0)
                acceleration+=pidDiff;
            else
                acceleration=0;
        }
        if(acceleration>0.5)
            acceleration=0.5;
        else if(acceleration<-0.5)
            acceleration=-0.5;
    }
}
