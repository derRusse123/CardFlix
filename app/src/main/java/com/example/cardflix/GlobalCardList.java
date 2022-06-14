package com.example.cardflix;

import android.content.Context;

import java.util.ArrayList;

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
    public boolean checkIfCardExistsString(String t) {
        for (MyCard card : cardList) {
            if (card.getName().equals(t)) {
                return true;
            }
        }
        return false;
    }

    public MyCard getCardByName(String cardName) {
        for (MyCard card : cardList) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }

}
