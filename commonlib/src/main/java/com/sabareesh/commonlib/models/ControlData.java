package com.sabareesh.commonlib.models;

import com.sabareesh.commonlib.Point;

/**
 * Created by sabareesh on 10/21/15.
 */
public class ControlData {
    private String name;
    private Double value;
    private String valueType;
    private String stringValue;
    private Boolean booleanValue;


    private Point pointValue;
    private GeoLocation geoLocation;


    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Point getPointValue() {
        return pointValue;
    }

    public void setPointValue(Point pointValue) {
        this.pointValue = pointValue;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }
}
