package com.sabareesh.dronedna.WebService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabareesh.dronedna.WebService.resultModels.GoogleAltitudeJson;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Created by sabareesh on 8/22/15.
 */
public class GeoAltitude {
    private static GeoAltitude geoAltitude;
    private GeoAltitude(){

    }
    public static GeoAltitude get(){
        return geoAltitude;
    }
    public static void create(){
        geoAltitude=new GeoAltitude();

    }
    public static double getAltitude(Double latitude, Double longitude) throws IOException {

        String url = "http://gisdata.usgs.gov/"
                + "xmlwebservices2/elevation_service.asmx/"
                + "getElevation?X_Value=" + String.valueOf(longitude)
                + "&Y_Value=" + String.valueOf(latitude)
                + "&Elevation_Units=METERS&Source_Layer=-1&Elevation_Only=true";
        url="https://maps.googleapis.com/maps/api/elevation/json?locations="+latitude+","+longitude+"&key=AIzaSyCT1-ZNteaLS-Ix-0OOxdJ7X8Pg-6EHeG4";
        String json=WebClient.get(url);
        ObjectMapper map=new ObjectMapper();
        GoogleAltitudeJson o=map.readValue(json, GoogleAltitudeJson.class);

        return  o.getResults().get(0).elevation;

    }


}
