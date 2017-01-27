package com.example.allan.androidweather;

/**
 * Created by Allan on 25/01/2017.
 */

public class Ville {

    String nom;
    Meteo meteo;

    public Ville(String nom, Meteo meteo) {

        this.meteo = meteo;
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Meteo getMeteo() {
        return meteo;
    }

    public void setMeteo(Meteo meteo) {
        this.meteo = meteo;
    }
}
