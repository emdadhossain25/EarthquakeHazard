package com.example.administrator.earthquakehazard;

/**
 * Created by Administrator on 5/2/2017.
 */

public class EarthQuakeModel {

    private Double magnitude;
    private String location;
    private Long date_time;
    private String url;

    public EarthQuakeModel(Double magnitude, String location, Long date_time, String url) {
        this.magnitude = magnitude;
        this.location = location;
        this.date_time = date_time;
        this.url = url;

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

    public String getUrl() {
        return url;
    }
}
