package com.example.administrator.earthquakehazard;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 5/2/2017.
 */

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuakeAdapterModel> {

    public EarthQuakeAdapter(Activity context, ArrayList<EarthQuakeAdapterModel> earthQuakeAdapterModels) {
        super(context,0,earthQuakeAdapterModels);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        final EarthQuakeAdapterModel earthQuakeAdapterModel = getItem(position);

        TextView tv_magnitude = (TextView)listView.findViewById(R.id.tv_magnitude);
        tv_magnitude.setText(earthQuakeAdapterModel.getMagnitude()+"");

        TextView tv_location = (TextView)listView.findViewById(R.id.tv_location);
        tv_location.setText(earthQuakeAdapterModel.getLocation());

        TextView tv_date_time = (TextView)listView.findViewById(R.id.tv_date_time);
        Long timeInmilliseconds = earthQuakeAdapterModel.getDate_time();
        Date dateObject = new Date(timeInmilliseconds);
        String dateToDisplay = formatDate(dateObject);
        tv_date_time.setText(dateToDisplay);

        TextView tv_time = (TextView)listView.findViewById(R.id.tv_time);
        String time = formatTime(dateObject);
        tv_time.setText(time);

        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),earthQuakeAdapterModel.getMagnitude()+"",Toast.LENGTH_LONG).show();
            }
        });

        return listView;
    }


    /**
     * formating date into current date converting it from UNIX time
     * @param dateObject
     * @return
     */
    private String formatDate(Date dateObject){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormatter.format(dateObject);
    }

    /**
     * formating time into current time converting it from UNIX time
     * @param dateObject
     * @return
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}

