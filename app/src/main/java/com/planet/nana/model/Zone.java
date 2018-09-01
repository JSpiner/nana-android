package com.planet.nana.model;

import java.util.ArrayList;

public class Zone {

    private int id;
    private double latitude;
    private double longitude;
    private double radius;
    private ArrayList<Contact> contact;

    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getRadius() {
        return radius;
    }

    public ArrayList<Contact> getContact() {
        return new ArrayList<>(contact);
    }
}
