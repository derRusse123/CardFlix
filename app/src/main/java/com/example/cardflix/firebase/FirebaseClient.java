package com.example.cardflix.firebase;

import com.google.firebase.auth.*;

public class FirebaseClient {
    private FirebaseAuth mAuth;

    public FirebaseClient(FirebaseAuth mAuth){
        this.mAuth = mAuth;
    }

    public String[] getAllCardsFromUser(){
        String[] cardsFromUser = new String[0];
        return cardsFromUser;
    }
}
