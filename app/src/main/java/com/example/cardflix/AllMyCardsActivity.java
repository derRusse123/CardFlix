package com.example.cardflix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.cardflix.ui.home.Besitz_RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class AllMyCardsActivity extends AppCompatActivity implements RecyclerViewInterface{
    private GlobalCardList myGlobalList;
    Besitz_RecyclerViewAdapter bAdapter;
    RecyclerView recyclerViewMyCards;
    ArrayList<MyCard> searchCards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("All my Cards");
        myGlobalList = GlobalCardList.getInstance(this);
        setContentView(R.layout.activity_all_my_cards);
        recyclerViewMyCards = findViewById(R.id.rv_AllMyCards_MyCards);
        EditText search = findViewById(R.id.et_AllMyCards_Search);
        searchCards = myGlobalList.cardList;
        setupRecyclerView(myGlobalList.cardList);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchCards = myGlobalList.getAllCardsThatContainName(charSequence.toString());
                setupRecyclerView(searchCards);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        }
        );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecyclerItemClick(int position, int type) {
        Intent intent = new Intent(AllMyCardsActivity.this, ExpandedView.class);
        intent.putExtra("objectValues", searchCards.get(position));
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