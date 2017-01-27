package com.example.allan.androidweather;

import android.os.Parcelable;

/**
 * Created by Allan on 25/01/2017.
 */
public class Meteo{

    private double latitude;
    private double longitude;
    private double temperatureActuelle;
    private double temperatureMax;
    private double temparatureMinimum;
    private String description;
    private String icone;

    public Meteo(double latitude, double longitude, double temperatureActuelle, double temperatureMax, double temparatureMinimum, String description, String icone) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperatureActuelle = temperatureActuelle;
        this.temperatureMax = temperatureMax;
        this.temparatureMinimum = temparatureMinimum;
        this.description = description;
        this.icone = icone;
    }

    public double getLatitude() {

        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemparatureMinimum() {
        return temparatureMinimum;
    }

    public void setTemparatureMinimum(double temparatureMinimum) {
        this.temparatureMinimum = temparatureMinimum;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public double getTemperatureActuelle() {
        return temperatureActuelle;
    }

    public void setTemperatureActuelle(double temperatureActuelle) {
        this.temperatureActuelle = temperatureActuelle;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    @Override
    public String toString() {
        return "Meteo{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", temperatureActuelle=" + temperatureActuelle +
                ", temperatureMax=" + temperatureMax +
                ", temparatureMinimum=" + temparatureMinimum +
                ", description=" + description +
                ", icone='" + icone + '\'' +
                '}';
    }
}
