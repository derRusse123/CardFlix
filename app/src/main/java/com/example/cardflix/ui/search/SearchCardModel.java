package com.example.cardflix.ui.search;

public class SearchCardModel {
    String name;
    String picture;

    public SearchCardModel(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }
}
