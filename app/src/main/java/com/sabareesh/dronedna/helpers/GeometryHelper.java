package com.sabareesh.dronedna.helpers;

import com.sabareesh.commonlib.Point;
import com.sabareesh.commonlib.models.GeoLocation;


/**
 * Created by sabareesh on 8/30/15.
 */
public class GeometryHelper {
    public static Integer findQuadrant(GeoLocation referencePoint,GeoLocation point){

        if(referencePoint.getLatitude()<point.getLatitude())
        {      if(referencePoint.getLongitude()<point.getLongitude())
            return 1;
        else
            return 2;

        }
        else
        {
            if(referencePoint.getLongitude()<point.getLongitude())
                return 4;
            else
                return 3;
        }
    }

    /**
     *
     * @param angle range -180 to 180
     * @return
     */
    public static Integer findQuadrant(double angle){
        if(angle>90)
            return 2;
        else if(angle >0)
        return 1;
        else if(angle<-90)
            return 3;
        else if(angle<0)
            return 4;
        return 0;
        }
    public static int addQuadrant(int originalQuadrant,int error){
        originalQuadrant=originalQuadrant+error;
        if(originalQuadrant<0)
            originalQuadrant+=4;
        if(originalQuadrant>4)
            originalQuadrant=originalQuadrant%4;
        return originalQuadrant;
    }
    public static int calculateQuadrant(GeoLocation referencePoint,GeoLocation secondPoint,double angle){
        int quadrant=findQuadrant(referencePoint,secondPoint);
        quadrant=addQuadrant(quadrant,findQuadrant(angle));
        return quadrant;
    }
    public static Point computePoint(Point center, Point referencePoint, double degrees){

        double radius= distance(center, referencePoint);
        Point newPoint=computePoint(center, radius, degrees);
        return newPoint;
    }
    public static Point computePoint(Point center,double radius,double degrees){
        double radian=Math.toRadians(degrees);
        double x=(Math.cos(radian)*radius)+center.getX();
        double y=(Math.sin(radian)*radius)+center.getY();
        Point newPoint=new Point(round(x),round(y));


        return newPoint;

    }
    public static double distance(Point point1, Point point2){
        double v1=Math.pow(point1.getX()-point2.getX(),2);
                double v2=Math.pow(point1.getY()-point2.getY(),2);
        return Math.sqrt(v1+v2);
    }
    public static double angleBetween(Point p1,Point p2){


        return Math.toDegrees(Math.atan2(p2.getY() - p1.getY(), p2.getX() - p1.getX()));
    }
    public static double round(double input){
        return (double)Math.round(input * 100000) / 100000;
    }
    public static double round1D(double input){
        return (double)Math.round(input * 10) / 10;
    }
    public static double round2D(double input){
        return (double)Math.round(input * 100) / 100;
    }

    public static double angleBetween(GeoLocation currentLocation, GeoLocation desiredLocation) {
        return angleBetween(new Point(currentLocation.getLatitude(), currentLocation.getLongitude()), new Point(desiredLocation.getLatitude(), desiredLocation.getLongitude()));
    }
}
