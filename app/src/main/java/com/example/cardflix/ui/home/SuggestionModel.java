package com.example.cardflix.ui.home;

public class SuggestionModel {
    String name;
    String price;
    String picture;

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getPicture() {
        return picture;
    }

    public SuggestionModel(String name, String price, String picture) {
        this.name = name;
        this.price = price;
        this.picture = picture;
    }
}
