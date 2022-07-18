package com.example.cardflix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;


import com.example.cardflix.cardApi.APICallbacks;
import com.example.cardflix.cardApi.APICalls;
import com.example.cardflix.cardApi.APIQueue;
import com.example.cardflix.ui.home.Besitz_RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MoreSuggestedCardsActivity extends AppCompatActivity implements APICallbacks, RecyclerViewInterface {
    private RecyclerView recyclerViewMyCards;
    private APICalls calls;
    private APIQueue singletonQueue;
    private int counterSet = 0;
    private Parcelable recyclerViewState;
    private boolean pauseBeforeNext = false;
    ArrayList<MyCard> suggestionList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("More Suggestions");
        setContentView(R.layout.activity_more_suggested_cards);
        recyclerViewMyCards = findViewById(R.id.rv_MoreSuggestedCards_View);
        recyclerViewMyCards.setLayoutManager(new LinearLayoutManager(this));
        singletonQueue = APIQueue.getInstance(this);
        calls = new APICalls(this);
        getSuggestedCards();
        recyclerViewMyCards.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    if(!pauseBeforeNext) {
                        recyclerViewState = Objects.requireNonNull(recyclerViewMyCards.getLayoutManager()).onSaveInstanceState();
                        getSuggestedCards();
                        pauseBeforeNext = true;
                    }
                }
            }
        });
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
        Intent intent = new Intent(this, ExpandedView.class);
        intent.putExtra("objectValues", suggestionList.get(position));
        startActivity(intent);
    }

    @Override
    public void cardsByNameCallback(JSONArray array) {

    }

    @Override
    public void suggestedCardCallback(JSONObject object) throws JSONException {
        counterSet++;
        suggestionList.add(new MyCard(object, null));
        if(counterSet >= 5){
            setupRecyclerView(suggestionList);
        }
    }

    @Override
    public void filteredCardsCallback(JSONArray array) {

    }

    void setupRecyclerView(ArrayList<MyCard> list){
        Besitz_RecyclerViewAdapter bAdapter = new Besitz_RecyclerViewAdapter(this, list, this);
        recyclerViewMyCards.setAdapter(bAdapter);
        Objects.requireNonNull(recyclerViewMyCards.getLayoutManager()).onRestoreInstanceState(recyclerViewState);
        pauseBeforeNext = false;
    }

   private void getSuggestedCards(){
        counterSet = 0;
        for (int i = 0; i<5; i++){
            singletonQueue.addToRequestQueue(calls.getSuggestedCardStringRequest());
        }
    }
}