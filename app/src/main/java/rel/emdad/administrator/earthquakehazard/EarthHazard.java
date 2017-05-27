package rel.emdad.administrator.earthquakehazard;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.AsyncTaskLoader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

// TODO(1): Implement LoaderManager Callback with Library imported-import android.content.AsyncTaskLoader;
public class EarthHazard extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<EarthQuakeModel>> {

    String LOG_TAG = "Earthquake app";
    AdView adView;
    ListView listView;
    TextView textView;
    boolean isConnected_activeNetwork, isConnected_wifiNetwork;
    ProgressBar progressBar;
    EarthQuakeAdapter arrayAdapter;
    android.app.LoaderManager loaderManager;
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth_hazard);

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        NetworkInfo wifi_status = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        isConnected_activeNetwork = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        isConnected_wifiNetwork = wifi_status.isConnected();

        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);

        listView = (ListView) findViewById(R.id.listview1);
        textView = (TextView) findViewById(R.id.tv_emptyview);
        listView.setEmptyView(textView);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, EarthHazard.this);
        Log.d(LOG_TAG, "initLoader");
    }


    @Override
    protected void onPause() {

        if (adView != null) {
            adView.pause();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        if (adView != null) {
            adView.resume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {

        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }


    // TODO(3): Implement the methods onCreateLoader
    @Override
    public Loader<List<EarthQuakeModel>> onCreateLoader(int i, Bundle bundle) {
        Log.d(LOG_TAG, "onCreateLoader");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPreferences.getString(getString(R.string.settings_min_magnitude_key), getString(R.string.settings_min_magnitude_default));
        String orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "400");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);
        return new EarthquakeLoader(this, uriBuilder.toString());
    }

    // TODO(4): Implement the methods onLoadFinished method - called in main thread
    @Override
    public void onLoadFinished(Loader<List<EarthQuakeModel>> loader, List<EarthQuakeModel> data) {

        progressBar.setVisibility(View.GONE);
        if (!isConnected_activeNetwork) {
            textView.setText("No Internet Connection");
        } else if (!isConnected_wifiNetwork) {
            textView.setText("No Internet Connection");
        } else {
            textView.setText("No EarthQuake data found");
        }
        if (data != null && !data.isEmpty()) {
            // TODO(4.a): checking and setting the adapter;
            arrayAdapter = new EarthQuakeAdapter(EarthHazard.this, (ArrayList<EarthQuakeModel>) data);
            listView.setAdapter(arrayAdapter);
            Log.d(LOG_TAG, "onLoadfinished");

        }

    }

    // TODO(5): Implement the methods onCreateLoader
    @Override
    public void onLoaderReset(Loader<List<EarthQuakeModel>> loader) {
        // TODO(5.a): reseting the adapter

//            arrayAdapter.clear();
    }


    /**
     * TODO(2)- creating an earthquake Loader using the import android.content.Loader and  import android.content.AsyncTaskLoader;
     */
    private static class EarthquakeLoader extends AsyncTaskLoader<List<EarthQuakeModel>> {

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
        public List<EarthQuakeModel> loadInBackground() {
            Log.d("EarthQuakeLoaderCLass", "loadInBackground");
            if (result == null) {
                return null;
            }

            // TODO(2.d)- fetching the JsonResponse - using requestURL, makeHttpCOnnection, readInputStream, addinng bufferedRead and then adding in the model class
            result = QueryUtils.fetchEarthquakeData(result);
            ArrayList<EarthQuakeModel> city_array = QueryUtils.extractEarthQuakes(result);
            return city_array;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}