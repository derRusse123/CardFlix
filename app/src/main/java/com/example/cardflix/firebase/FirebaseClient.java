package com.example.cardflix.firebase;

import com.google.firebase.auth.*;
import com.google.firebase.database.*;

public class FirebaseClient {
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    public FirebaseClient(FirebaseAuth mAuth){
        this.mAuth = mAuth;
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://cardflix-4cfb8-default-rtdb.europe-west1.firebasedatabase.app");
        this.myRef = database.getReference("cards");
    }

    public String[] getAllCardsFromUser(){
        // Read from the database
        this.myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                System.out.println("Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException().toString());
            }
        });

        String[] cardsFromUser = new String[0];
        return cardsFromUser;
    }
}
