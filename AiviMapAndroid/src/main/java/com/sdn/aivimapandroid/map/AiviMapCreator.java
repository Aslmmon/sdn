
package com.sdn.aivimapandroid.map;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class AiviMapCreator {
    private final List<LatLng> listLocation;
    private String speed;
    private String snd_mileage;
    private String device_mileage;
    private String id;
    private String date;

    private AiviMapCreator(AiviMapBuilder builder) {
        this.listLocation = builder.listneeded;

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

    public static class AiviMapBuilder {
        private String speed;
        private String snd_mileage;
        private String device_mileage;
        private String id;
        private String date;


        private Context context;
        private List<LatLng> listneeded;


        public AiviMapBuilder(Context context) {
            this.context = context;
        }

        public AiviMapBuilder(int colorRoute, List<LatLng> listneeded) {
            this.listneeded = listneeded;
        }


        public AiviMapBuilder setLatLngs(List<LatLng> lists) {
            this.listneeded = lists;
            return this;
        }

        //Return the finally consrcuted User object
        public AiviMapCreator build() {
            AiviMapCreator aiviMapCreator = new AiviMapCreator(this);
            validateUserObject(aiviMapCreator);
            return aiviMapCreator;
        }

        private void validateUserObject(AiviMapCreator aiviMapCreator) {
        }

        public String getSpeed() {
            return speed;
        }

        public AiviMapBuilder setSpeed(String speed) {
            this.speed = speed;
            return this;
        }

        public String getSDNd_mileage() {
            return snd_mileage;
        }

        public AiviMapBuilder setSDN_mileage(String snd_mileage) {
            this.snd_mileage = snd_mileage;
            return this;
        }

        public String getDevice_mileage() {
            return device_mileage;
        }

        public AiviMapBuilder setDevice_mileage(String device_mileage) {
            this.device_mileage = device_mileage;
            return this;
        }

        public String getId() {
            return id;
        }

        public AiviMapBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public String getDate() {
            return date;
        }

        public AiviMapBuilder setDate(String date) {
            this.date = date;
            return this;
        }
    }
}
