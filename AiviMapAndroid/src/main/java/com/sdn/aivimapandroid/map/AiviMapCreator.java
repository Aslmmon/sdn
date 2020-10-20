package com.sdn.aivimapandroid.map;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class AiviMapCreator {
    private final int color;
    private final List<LatLng> listLocation;

    private AiviMapCreator(AiviMapBuilder builder) {
        this.color = builder.colorRoute;
        this.listLocation = builder.listneeded;

    }

    public static class AiviMapBuilder {
        private int colorRoute;
        private final List<LatLng> listneeded;


        public AiviMapBuilder(int colorRoute, List<LatLng> listneeded) {
            this.colorRoute = colorRoute;
            this.listneeded = listneeded;
        }

        public AiviMapBuilder setColorRoute(int colorRoute) {
            this.colorRoute = colorRoute;
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
    }
}
