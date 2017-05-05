package com.example.administrator.earthquakehazard;

/**
 * Created by Administrator on 5/2/2017.
 */

public class EarthQuakeAdapterModel {

    private Double magnitude;
    private String location;
    private Long date_time;

    public EarthQuakeAdapterModel(Double magnitude, String location, Long date_time) {
        this.magnitude = magnitude;
        this.location = location;
        this.date_time = date_time;

    }

    public Double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public Long getDate_time() {
        return date_time;
    }

}
