package com.example.cardflix.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DbCard {
    public String key;
    public String name;
    public String type;
    public int amount;

    public DbCard() {
        //default constructor keep it
    }

    public DbCard(String name_, String type_, int amount_){
        this.name = name_;
        this.type = type_;
        this.amount = amount_;
    }
}
