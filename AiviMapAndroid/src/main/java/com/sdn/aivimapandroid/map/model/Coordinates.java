package com.sdn.aivimapandroid.map.model;

public class Coordinates {
    private Double  longtit;
    private  Double latit;

    public Coordinates(double v, double v1) {
        this.latit = v1;
        this.longtit = v;
    }

    public Double getLongtit() {
        return longtit;
    }

    public void setLongtit(Double longtit) {
        this.longtit = longtit;
    }

    public Double getLatit() {
        return latit;
    }

    public void setLatit(Double latit) {
        this.latit = latit;
    }
}
