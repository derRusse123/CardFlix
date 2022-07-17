package com.example.cardflix;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cardflix.cardApi.APICallbacks;
import com.example.cardflix.cardApi.APICalls;
import com.example.cardflix.cardApi.APIQueue;
import com.example.cardflix.firebase.DbCard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class GlobalCardList implements APICallbacks {
    private DatabaseReference cardsRef;
    public ArrayList<DbCard> dbCards;
    public ArrayList<MyCard> cardList;
    private static GlobalCardList instance = null;
    APIQueue singletonQueue;
    APICalls calls;

    protected GlobalCardList(Context context) {
        cardList = new ArrayList<>();
        dbCards = new ArrayList<>();
        singletonQueue = APIQueue.getInstance(context);
        calls = new APICalls(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase myDb = FirebaseDatabase.getInstance("https://cardflix-4cfb8-default-rtdb.europe-west1.firebasedatabase.app");
        if(mAuth.getUid() != null){
            cardsRef = myDb.getReference().child("users").child(mAuth.getUid()).child("cards");
            cardsRef.addChildEventListener(new ChildEventListener() {

                // Jo Nikita - Beim Start ist cardList.size() null, heißt du musst für jeden onChildAdded eigentlich den API call machen, nicht umgekehrt

                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d("Added", "SNAP:" + dataSnapshot.getKey());
                    //Been called in the Beginning for each card the User has
                    DbCard card = dataSnapshot.getValue(DbCard.class);
                    if(card == null){ return; }
                    dbCards.add(card);
                    singletonQueue.addToRequestQueue(calls.getCardsByNameStringRequest(card.name));
                    Log.d("MyCard-length", String.valueOf(cardList.size()));
                    cardList.forEach(myCard -> {
                        if(myCard.getKey()==null){
                            myCard.setKey(card.key);
                        }
                    });
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d("Changed", "SNAP:" + dataSnapshot.getKey());
                    DbCard changedCard = dataSnapshot.getValue(DbCard.class);
                    if(changedCard == null){ return; }
                    int index = -1;
                    for (int i = 0; i < dbCards.size(); i++) {
                        if(dbCards.get(i).key.equals(changedCard.key)){
                            index = i;
                            break;
                        }
                    }
                    if(index != -1){
                        dbCards.set(index, changedCard);
                    }
                    else{
                        System.out.println("Error in onChildChanged");
                    }
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    System.out.println("onChildRemove");
                    Log.d("Removed", "SNAP:" + dataSnapshot.getKey());
                    int index = -1;
                    String cardKey = dataSnapshot.getKey();
                    for (int i = 0; i < dbCards.size(); i++) {
                        if(dbCards.get(i).key.equals(cardKey)){
                            index = i;
                            break;
                        }
                    }
                    if(index != -1){
                        System.out.println("KARTE GEFUNDEN");
                        dbCards.remove(index);
                    }
                    else{
                        System.out.println("Error in onChildRemoved");
                    }
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                    // This won't happen I guess
                    Log.d("Moved", "SNAP:" + dataSnapshot.getKey());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("Cancel", "postComments:onCancelled", databaseError.toException());
                }
            });
        }
        else{
            Log.d("GlobalCardList", "User is " + mAuth.getUid());
        }
    }
    public static GlobalCardList getInstance(Context applicationContext) {
        if(instance == null) {
            instance = new GlobalCardList(applicationContext);
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
        System.out.println("saveCard");
        //Generates key and inserts Card in db
        DbCard dbCard = new DbCard(name, type, amount);
        dbCard.key = cardsRef.push().getKey();
        if(dbCard.key == null) { Log.d("GlobalCardList", "This should not happen..."); return; }
        cardsRef.child(dbCard.key).setValue(dbCard);
    }
    public void updateCard(String key, int amount){
        System.out.println("updateCard");
        //Updates card with specific key in db
        cardsRef.child(key).child("amount").setValue(amount);
    }
    public void deleteCard(String key){
        System.out.println("deleteCard");
        //Deletes card with specific key from db
        cardsRef.child(key).setValue(null);
    }
    // db reset
    public void clearDbForTestingPurpose(){
        cardsRef.setValue(null);
    }

    @Override
    public void cardsByNameCallback(JSONArray array) throws JSONException, IOException {
        MyCard newCard = new MyCard(array.getJSONObject(0), null);

    }

    @Override
    public void suggestedCardCallback(JSONObject object) throws JSONException {

    }

    @Override
    public void filteredCardsCallback(JSONArray array) throws JSONException {

    }
}
