package com.example.administrator.earthquakehazard;

/**
 * Created by Administrator on 5/2/2017.
 */

public class EarthQuakeAdapterModel {

        private Double  magnitude;
        private String location;
        private int date_time;

        public EarthQuakeAdapterModel(Double magnitude, String location, int date_time) {
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

        public int getDate_time() {
            return date_time;
        }
}
