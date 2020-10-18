package com.sdn.aivimapandroid.map;

import android.animation.ValueAnimator;
import android.graphics.Camera;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sdn.aivimapandroid.R;
import com.sdn.aivimapandroid.map.model.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener {


    GoogleMap mMap;
    LatLng positionNeeded = new LatLng(25.0, 25.0);
    private Marker originMarker;
    private Marker destinationMarker;
    private Marker movingCabMarker;
    private LatLng previousLatLngFromServer;
    private LatLng currentLatLngFromServer;
    private Polyline greyPolyLine;
    private Polyline blackPolyline;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, null, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ArrayList<LatLng> listay = new ArrayList<>();
        listay.add(new LatLng( 61.52269494598361, 72.7734375));
        listay.add(new LatLng( 61.52269494598361, 72.7734375));
        listay.add(new LatLng( 61.52269494598361, 72.7734375));
        listay.add(new LatLng( 61.52269494598361, 72.7734375));

        listay.add(new LatLng(52.802761415419674, 16.34765625));
        listay.add(new LatLng(50.28933925329178, 18.80859375));
        listay.add(new LatLng( 47.989921667414194, 20.0390625));
        listay.add(new LatLng(20.0390625,66.796875));
        listay.add(new LatLng(60.84491057364912,72.421875));

        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (int i = 0; i < listay.size(); i++) {
            builder.include(listay.get(i));
        }
        LatLngBounds bounds = builder.build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 6));


        greyPolyLine = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .width(5)
                .color(Color.GRAY)
                .addAll(listay));

        blackPolyline = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .width(5)
                .color(Color.BLACK)
                .addAll(listay));

        originMarker = addOriginDestinationMarkerAndGet(new LatLng(listay.get(0).latitude, listay.get(0).longitude));
        originMarker.setAnchor(0.5f, 0.5f);
        destinationMarker = addOriginDestinationMarkerAndGet(new LatLng((listay.get(listay.size() - 1)).latitude, listay.get(listay.size() - 1).longitude));
        destinationMarker.setAnchor(0.5f, 0.5f);


        ValueAnimator polylineAnimator = AiviAnimation.polyLineAnimator();
        polylineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int percentage = (int) animation.getAnimatedValue();
                int index = (int) (greyPolyLine.getPoints().size() * (percentage / 100.0f));
                blackPolyline.setPoints(greyPolyLine.getPoints().subList(0, index));
            }
        });
        polylineAnimator.start();

        for (int i = 0; i < listay.size(); i++) {

            updateCarLocation(new LatLng(listay.get(i).latitude, listay.get(i).longitude));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mMap.setOnPolylineClickListener(this);

    }

    private Marker addOriginDestinationMarkerAndGet(LatLng latlng) {
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(AiviUtils.getDestinationBitmap());
        MarkerOptions markerOptions = new MarkerOptions();
        return mMap.addMarker(
                markerOptions.position(latlng).flat(true).icon(bitmapDescriptor)
        );
    }


    @Override
    public void onPolylineClick(Polyline polyline) {

    }

    public void updateCarLocation(LatLng latlng) {
        if (movingCabMarker == null) {
            movingCabMarker = addCarMarkerAndGet(latlng);
        }
        if (previousLatLngFromServer == null) {
            currentLatLngFromServer = latlng;
            previousLatLngFromServer = currentLatLngFromServer;
            movingCabMarker.setPosition(currentLatLngFromServer);
            movingCabMarker.setAnchor(0.5f, 0.5f);
            animateCamera(currentLatLngFromServer);
        } else {
            previousLatLngFromServer = currentLatLngFromServer;
            currentLatLngFromServer = latlng;
            ValueAnimator valueAnimator = AiviAnimation.cabAnimator();
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (currentLatLngFromServer != null && previousLatLngFromServer != null) {
                        float multiplier = animation.getAnimatedFraction();
                        LatLng nextLocation = new LatLng(
                                multiplier * currentLatLngFromServer.latitude + (1 - multiplier) * previousLatLngFromServer.latitude,
                                multiplier * currentLatLngFromServer.longitude + (1 - multiplier) * previousLatLngFromServer.longitude
                        );
                        movingCabMarker.setPosition(nextLocation);
                        movingCabMarker.setAnchor(0.5f, 0.5f);
                        float rotation = AiviUtils.getRotation(previousLatLngFromServer, nextLocation);
                        if (!Double.isNaN(rotation)) {
                            movingCabMarker.setRotation(rotation);
                        }
                        animateCamera(nextLocation);
                    }

                }
            });
            valueAnimator.start();
        }
    }

    private void animateCamera(LatLng currentLatLngFromServer) {
        CameraPosition.Builder camera = CameraPosition.builder();
        CameraPosition cameraPosition = camera.target(currentLatLngFromServer).zoom(15.5f).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    private Marker addCarMarkerAndGet(LatLng latlng) {
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(AiviUtils.getCarBitmap(this.getActivity()));
        MarkerOptions markerOptions = new MarkerOptions();

        return mMap.addMarker(
                markerOptions.position(latlng).flat(true).icon(bitmapDescriptor)
        );
    }

}