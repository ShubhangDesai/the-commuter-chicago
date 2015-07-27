package com.example.android.thecommuter.location;

/**
 * Created by Shubhang on 7/26/2015.
 */
public class SubwayLocation {
    double latitude;
    double longitude;

    public SubwayLocation(double latitude, double longitude) {
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
