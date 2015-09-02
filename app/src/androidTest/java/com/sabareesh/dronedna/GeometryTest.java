package com.sabareesh.dronedna;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.sabareesh.dronedna.helpers.GeometryHelper;
import com.sabareesh.dronedna.models.Point;

/**
 * Created by sabareesh on 8/31/15.
 */
public class GeometryTest extends InstrumentationTestCase {
    public void testRadius(){
        Point p1=new Point(0,0);
        Point p2=new Point(0,1);
       Log.d("distance", "" + GeometryHelper.distance(p1, p2));
    }
    public void testRotation(){
        Point center=new Point(0,0);
        Point p1=new Point(1,1);
       Point p2= GeometryHelper.computePoint(center, p1, -45);
        Log.d("rotation","X "+p2.getX()+ " Y "+p2.getY());
        p2=GeometryHelper.computePoint(center, p1, 90);
        Log.d("rotation","X "+p2.getX()+ " Y "+p2.getY());
        p2=GeometryHelper.computePoint(center, p1, 135);
        Log.d("rotation","X "+p2.getX()+ " Y "+p2.getY());
        p2=GeometryHelper.computePoint(center, p1, 180);
        Log.d("rotation","X "+p2.getX()+ " Y "+p2.getY());
        p2=GeometryHelper.computePoint(center, p1, 225);
        Log.d("rotation","X "+p2.getX()+ " Y "+p2.getY());
        p2=GeometryHelper.computePoint(center, p1, 270);
        Log.d("rotation","X "+p2.getX()+ " Y "+p2.getY());
        p2=GeometryHelper.computePoint(center, p1, 315);
        Log.d("rotation","X "+p2.getX()+ " Y "+p2.getY());
        p2=GeometryHelper.computePoint(center, p1, 360);
        Log.d("rotation","X "+p2.getX()+ " Y "+p2.getY());


    }
    public void testAngle(){
        Point p1=new Point( 33.143181,-117.084877);
        Point p2=new Point(  33.143717,-117.092049);
        Log.d("angle",""+GeometryHelper.angleBetween(p1,p2));
    }
    public void testTrig(){
        double radian=Math.toRadians(90);
        Log.d("trig",radian+" "+Math.cos(radian)+"");
    }
}
