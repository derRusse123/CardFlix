package com.example.cardflix;

import android.content.Context;

import java.util.ArrayList;
import java.util.Locale;

public class GlobalCardList {
    public ArrayList<MyCard> cardList;
    private static GlobalCardList instance = null;
    protected GlobalCardList() {
        cardList = new ArrayList<MyCard>();
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

}
