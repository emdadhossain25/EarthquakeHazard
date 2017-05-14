package com.example.administrator.earthquakehazard;

import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthHazard extends AppCompatActivity {

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2017-05-02&minfelt=50&minmagnitude=5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth_hazard);
        AsyncTaskHelper asyncTaskHelper = new AsyncTaskHelper();
        asyncTaskHelper.execute(USGS_REQUEST_URL);
    }

    private class AsyncTaskHelper extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result =QueryUtils.fetchEarthquakeData(strings[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<EarthQuakeAdapterModel> city_array = QueryUtils.extractEarthQuakes(s);
        EarthQuakeAdapter arrayAdapter = new EarthQuakeAdapter(EarthHazard.this, city_array);
        ListView listView = (ListView) findViewById(R.id.listview1);
        listView.setAdapter(arrayAdapter);

        }
    }
}
