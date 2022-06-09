package com.example.cardflix.ui.home;

import android.graphics.Bitmap;

public class BesitzModel {
    String besitzID;
    String besitzName;
    String besitzBeschreibung;
    String image;

    public BesitzModel(String besitzName, String besitzBeschreibung, String image) {
        this.besitzName = besitzName;
        this.besitzBeschreibung = besitzBeschreibung;
        this.image = image;
    }

    public String getBesitzName() {
        return besitzName;
    }

    public String getBesitzID(){return besitzID;}

    public String getBesitzBeschreibung() {
        return besitzBeschreibung;
    }

    public String getImage() {
        return image;
    }
}
