package com.sdn.aivimapandroid.map;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

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
import com.google.gson.Gson;
import com.sdn.aivimapandroid.R;
import com.sdn.aivimapandroid.map.custom.CustomWindowMarker;

import java.util.ArrayList;
import java.util.List;

/**
 * created by Aslm on 18/10/2020 ..
 */


public class AiviMapFragment extends Fragment implements OnMapReadyCallback {

    /**
     * here we have made the needed variables
     * to be used in the google map platform ..
     * for example  : Markers , polylines, and latlng ..
     */

    private GoogleMap mMap;
    private Marker originMarker;
    private Marker movingCabMarker;
    private LatLng previousLatLngFromServer;
    private LatLng currentLatLngFromServer;
    private Polyline endPolyline;
    private static Handler taskHandler;
    private int counter2 = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, null, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mMap == null) {
            this.mMap = googleMap;
        }
        this.mMap.setInfoWindowAdapter(new CustomWindowMarker(requireActivity()));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.isInfoWindowShown()) {
                    marker.hideInfoWindow();
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * This is with Delay
     * This function is the core of the module in which
     * list of latlngs are passed ,, and then processing
     * of animating camera , adding marker to start , and update the location from websocket ..
     * need to be run on UI thread ..
     */
    public void showPathOfLocationsWithDelay(final AiviMapCreator aiviMapCreator) {

        final List<LatLng> list = aiviMapCreator.getListLocation();
        final int playSpeed = aiviMapCreator.getPlaySpeed();
        final ArrayList<LatLng> neededList = new ArrayList();
        try {
            counter2 = z();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        taskHandler = new Handler();


        requireActivity().runOnUiThread(new Runnable() {
            public void run() {

                Log.i("test", String.valueOf(counter2));
                neededList.add(list.get(counter2++));
                if (neededList.size() > 0) {
                    animateCameraToSpecificLatLngBounds(neededList);
                    setDrawToLinePolygon(neededList);
                    originMarker = addOriginDestinationMarkerAndGet(new LatLng(neededList.get(0).latitude, neededList.get(0).longitude));
                    originMarker.setAnchor(0.5f, 0.5f);
                    Log.i("list", neededList.toString());
                    Gson gson = new Gson();
                    final String markerInfoString = gson.toJson(aiviMapCreator);
                    for (int j = 0; j < neededList.size(); j++) {
                        moveUnit(new LatLng(neededList.get(j).latitude, neededList.get(j).longitude), "test");
                    }
                }
                taskHandler.postDelayed(this, 10000 / playSpeed);
            }
        });

    }

    public int z() throws InterruptedException {
        DummyThread foo = new DummyThread();

        Thread t = new Thread(foo);
        t.start();
        t.join();
        int value = foo.getValue();
        return value;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (taskHandler != null) taskHandler.removeCallbacksAndMessages(null);

    }

    /**
     * This function is the core of the module in which
     * list of latlngs are passed ,, and then processing
     * of animating camera , adding marker to start , and update the location from websocket ..
     * need to be run on UI thread ..
     */
    public void showPathOfLocations(final AiviMapCreator aiviMapCreator) {
        final List<LatLng> list = aiviMapCreator.getListLocation();
        getActivity().runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                if (list.size() > 0) {

                    animateCameraToSpecificLatLngBounds(list);
                    setDrawToLinePolygon(list);

                    originMarker = addOriginDestinationMarkerAndGet(new LatLng(list.get(0).latitude, list.get(0).longitude));
                    originMarker.setAnchor(0.5f, 0.5f);

                    Gson gson = new Gson();
                    String markerInfoString = gson.toJson(aiviMapCreator);
                    //  showPolylineAnimation();

                    for (int i = 0; i < list.size(); i++)
                        moveUnit(new LatLng(list.get(i).latitude, list.get(i).longitude), markerInfoString);
                }
            }
        });


    }


    private void setDrawToLinePolygon(List<LatLng> list) {
        /**
         * This function draw polyline  from the start destination of
         * Moving car , and also draw another polyline after ending Specific route with
         * another color
         *
         */


        endPolyline = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .width(5)
                .color(Color.GREEN)
                .addAll(list));
    }

    private void animateCameraToSpecificLatLngBounds(List<LatLng> list) {
        /**
         * this function is used to animate the camera to Specifically to LOCATION
         *
         * of the moving car begining from it's first location location ..
         *
         */
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (int i = 0; i < list.size(); i++) {
            builder.include(list.get(i));
        }
        if (!builder.build().equals(null)) {
            LatLngBounds bounds = builder.build();
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 2));

        }
    }

    private Marker addOriginDestinationMarkerAndGet(LatLng latlng) {
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(AiviUtils.getDestinationBitmap());
        MarkerOptions markerOptions = new MarkerOptions();
        return mMap.addMarker(
                markerOptions.position(latlng).flat(true).icon(bitmapDescriptor)

        );
    }


    private void moveUnit(LatLng latlng, final String markerInfoString) {
        /**
         * This function is used to update car location
         * and swap  the latlngs of previous and current ..
         * with animating the camera to the car location
         *
         */


        if (movingCabMarker == null) {
            movingCabMarker = addUnit(latlng);
        }
        if (previousLatLngFromServer == null) {
            currentLatLngFromServer = latlng;
            previousLatLngFromServer = currentLatLngFromServer;
            postionUnit(currentLatLngFromServer, markerInfoString);
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
                        postionUnit(nextLocation, markerInfoString);
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

    private void postionUnit(LatLng currentLatLngFromServer, String markerInfoString) {
        movingCabMarker.setPosition(currentLatLngFromServer);
        // movingCabMarker.setSnippet(markerInfoString);
        movingCabMarker.setAnchor(0.5f, 0.5f);
    }


    private void animateCamera(LatLng currentLatLngFromServer) {
        /**
         * used to animate camera to specific Latlng location
         */
        CameraPosition.Builder camera = CameraPosition.builder();
        CameraPosition cameraPosition = camera.target(currentLatLngFromServer).zoom(15.5f).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    private void moveCamera(LatLng currentLatLngFromServer) {
        /**
         * used to animate camera to specific Latlng location
         */
        CameraPosition.Builder camera = CameraPosition.builder();
        CameraPosition cameraPosition = camera.target(currentLatLngFromServer).zoom(15.5f).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public void showCurrentLocationOnMap(final LatLng currentLatLngFromServer,
                                         final AiviMapCreator aiviMapCreator) {
        /**
         * used to animate camera to specific Latlng location
         */
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CameraPosition.Builder camera = CameraPosition.builder();
                CameraPosition cameraPosition = camera.target(currentLatLngFromServer).zoom(15.5f).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                movingCabMarker = addUnit(currentLatLngFromServer);
                Gson gson = new Gson();
                String markerInfoString = gson.toJson(aiviMapCreator);
                postionUnit(currentLatLngFromServer, markerInfoString);

            }
        });

    }


    private Marker addUnit(LatLng latlng) {
        /**
         * used to getCar Marker  ..
         */
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(AiviUtils.getCarBitmap(this.getActivity()));
        MarkerOptions markerOptions = new MarkerOptions();
        return mMap.addMarker(markerOptions.position(latlng).flat(true).icon(bitmapDescriptor));
    }

}
