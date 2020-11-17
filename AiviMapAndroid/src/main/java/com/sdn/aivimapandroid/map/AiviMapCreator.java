
package com.sdn.aivimapandroid.map;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class AiviMapCreator {
    private final List<LatLng> listLocation;
    private final String speed;
    private final String snd_mileage;
    private final String device_mileage;
    private String id = "124";
    private final String date;
    private final LatLng specificLatLng;
    private final int playSpeed;
    private final List<TrackerData> trackerDataList;


    private AiviMapCreator(AiviMapBuilder builder) {
        this.listLocation = builder.listneeded;
        this.speed = builder.speed;
        this.snd_mileage = builder.snd_mileage;
        this.device_mileage = builder.device_mileage;
        this.id = builder.id;
        this.date = builder.date;
        this.specificLatLng = builder.specificLatLng;
        this.playSpeed = builder.playSpeed;
        this.trackerDataList = builder.trackerData;


    }

    public List<LatLng> getListLocation() {
        return listLocation;
    }

    public String getSpeed() {
        return speed;
    }

    public String getSnd_mileage() {
        return snd_mileage;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDevice_mileage() {
        return device_mileage;
    }

    public LatLng getSpecificLatLng() {
        return specificLatLng;
    }

    public int getPlaySpeed() {
        return playSpeed;
    }

    public List<TrackerData> getTrackerDataList() {
        return trackerDataList;
    }

    public static class AiviMapBuilder {
        private String snd_mileage = "0";
        private String device_mileage = "0";
        private String id = "0";
        private String date = "";
        private String speed = "0";
        private int playSpeed = 1;
        private LatLng specificLatLng = new LatLng(0.2, 0.0);
        private List<TrackerData> trackerData;

        private Context context;
        private List<LatLng> listneeded;


        public AiviMapBuilder(Context context) {
            this.context = context;
        }


        public AiviMapBuilder setLatLngs(List<LatLng> lists) {

            this.listneeded = lists;
            return this;
        }

        public AiviMapBuilder setSpecificLatLng(LatLng specificLatLng) {
            this.specificLatLng = specificLatLng;
            return this;
        }

        public AiviMapBuilder setSpeed(String speed) {
            if (speed != null) this.speed = speed;
            return this;
        }


        public AiviMapBuilder setSDN_mileage(String snd_mileage) {
            this.snd_mileage = snd_mileage;
            return this;
        }


        public AiviMapBuilder setDevice_mileage(String device_mileage) {
            this.device_mileage = device_mileage;
            return this;
        }

        public AiviMapBuilder setPlaySpeed(int playSpeed) {
            this.playSpeed = playSpeed;
            return this;
        }


        public AiviMapBuilder setId(String id) {
            this.id = id;
            return this;
        }


        public AiviMapBuilder setDate(String date) {
            this.date = date;
            return this;
        }

        public AiviMapBuilder setTrackerData(List<TrackerData> trackerData) {
            this.trackerData = trackerData;
            return this;
        }

        //Return the finally consrcuted User object
        public AiviMapCreator build() {
            AiviMapCreator aiviMapCreator = new AiviMapCreator(this);
            validateUserObject(aiviMapCreator);
            return aiviMapCreator;
        }

        private void validateUserObject(AiviMapCreator aiviMapCreator) {
            //if(aiviMapCreator.getTrackerDataList() && )
        }


    }
}
