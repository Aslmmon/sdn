package com.sdn.aivimapandroid.map.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.sdn.aivimapandroid.R;

public class CustomWindowMarker implements GoogleMap.InfoWindowAdapter {

    private final View markerItemView;
    private Context context;

    public CustomWindowMarker(Context context) {
        this.context = context;
        markerItemView = LayoutInflater.from(context).inflate(R.layout.marker_info_window, null);  // 1
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindow(marker,markerItemView);
        return markerItemView;
    }

    private void  renderWindow(Marker marker,View view){
        String title = marker.getTitle();
        TextView textView = view.findViewById(R.id.tv_title);
        //if(!title.equals("")) textView.setText(title);

    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindow(marker,markerItemView);
        return markerItemView;
    }
}