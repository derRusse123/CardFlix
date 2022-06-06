package com.example.cardflix.ui.home;

public class BesitzModel {
    String besitzID;
    String besitzName;
    String besitzBeschreibung;
    int image;

    public BesitzModel(String besitzName, String besitzBeschreibung, int image) {
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

    public int getImage() {
        return image;
    }
}
