package com.sdn.aivimapandroid.map;

public class TrackerData {
    private final String latitude;
    private final String longtitude;
    private final String Plate;
    private final String Date;
    private final Double Speed;

    public TrackerData(String latitude, String longtitude, String plate, String date, Double speed) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        Plate = plate;
        Date = date;
        Speed = speed;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public String getPlate() {
        return Plate;
    }

    public String getDate() {
        return Date;
    }

    public Double getSpeed() {
        return Speed;
    }
}

