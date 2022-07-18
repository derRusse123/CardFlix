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
    private boolean allCardsLoadedIn = false;
    private DatabaseReference cardsRef;
    public ArrayList<DbCard> dbCards;
    public ArrayList<MyCard> cardList;
    private ArrayList<DbCard> allCardsOnBoot;
    private static GlobalCardList instance = null;
    private OnGetCardsCompleted cardsLoadedListener;
    private ChildEventListener myChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
            Log.d("Added", "SNAP:" + dataSnapshot.getKey());
            //Been called in the Beginning for each card the User has
            if(!allCardsLoadedIn) { System.out.println("CardsNotLoadedInYet"); return; }
            DbCard card = dataSnapshot.getValue(DbCard.class);
            if(card == null){ return; }
            dbCards.add(card);
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
            if(!allCardsLoadedIn) { System.out.println("CardsNotLoadedInYet"); return; }
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
            if(!allCardsLoadedIn) { System.out.println("CardsNotLoadedInYet"); return; }
            int index = -1;
            String cardKey = dataSnapshot.getKey();
            for (int i = 0; i < dbCards.size(); i++) {
                if(dbCards.get(i).key.equals(cardKey)){
                    index = i;
                    break;
                }
            }
            if(index != -1){
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
            if(!allCardsLoadedIn) { System.out.println("CardsNotLoadedInYet"); return; }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.w("Cancel", "onCancelled", databaseError.toException());
            System.out.println("ONCANCELED");
            allCardsLoadedIn = false;
            dbCards.clear();
            cardList.clear();
        }
    };
    APIQueue singletonQueue;
    APICalls calls;

    protected GlobalCardList(Context context) {
        cardList = new ArrayList<>();
        dbCards = new ArrayList<>();
        this.cardsLoadedListener = null;
        singletonQueue = APIQueue.getInstance(context);
        calls = new APICalls(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase myDb = FirebaseDatabase.getInstance("https://cardflix-4cfb8-default-rtdb.europe-west1.firebasedatabase.app");
        if(mAuth.getUid() != null){
            cardsRef = myDb.getReference().child("users").child(mAuth.getUid()).child("cards");
            // Gets all data once at the start
            cardsRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("Get Once", String.valueOf(task.getResult().getValue()));
                    allCardsOnBoot = new ArrayList<>();
                    for (DataSnapshot snap: task.getResult().getChildren()){
                        allCardsOnBoot.add(snap.getValue(DbCard.class));
                    }
                    allCardsOnBoot.forEach(dbCard -> {
                    });
                    this.getCardsFromAPI();
                    allCardsLoadedIn = true; // Do this once all cards are processed already
                }
                else {
                    Log.e("Get Once", "Error getting data", task.getException());
                }
            });
            // Adds Listener for certain events on db
            cardsRef.addChildEventListener(myChildEventListener);
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

    public void saveCard(String name, int rarityIndex, int amount) {
        System.out.println("saveCard");
        //Generates key and inserts Card in db
        DbCard dbCard = new DbCard(name, rarityIndex, amount);
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

    @Override
    public void cardsByNameCallback(JSONArray array) throws JSONException, IOException {
        for(int i = 0; i < allCardsOnBoot.size(); i++){
            //Searching for cards with the same name;
            for(int j = 0; j<array.length(); j++){
                if(allCardsOnBoot.get(i).name.equalsIgnoreCase(array.getJSONObject(j).getString("name"))){
                    MyCard newCard = new MyCard(array.getJSONObject(j),allCardsOnBoot.get(i).key);
                    newCard.setAmount(allCardsOnBoot.get(i).amount);
                    newCard.setRarityIndex(allCardsOnBoot.get(i).rarityIndex);
                    cardList.add(newCard);
                    break;
                }
            }
        }
        cardsLoadedListener.OnDataLoaded();
    }

    @Override
    public void suggestedCardCallback(JSONObject object) throws JSONException {

    }

    @Override
    public void filteredCardsCallback(JSONArray array) throws JSONException {

    }

    public void setCardsLoadedListener(OnGetCardsCompleted listener) {
        this.cardsLoadedListener = listener;
    }

    public void removeListener(){
        cardsRef.removeEventListener(myChildEventListener);
    }

    private void getCardsFromAPI(){
        String cardNames = "";
        for(int i = 0; i < allCardsOnBoot.size(); i++){
            if(i == allCardsOnBoot.size()-1){
                cardNames = cardNames + allCardsOnBoot.get(i).name;
            }else {
                cardNames = cardNames + allCardsOnBoot.get(i).name + "|";
            }
        }
        singletonQueue.addToRequestQueue(calls.getCardsByNameStringRequest(cardNames));
    }
}
