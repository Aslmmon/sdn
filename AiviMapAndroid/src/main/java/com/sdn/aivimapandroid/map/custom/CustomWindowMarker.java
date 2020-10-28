package com.sdn.aivimapandroid.map.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.sdn.aivimapandroid.R;
import com.sdn.aivimapandroid.map.AiviMapCreator;
import com.sdn.aivimapandroid.map.AiviUtils;

public class CustomWindowMarker implements GoogleMap.InfoWindowAdapter {

    private final View markerItemView;
    private Context context;

    public CustomWindowMarker(Context context) {
        this.context = context;
        markerItemView = LayoutInflater.from(context).inflate(R.layout.marker_info_window, null);  // 1
    }

    @Override
    public View getInfoContents(Marker marker) {

//        if (marker != null && marker.isInfoWindowShown()) {
//            marker.hideInfoWindow();
//            marker.showInfoWindow();
//            renderWindow(marker, markerItemView);
//        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    private void renderWindow(Marker marker, View view) {
        TextView name = view.findViewById(R.id.tv_title);
        TextView speed = view.findViewById(R.id.tv_speed);
        TextView date = view.findViewById(R.id.tv_date);
        TextView location = view.findViewById(R.id.tv_location);
        Gson gson = new Gson();
        if (marker != null) {
            if (marker.getSnippet() != null) {
                AiviMapCreator aiviMapCreator = gson.fromJson(marker.getSnippet(), AiviMapCreator.class);
                if (!aiviMapCreator.getSpeed().isEmpty())
                    speed.setText("Speed : " + aiviMapCreator.getSpeed()  + " Km/hr ");
                if (!aiviMapCreator.getId().toString().equals(""))
                    name.setText("Name : " + aiviMapCreator.getId().toString());
                if (!aiviMapCreator.getDate().isEmpty())
                    date.setText("Date :" + AiviUtils.splitDate(aiviMapCreator.getDate().toString(), "T"));
                if (!String.valueOf(aiviMapCreator.getSpecificLatLng()).isEmpty()) {
                    String address = "Location:" + AiviUtils.getCompleteAddressString(context, aiviMapCreator.getSpecificLatLng().latitude, aiviMapCreator.getSpecificLatLng().longitude);
                    location.setText(address.toString());
                }
            }
        }


    }

    @Override
    public View getInfoWindow(Marker marker) {
        if (marker != null) {
            renderWindow(marker, markerItemView);
        }
        return markerItemView;
    }
}