package com.example.cardflix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.cardflix.ui.home.Besitz_RecyclerViewAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AllMyCards extends AppCompatActivity implements RecyclerViewInterface{
    private GlobalCardList myGlobalList;
    private EditText search;
    Besitz_RecyclerViewAdapter bAdapter;
    RecyclerView recyclerViewMyCards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myGlobalList = GlobalCardList.getInstance(this);
        setContentView(R.layout.activity_all_my_cards);
        recyclerViewMyCards = findViewById(R.id.rv_AllMyCards_MyCards);
        search = findViewById(R.id.et_AllMyCards_Search);
        setupRecyclerView(myGlobalList.cardList);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<MyCard> searchCards = myGlobalList.getAllCardsThatContainName(charSequence.toString());
                setupRecyclerView(searchCards);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        }
        );
    }

    @Override
    public void onRecyclerItemClick(int position, int type) {
        Intent intent = new Intent(AllMyCards.this, ExpandedView.class);
        intent.putExtra("objectValues", myGlobalList.cardList.get(position));
        startActivity(intent);
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        setupRecyclerView(myGlobalList.cardList);
    }

    void setupRecyclerView(ArrayList<MyCard> list){
        bAdapter = new Besitz_RecyclerViewAdapter(this, list,this);
        recyclerViewMyCards.setAdapter(bAdapter);
        recyclerViewMyCards.setLayoutManager(new LinearLayoutManager(this));
    }

}