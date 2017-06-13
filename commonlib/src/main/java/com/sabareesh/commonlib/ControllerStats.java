package com.sabareesh.commonlib;

/**
 * Created by sabareesh on 9/2/15.
 */
public class ControllerStats {
    private static ControllerStats ourInstance = new ControllerStats();
    private int activeSat;
    private int visibleSat;
    private float gpsAccuracy;
    private double currentPid;
    private double previousPid;
    private double pidDiff;

    public static ControllerStats getInstance() {
        return ourInstance;
    }

    private ControllerStats() {
    }

    private double latitudeAcceleration;//
    private double longitudeAcceleration;//
    private double altitudeAcceleration;//
    private double correctedAngle;
    private Point finalPoint;
    private double accelerationRadius;
    private Point currentLocation;//
    private Point desiredLocation;//
    private double currentAltitude;//
    private double desiredAltitude;//
    private double compassHeading;

    public static ControllerStats getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(ControllerStats ourInstance) {
        ControllerStats.ourInstance = ourInstance;
    }

    public double getLatitudeAcceleration() {
        return latitudeAcceleration;
    }

    public void setLatitudeAcceleration(double latitudeAcceleration) {
        this.latitudeAcceleration = latitudeAcceleration;
    }

    public double getLongitudeAcceleration() {
        return longitudeAcceleration;
    }

    public void setLongitudeAcceleration(double longitudeAcceleration) {
        this.longitudeAcceleration = longitudeAcceleration;
    }

    public double getAltitudeAcceleration() {
        return altitudeAcceleration;
    }

    public void setAltitudeAcceleration(double altitudeAcceleration) {
        this.altitudeAcceleration = altitudeAcceleration;
    }

    public double getCorrectedAngle() {
        return correctedAngle;
    }

    public void setCorrectedAngle(double correctedAngle) {
        this.correctedAngle = correctedAngle;
    }

    public Point getFinalPoint() {
        return finalPoint;
    }

    public void setFinalPoint(Point finalPoint) {
        this.finalPoint = finalPoint;
    }

    public double getAccelerationRadius() {
        return accelerationRadius;
    }

    public void setAccelerationRadius(double accelerationRadius) {
        this.accelerationRadius = accelerationRadius;
    }

    public Point getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Point currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Point getDesiredLocation() {
        return desiredLocation;
    }

    public void setDesiredLocation(Point desiredLocation) {
        this.desiredLocation = desiredLocation;
    }

    public double getCurrentAltitude() {
        return currentAltitude;
    }

    public void setCurrentAltitude(double currentAltitude) {
        this.currentAltitude = currentAltitude;
    }

    public double getDesiredAltitude() {
        return desiredAltitude;
    }

    public void setDesiredAltitude(double desiredAltitude) {
        this.desiredAltitude = desiredAltitude;
    }

    public double getCompassHeading() {
        return compassHeading;
    }

    public void setCompassHeading(double compassHeading) {
        this.compassHeading = compassHeading;
    }

    public void setActiveSat(int activeSat) {
        this.activeSat = activeSat;
    }

    public int getActiveSat() {
        return activeSat;
    }

    public void setVisibleSat(int visibleSat) {
        this.visibleSat = visibleSat;
    }

    public int getVisibleSat() {
        return visibleSat;
    }

    public void setGpsAccuracy(float gpsAccuracy) {
        this.gpsAccuracy = gpsAccuracy;
    }

    public float getGpsAccuracy() {
        return gpsAccuracy;
    }

    public void setCurrentPid(double currentPid) {
        this.currentPid = currentPid;
    }

    public double getCurrentPid() {
        return currentPid;
    }

    public void setPreviousPid(double previousPid) {
        this.previousPid = previousPid;
    }

    public double getPreviousPid() {
        return previousPid;
    }

    public void setPidDiff(double pidDiff) {
        this.pidDiff = pidDiff;
    }
    public double getPidDiff(){
        return pidDiff;
    }
}
