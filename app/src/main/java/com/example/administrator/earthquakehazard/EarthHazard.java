package com.example.administrator.earthquakehazard;

import android.database.DataSetObserver;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth_hazard);

        /**
         * TODO(2) adding a static arraylist
         */
        ArrayList<EarthQuakeAdapterModel> city_array = QueryUtils.extractEarthQuakes();

        ListView listView = (ListView) findViewById(R.id.listview1);
        /**
         * TODO(3) adding listView Adapter
         */
        EarthQuakeAdapter arrayAdapter = new EarthQuakeAdapter(EarthHazard.this, city_array);

        listView.setAdapter(arrayAdapter);
    }
}
