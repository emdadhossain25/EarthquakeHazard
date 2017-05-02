package com.example.administrator.earthquakehazard;

/**
 * Created by Administrator on 5/2/2017.
 */

public class EarthQuakeAdapterModel {

        private int magnitude;
        private String location;
        private String date_time;

        public EarthQuakeAdapterModel(int magnitude, String location, String date_time) {
            this.magnitude = magnitude;
            this.location = location;
            this.date_time = date_time;

        }

        public int getMagnitude() {
            return magnitude;
        }

        public String getLocation() {
            return location;
        }

        public String getDate_time() {
            return date_time;
        }
}
