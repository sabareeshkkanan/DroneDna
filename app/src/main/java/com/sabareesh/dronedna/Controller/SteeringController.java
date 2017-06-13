package com.sabareesh.dronedna.Controller;

import com.sabareesh.commonlib.ControllerStats;
import com.sabareesh.commonlib.Point;
import com.sabareesh.dronedna.FlightWarmup.ConfigurationManager;
import com.sabareesh.dronedna.helpers.GeometryHelper;
import com.sabareesh.dronedna.deviceSensor.Gps;
import com.sabareesh.dronedna.deviceSensor.SensorMan;
import com.sabareesh.commonlib.models.GeoLocation;

/**
 * Created by sabareesh on 8/30/15.
 */
public class SteeringController extends Controller {

    private final ControllerStats stats;
    private GeoLocation desiredLocation;
    private AltitudeController altitudeController;

    private double idleRoll;
    private double idlePitch;
    private double idleThrottle;
private boolean altitudeFlag;


    private Point finalControlPoint;
    private Point controlCenter=new Point(0,0);


    private LatitudeController latitudeController;
    private LongitudeController longitudeController;
private     Point acceleration;
    private double altitudeAcceleration;
    private double controllerOrientation;


    public GeoLocation getDesiredLocation() {
        return desiredLocation;
    }

    public void setDesiredLocation(GeoLocation desiredLocation) {
        this.desiredLocation = desiredLocation;
        altitudeFlag=true;
        latitudeController.setDesiredLatitude(desiredLocation.getLatitude());
        longitudeController.setDesiredLongitude(desiredLocation.getLongitude());
        altitudeController.setDesiredAltitude(desiredLocation.getAltitude());
        controllerOrientation=-90.0;
    }
    public SteeringController(){
        super();
        fetchIdlingValues();


        latitudeController=new LatitudeController();
        longitudeController=new LongitudeController();
        altitudeController=new AltitudeController();


        stats=ControllerStats.getInstance();
    }

    private void fetchIdlingValues() {
        idlePitch=(double) ConfigurationManager.getConfigManager().getDefaultValues().get("elevator");
        idleRoll=(double) ConfigurationManager.getConfigManager().getDefaultValues().get("aileron");
        idleThrottle=(double) ConfigurationManager.getConfigManager().getDefaultValues().get("throttle");
    }

    @Override
    public void execute() {
        prepare();
        OrientationComputation();
        applySignals();
    }

    private void prepare() {

        if(altitudeFlag){
            double diff=altitudeController.getCurrentAltitude()-desiredLocation.getAltitude();
            if(diff<0)
                diff*=-1;
            if(diff<0.2)
                altitudeFlag=false;
        }
        if(!altitudeFlag) {
            latitudeController.execute();
            longitudeController.execute();
        }
        altitudeController.execute();
        altitudeAcceleration=altitudeController.getAcceleration();
        acceleration=new Point(GeometryHelper.round2D(longitudeController.getAcceleration()),GeometryHelper.round2D(latitudeController.getAcceleration()));

    }


    private void OrientationComputation() {
        GeoLocation currentLocation=Gps.getInstance().getSmoothLocation();
      //  double angle=GeometryHelper.angleBetween(currentLocation, desiredLocation)+180;
        double angle=GeometryHelper.angleBetween(controlCenter,acceleration);
        double compass=SensorMan.getSensor().getCompassHeading();
        compass+=controllerOrientation;
        double correctedOrientation=angle+compass;



        double accelerationRadius = GeometryHelper.distance(controlCenter, acceleration);
    //    accelerationRadius=0.5;
         finalControlPoint=GeometryHelper.computePoint(controlCenter,accelerationRadius,correctedOrientation);



        stats.setAccelerationRadius(accelerationRadius);
        stats.setCurrentLocation(currentLocation.getPoint());
        stats.setDesiredLocation(desiredLocation.getPoint());
        stats.setCorrectedAngle(correctedOrientation);
        stats.setFinalPoint(finalControlPoint);
        stats.setCompassHeading(compass);
        stats.setLatitudeAcceleration(acceleration.getY());
        stats.setLongitudeAcceleration(acceleration.getX());
        stats.setAltitudeAcceleration(altitudeAcceleration);








        //Log.d("steering","Angle "+angle+" compass "+compass+" correcetd "+correctedOrientation+" accRadius "+accelerationRadius+" final "+finalControlPoint.getX()+" Y "+finalControlPoint.getY());

    }
    private void applySignals(){
        if (!altitudeFlag) {
            setPitch(finalControlPoint.getY());
            setRoll(finalControlPoint.getX());}
        setThrottle(altitudeAcceleration);

    }

    private void setThrottle(double altitudeAcceleration) {
        double tFinal =idleThrottle+altitudeAcceleration;
        signalModel.setPWMValue("throttle", tFinal);

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
