package com.sdn.aivimapandroid.map.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.sdn.aivimapandroid.R;
import com.sdn.aivimapandroid.map.AiviMapCreator;
import com.sdn.aivimapandroid.map.AiviUtils;
import com.sdn.aivimapandroid.map.TrackerData;

public class CustomWindowMarker implements GoogleMap.InfoWindowAdapter {

    private final View markerItemView;
    private Context context;
    private Boolean fromHistoryTracking;
    private int counter;

    public CustomWindowMarker(Context context, boolean fromHistoryTracking, int counter) {
        this.context = context;
        this.fromHistoryTracking = fromHistoryTracking;
        this.counter = counter;
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
        if (fromHistoryTracking) {
            if (marker != null) {
                if (marker.getSnippet() != null) {
                    AiviMapCreator aiviMapCreator = gson.fromJson(marker.getSnippet(), AiviMapCreator.class);
                    if (!aiviMapCreator.getTrackerDataList().isEmpty()) {
                        TrackerData trackerData = aiviMapCreator.getTrackerDataList().get(counter);
                        speed.setText("Speed : " + trackerData.getSpeed() + " Km/hr ");
                        name.setText("Plate : " + trackerData.getPlate());
                        date.setText("Date :" + AiviUtils.splitDate(trackerData.getDate(), "T"));
                        String address = "Location:" + AiviUtils.getCompleteAddressString(context, Double.parseDouble(trackerData.getLatitude()), Double.parseDouble(trackerData.getLongtitude()));
                        location.setText(address);
                    }
                }
            }

        } else {
            if (marker != null) {
                if (marker.getSnippet() != null) {
                    AiviMapCreator aiviMapCreator = gson.fromJson(marker.getSnippet(), AiviMapCreator.class);
                    if (!aiviMapCreator.getSpeed().isEmpty())
                        speed.setText("Speed : " + aiviMapCreator.getSpeed() + " Km/hr ");
                    if (!aiviMapCreator.getId().toString().equals(""))
                        name.setText("Plate : " + aiviMapCreator.getId().toString());
                    if (!aiviMapCreator.getDate().isEmpty())
                        date.setText("Date :" + AiviUtils.splitDate(aiviMapCreator.getDate().toString(), "T"));
                    if (!String.valueOf(aiviMapCreator.getSpecificLatLng()).isEmpty()) {
                        String address = "Location:" + AiviUtils.getCompleteAddressString(context, aiviMapCreator.getSpecificLatLng().latitude, aiviMapCreator.getSpecificLatLng().longitude);
                        location.setText(address.toString());
                    }
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