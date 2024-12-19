package com.example.carpoolingapp.models;

import java.io.Serializable;

public class RidePost implements Serializable {
    private String startLocation;
    private String destination;
    private String departureTime;
    private String carModel;
    private double cost;


    public RidePost(String startLocation, String destination, String departureTime, String carModel, double cost) {
        this.startLocation = startLocation;
        this.destination = destination;
        this.departureTime = departureTime;
        this.carModel = carModel;
        this.cost = cost;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getCarModel() {
        return carModel;
    }

    public double getCost() {
        return cost;
    }
}

