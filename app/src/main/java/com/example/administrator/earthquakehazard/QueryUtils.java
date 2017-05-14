package com.example.administrator.earthquakehazard;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Administrator on 5/4/2017.
 */

public final class QueryUtils {


    private QueryUtils() {
    }

    public static String fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("log_tag", "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
//        EarthQuakeAdapterModel earthquake = extractEarthQuakes(jsonResponse);

        // Return the {@link Event}
        return jsonResponse;
    }


    private static URL createUrl(String stringUrl) {
        URL to_open_url = null;
        try {
            to_open_url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("log_tag", "Error with creating URL ", e);
        }
        return to_open_url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("log_tag", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("log_tag", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    public static ArrayList<EarthQuakeAdapterModel> extractEarthQuakes(String params) {



        ArrayList<EarthQuakeAdapterModel> earthQuakeAdapterModels = new ArrayList<>();

        try {
            /**
             * getting root json object
             */
            JSONObject root = new JSONObject(params);

            /**
             * getting JSonArray with key "features"
             */
            JSONArray features = root.optJSONArray("features");

            /**
             * Looping for the entire length of array
             */
            for (int i = 0; i < features.length(); i++) {
                /**
                 * getting the i-th element for the array
                 */
                JSONObject array_element = features.optJSONObject(i);

                // getting the properties key value in jsonObject
                JSONObject properties = array_element.optJSONObject("properties");
                // getting the mag key value in double type variable
                Double magnitude = properties.optDouble("mag");
                // getting the place key value in string type variable
                String place = properties.optString("place");
                // getting the time key value in int type variable
                Long time = properties.optLong("time");
                // getting the url key from the json response using key url
                String url = properties.optString("url");

                /**
                 *adding to ArrayList of type Model Class
                 */
                earthQuakeAdapterModels.add(new EarthQuakeAdapterModel(magnitude, place, time, url));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return earthQuakeAdapterModels;
    }

}