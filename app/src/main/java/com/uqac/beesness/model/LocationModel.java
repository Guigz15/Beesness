package com.uqac.beesness.model;

import androidx.annotation.NonNull;

public class LocationModel {
    private double latitude;
    private double longitude;

    public LocationModel() {}

    public LocationModel(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @NonNull
    @Override
    public String toString() {
        return latitude + ", " + longitude;
    }
}
