package com.example.cardflix.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DbCard {
    public String key;
    public String name;
    public int rarityIndex;
    public int amount;

    public DbCard() {
        //default constructor keep it
    }

    public DbCard(String name_, int rarityIndex_, int amount_){
        this.name = name_;
        this.rarityIndex = rarityIndex_;
        this.amount = amount_;
    }
}
