package com.example.cardflix;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cardflix.firebase.DbCard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GlobalCardList {
    private FirebaseAuth mAuth;
    private FirebaseDatabase myDb;
    private DatabaseReference cardsRef;
    public ArrayList<DbCard> dbCards;

    public ArrayList<MyCard> cardList;
    private static GlobalCardList instance = null;
    protected GlobalCardList() {
        cardList = new ArrayList<MyCard>();
        mAuth = FirebaseAuth.getInstance();
        myDb = FirebaseDatabase.getInstance("https://cardflix-4cfb8-default-rtdb.europe-west1.firebasedatabase.app");
        if(mAuth.getUid() != null){
            cardsRef = myDb.getReference().child("users").child(mAuth.getUid()).child("cards");

            cardsRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d("Added", "onChildAdded:" + dataSnapshot.getKey());
                    Log.d("Added", "prevChild:" + previousChildName);
                    // A new comment has been added, add it to the displayed list
                    DbCard card = dataSnapshot.getValue(DbCard.class);
                    Log.d("Added", "Card:" + card.toString());
                    // ...
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d("Changed", "SNAP:" + dataSnapshot.getKey());
                    Log.d("Changed", "prevChild:" + previousChildName);
                    // A comment has changed, use the key to determine if we are displaying this
                    // comment and if so displayed the changed comment.
                    DbCard newCard = dataSnapshot.getValue(DbCard.class);
                    String cardKey = dataSnapshot.getKey();
                    Log.d("Changed", "Card:" + newCard.toString());
                    // ...
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Log.d("Removed", "SNAP:" + dataSnapshot.getKey());

                    // A comment has changed, use the key to determine if we are displaying this
                    // comment and if so remove it.
                    String cardKey = dataSnapshot.getKey();

                    // ...
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d("Moved", "SNAP:" + dataSnapshot.getKey());
                    Log.d("Moved", "prevChild:" + dataSnapshot.getKey());
                    // A comment has changed position, use the key to determine if we are
                    // displaying this comment and if so move it.
                    DbCard movedCard = dataSnapshot.getValue(DbCard.class);
                    Log.d("Moved", "Card:" + movedCard.toString());
                    String cardKey = dataSnapshot.getKey();

                    // ...
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("Cancel", "postComments:onCancelled", databaseError.toException());
                }
            });




            //// Add Value-Listener for cardsRef
            //cardsRef.addValueEventListener(new ValueEventListener() {
            //    @Override
            //    public void onDataChange(DataSnapshot snapshot) {
            //        dbCards = new ArrayList<>();
            //        for(DataSnapshot cardSnapshot : snapshot.getChildren()){
            //            dbCards.add(cardSnapshot.getValue(DbCard.class));
            //        }
            //        //Do Something with the dbCards you got from Firebase
            //        dbCards.forEach(dbCard -> {
            //            Log.d("OnChange", dbCard.name);
            //        });
            //    }
            //    @Override
            //    public void onCancelled(DatabaseError e) {
            //        Log.d("UpdateDbCards", e.toString());
            //    }
            //});
        }
        else{
            Log.d("GlobalCardList", "User is " + String.valueOf(mAuth.getUid()));
        }
    }
    public static GlobalCardList getInstance(Context applicationContext) {
        if(instance == null) {
            instance = new GlobalCardList();
        }
        return instance;
    }

    public boolean checkIfCardExistsCard(MyCard c) {
        for (MyCard card : cardList) {
            if (card.equals(c)) {
                return true;
            }
        }
        return false;
    }
    public int checkIfCardExistsString(String t, int rarityIndex) {
        int i = -1;
        for (MyCard card : cardList) {
            i++;
            if (card.getName().equals(t) && rarityIndex == card.getRarityIndex()) {
                return i;
            }
        }
        return -1;
    }
    public ArrayList<MyCard> getAllCardsThatContainName(String name){
        ArrayList<MyCard> returnList = new ArrayList<>();
        for (MyCard card: cardList) {
            if(card.getName().toUpperCase().contains(name.toUpperCase())){
                returnList.add(card);
            }
        }
        return returnList;
    }

    public void saveCard(String name, String type, int amount) {
        //Generates key and inserts Card in db
        DbCard dbCard = new DbCard(name, type, amount);
        dbCard.key = cardsRef.push().getKey();
        cardsRef.child(dbCard.key).setValue(dbCard);
    }
    public void updateCard(DbCard updatedCard){
        //Updates card with specific key in db
        cardsRef.child(updatedCard.key).setValue(updatedCard);
    }
    public void deleteCard(DbCard cardToDelete){
        //Deletes card with specific key from db
        cardsRef.child(cardToDelete.key).removeValue();
    }
}
