package com.sabareesh.dronedna.Controller;

import android.util.Log;

import com.sabareesh.dronedna.FlightWarmup.ConfigurationManager;
import com.sabareesh.dronedna.helpers.GeometryHelper;
import com.sabareesh.dronedna.deviceSensor.Gps;
import com.sabareesh.dronedna.deviceSensor.SensorMan;
import com.sabareesh.dronedna.models.GeoLocation;
import com.sabareesh.dronedna.models.Point;

/**
 * Created by sabareesh on 8/30/15.
 */
public class SteeringController extends Controller {

    private GeoLocation desiredLocation;

    private double idleRoll;
    private double idlePitch;
    private Point finalControlPoint;
    Point controlCenter=new Point(0,0);


    private LatitudeController latitudeController;
    private LongitudeController longitudeController;
    Point acceleration;



    public GeoLocation getDesiredLocation() {
        return desiredLocation;
    }

    public void setDesiredLocation(GeoLocation desiredLocation) {
        this.desiredLocation = desiredLocation;
        latitudeController.setDesiredLatitude(desiredLocation.getLatitude());
        longitudeController.setDesiredLongitude(desiredLocation.getLongitude());
    }
    public SteeringController(){
        super();
        idlePitch=(double) ConfigurationManager.getConfigManager().getDefaultValues().get("elevator");
        idleRoll=(double) ConfigurationManager.getConfigManager().getDefaultValues().get("aileron");
        latitudeController=new LatitudeController();
        longitudeController=new LongitudeController();
    }
    @Override
    public void execute() {
        prepare();
        OrientationComputation();
        applySignals();
    }

    private void prepare() {
        latitudeController.execute();
        longitudeController.execute();
        acceleration=new Point(longitudeController.getAcceleration(),latitudeController.getAcceleration());

    }


    private void OrientationComputation() {

        double angle=GeometryHelper.angleBetween(Gps.getInstance().getSmoothLocation(), desiredLocation)-90;
        double compass=SensorMan.getSensor().getCompassHeading();
        double correctedOrientation=angle+compass;



        double accelerationRadius = GeometryHelper.distance(controlCenter, acceleration);
    //    accelerationRadius=0.5;
         finalControlPoint=GeometryHelper.computePoint(controlCenter,accelerationRadius,correctedOrientation);
        Log.d("steering","Angle "+angle+" compass "+compass+" correcetd "+correctedOrientation+" accRadius "+accelerationRadius+" final "+finalControlPoint.getX()+" Y "+finalControlPoint.getY());

    }
    private void applySignals(){

        setRoll(finalControlPoint.getX());
        setRoll(finalControlPoint.getY());
    }

    private void setRoll(double acceleration){
        double value=idleRoll+acceleration;
        signalModel.setPWMValue("aileron", value);
    }
    private void setPitch(double acceleration){
        double value=idlePitch+acceleration;
        signalModel.setPWMValue("elevator", value);
    }



}
