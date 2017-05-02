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
         * TODO(2) adding a fake arraylist
         */
        ArrayList<String> city_array = new ArrayList<>();
        city_array.add("Dhaka");
        city_array.add("Khulna");
        city_array.add("Rangpur");
        city_array.add("Rajshahi");
        city_array.add("Barisal");
        city_array.add("Mymensingh");
        city_array.add("Noakhali");
        city_array.add("Chittagong");
        city_array.add("Narayangonj");
        city_array.add("Naoga");

        ListView listView = (ListView) findViewById(R.id.listview1);
        /**
         * TODO(3) adding listView Adapter
         */
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EarthHazard.this, android.R.layout.simple_expandable_list_item_1, city_array);

        listView.setAdapter(arrayAdapter);
    }
}
