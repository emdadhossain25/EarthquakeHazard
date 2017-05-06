package com.example.administrator.earthquakehazard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Administrator on 5/2/2017.
 */

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuakeAdapterModel> {

    String location;
    String location_offset;
    private static final String LOCATION_SEPARATOR = "of ";

    public EarthQuakeAdapter(Activity context, ArrayList<EarthQuakeAdapterModel> earthQuakeAdapterModels) {
        super(context, 0, earthQuakeAdapterModels);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        final EarthQuakeAdapterModel earthQuakeAdapterModel = getItem(position);


        /**
         * spliting the text in half based on condition "of" or adding near using strings.xml
         */
        String full_location = earthQuakeAdapterModel.getLocation();


        if (full_location.contains(LOCATION_SEPARATOR)) {
            String[] parts = full_location.split(LOCATION_SEPARATOR);
            location_offset = parts[0] + LOCATION_SEPARATOR;
            location = parts[1];
        } else {
            location_offset = getContext().getString(R.string.near_the);
            location = full_location;
        }

        Double magnitude_double = earthQuakeAdapterModel.getMagnitude();
        String string_magnitude = doublFormatter(magnitude_double);

        TextView tv_magnitude = (TextView) listView.findViewById(R.id.magnitude);
        tv_magnitude.setText(string_magnitude);

        /**
         * getting the background color
         */
        GradientDrawable magnitudecircle = (GradientDrawable) tv_magnitude.getBackground();
        int magnitudeColor = getMagnitudeColor(earthQuakeAdapterModel.getMagnitude());

        // setting the color for background in the text view background color;
        magnitudecircle.setColor(magnitudeColor);


        TextView tv_location = (TextView) listView.findViewById(R.id.primary_location);
        tv_location.setText(location);

        TextView tv_location_offset = (TextView) listView.findViewById(R.id.location_offset);
        tv_location_offset.setText(location_offset);

        TextView tv_date_time = (TextView) listView.findViewById(R.id.date);
        Long timeInmilliseconds = earthQuakeAdapterModel.getDate_time();


        /**
         * getting date object
         */
        Date dateObject = new Date(timeInmilliseconds);

        String dateToDisplay = formatDate(dateObject);
        tv_date_time.setText(dateToDisplay);

        TextView tv_time = (TextView) listView.findViewById(R.id.time);
        String time = formatTime(dateObject);
        tv_time.setText(time);

        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri earthQuakeUri = Uri.parse(earthQuakeAdapterModel.getUrl());
                Intent website_intent = new Intent(Intent.ACTION_VIEW,earthQuakeUri);
                startActivity(getContext(),website_intent,null);
                Toast.makeText(getContext(), earthQuakeAdapterModel.getMagnitude() + "", Toast.LENGTH_LONG).show();
            }
        });

        return listView;
    }

    /**
     * magnitude double to string formatter
     */
    private String doublFormatter(Double mag) {
        DecimalFormat formatter = new DecimalFormat("0.0");
        String output = formatter.format(mag);
        return output;
    }


    /**
     * formating date into current date converting it from UNIX time
     *
     * @param dateObject
     * @return
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormatter.format(dateObject);
    }

    /**
     * formating time into current time converting it from UNIX time
     *
     * @param dateObject
     * @return
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * method for switching color values based on magnitude values -> changed from double to int for easy switching
     *
     * @param magnitude
     * @return
     */
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        // return statement for getting the color back in a int format because the setcolor method expects int
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }


}

