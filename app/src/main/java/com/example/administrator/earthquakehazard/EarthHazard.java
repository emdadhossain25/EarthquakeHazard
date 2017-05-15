package com.example.administrator.earthquakehazard;

import android.content.Context;
import android.content.Loader;
import android.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// TODO(1): Implement LoaderManager Callback with Library imported-import android.content.AsyncTaskLoader;
public class EarthHazard extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<EarthQuakeAdapterModel>> {

    String LOG_TAG = "Earthquake app";
    ListView listView;
    TextView textView;
    EarthQuakeAdapter arrayAdapter;
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2017-05-02&minfelt=50&minmagnitude=3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth_hazard);
        listView = (ListView) findViewById(R.id.listview1);
        textView = (TextView) findViewById(R.id.tv_emptyview);
        listView.setEmptyView(textView);
        android.app.LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, EarthHazard.this);
        Log.d(LOG_TAG, "initLoader");
    }

    // TODO(3): Implement the methods onCreateLoader
    @Override
    public Loader<List<EarthQuakeAdapterModel>> onCreateLoader(int i, Bundle bundle) {
        Log.d(LOG_TAG, "onCreateLoader");
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    // TODO(4): Implement the methods onLoadFinished method - called in main thread
    @Override
    public void onLoadFinished(Loader<List<EarthQuakeAdapterModel>> loader, List<EarthQuakeAdapterModel> data) {
        textView.setText("No EarthQuake data found");

        if (data != null && !data.isEmpty()) {
            // TODO(4.a): checking and setting the adapter;
            arrayAdapter = new EarthQuakeAdapter(EarthHazard.this, (ArrayList<EarthQuakeAdapterModel>) data);
            listView.setAdapter(arrayAdapter);
            Log.d(LOG_TAG, "onLoadfinished");

        }

    }

    // TODO(5): Implement the methods onCreateLoader
    @Override
    public void onLoaderReset(Loader<List<EarthQuakeAdapterModel>> loader) {
        // TODO(5.a): reseting the adapter
        arrayAdapter.clear();
        Log.d(LOG_TAG, "onLoaderReset");
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
            Log.d("EarthQuakeLoaderCLass", "onStartLoading");
            forceLoad();
        }

        // TODO(2.c)- over riding background thread works in loadinBackground method
        @Override
        public List<EarthQuakeAdapterModel> loadInBackground() {
            Log.d("EarthQuakeLoaderCLass", "loadInBackground");
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