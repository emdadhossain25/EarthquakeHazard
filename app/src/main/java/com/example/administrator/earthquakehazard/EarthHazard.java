package com.example.administrator.earthquakehazard;

import android.content.Context;
import android.content.Loader;
import android.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

    // TODO(1): Implement LoaderManager Callback with Library imported-import android.content.AsyncTaskLoader;
public class EarthHazard extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<EarthQuakeAdapterModel>> {

    EarthQuakeAdapter arrayAdapter;
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2017-05-02&minfelt=50&minmagnitude=3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth_hazard);
        android.app.LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, EarthHazard.this);
    }

    // TODO(3): Implement the methods onCreateLoader
    @Override
    public Loader<List<EarthQuakeAdapterModel>> onCreateLoader(int i, Bundle bundle) {
        return new EarthquakeLoader(this,USGS_REQUEST_URL);
    }

    // TODO(4): Implement the methods onLoadFinished method - called in main thread
    @Override
    public void onLoadFinished(Loader<List<EarthQuakeAdapterModel>> loader, List<EarthQuakeAdapterModel> data) {
        if (data != null && !data.isEmpty()) {
            // TODO(4.a): checking and setting the adapter;
            arrayAdapter = new EarthQuakeAdapter(EarthHazard.this, (ArrayList<EarthQuakeAdapterModel>) data);
            ListView listView = (ListView) findViewById(R.id.listview1);
            listView.setAdapter(arrayAdapter);
        }

    }

    // TODO(5): Implement the methods onCreateLoader
    @Override
    public void onLoaderReset(Loader<List<EarthQuakeAdapterModel>> loader) {
    // TODO(5.a): reseting the adapter
        arrayAdapter.clear();
    }


        /**
         * TODO(2)- creating an earthquake Loader using the import android.content.Loader and  import android.content.AsyncTaskLoader;
         */
    private static class EarthquakeLoader extends AsyncTaskLoader<List<EarthQuakeAdapterModel>> {

        String result;

     // TODO(2.a)- constructor for passing the url in loader
        public EarthquakeLoader(Context context, String url) {
            super(context);
            result = url;

        }

     // TODO(2.b)- over riding onStartLoading calling forceLoad()
        @Override
        protected void onStartLoading() {
            forceLoad();
        }

     // TODO(2.c)- over riding background thread works in loadinBackground method
        @Override
        public List<EarthQuakeAdapterModel> loadInBackground() {
            if (result == null) {
                return null;
            }

            // TODO(2.d)- fetching the JsonResponse - using requestURL, makeHttpCOnnection, readInputStream, addinng bufferedRead and then adding in the model class
            result = QueryUtils.fetchEarthquakeData(result);
            ArrayList<EarthQuakeAdapterModel> city_array = QueryUtils.extractEarthQuakes(result);
            return city_array;
        }
    }

}