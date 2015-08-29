package com.sabareesh.dronedna.WebService.resultModels;

import android.location.Location;

import java.util.List;

/**
 * Created by sabareesh on 8/22/15.
 */
public class GoogleAltitudeJson {
    private String error_message;
    private List<ElevationPoint> results;
    private String status;
    public GoogleAltitudeJson(){

    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public List<ElevationPoint> getResults() {
        return results;
    }

    public void setResults(List<ElevationPoint> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
