package com.recursionlabs.thecommuter.location;

/**
 * Created by Shubhang on 7/26/2015.
 */
public class Loc {
    double latitude;
    double longitude;

    public Loc(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
