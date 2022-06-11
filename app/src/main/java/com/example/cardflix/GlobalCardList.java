package com.example.cardflix;

import android.content.Context;

import java.util.ArrayList;

public class GlobalCardList {
    ArrayList<MyCard> cardList;
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

    public boolean checkIfCardExists(String cardName) {
        for (MyCard card : cardList) {
            if (card.getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }
}
