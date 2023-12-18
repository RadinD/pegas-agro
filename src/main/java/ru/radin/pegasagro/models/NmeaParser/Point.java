package ru.radin.pegasagro.models.NmeaParser;

public class Point {

    private double latitude; // Широта в радианах
    private double longitude; // Долгота в радинах

    public Point(){}

    public Point(GGASentence ggaSentence){
        setCoordinatesFromGGA(ggaSentence);
    }

    private void setLatitudeFromGGA(GGASentence ggaSentence){
        latitude = Math.toRadians(ggaSentence.getLatitude() * 100 / 60);
    }

    private void setLongitudeFromGGA(GGASentence ggaSentence){
        longitude = Math.toRadians(ggaSentence.getLongitude() * 100 / 60);
    }

    public void setCoordinatesFromGGA(GGASentence ggaSentence){
        setLatitudeFromGGA(ggaSentence);
        setLongitudeFromGGA(ggaSentence);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }




}

