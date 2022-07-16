package com.example.cardflix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cardflix.cardApi.APICallbacks;
import com.example.cardflix.cardApi.APICalls;
import com.example.cardflix.cardApi.APIQueue;
import com.example.cardflix.ui.home.Besitz_RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MoreSuggestedCards extends AppCompatActivity implements APICallbacks, RecyclerViewInterface {
    private GlobalCardList myGlobalList;
    private Besitz_RecyclerViewAdapter bAdapter;
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
        setContentView(R.layout.activity_more_suggested_cards);
        myGlobalList = GlobalCardList.getInstance(this);
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
                        recyclerViewState = recyclerViewMyCards.getLayoutManager().onSaveInstanceState();
                        getSuggestedCards();
                        pauseBeforeNext = true;
                    }
                }
            }
        });
    }

    @Override
    public void onRecyclerItemClick(int position, int type) {
        Intent intent = new Intent(this, ExpandedView.class);
        intent.putExtra("objectValues", suggestionList.get(position));
        startActivity(intent);
    }

    @Override
    public void cardsByNameCallback(JSONArray array) throws JSONException, IOException {

    }

    @Override
    public void suggestedCardCallback(JSONObject object) throws JSONException {
        counterSet++;
        suggestionList.add(new MyCard(object));
        if(counterSet >= 5){
            setupRecyclerView(suggestionList);
        }
    }

    @Override
    public void filteredCardsCallback(JSONArray array) throws JSONException {

    }

    void setupRecyclerView(ArrayList<MyCard> list){
        bAdapter = new Besitz_RecyclerViewAdapter(this, list,this);
        recyclerViewMyCards.setAdapter(bAdapter);
        recyclerViewMyCards.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        pauseBeforeNext = false;
    }

   private void getSuggestedCards(){
        counterSet = 0;
        for (int i = 0; i<5; i++){
            singletonQueue.addToRequestQueue(calls.getSuggestedCardStringRequest());
        }
    }
}