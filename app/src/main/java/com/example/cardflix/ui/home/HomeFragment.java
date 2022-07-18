package com.example.cardflix.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardflix.AllMyCardsActivity;
import com.example.cardflix.ExpandedView;
import com.example.cardflix.GlobalCardList;
import com.example.cardflix.MoreSuggestedCardsActivity;
import com.example.cardflix.MyCard;
import com.example.cardflix.R;
import com.example.cardflix.RecyclerViewInterface;
import com.example.cardflix.cardApi.APICallbacks;
import com.example.cardflix.cardApi.APICalls;
import com.example.cardflix.cardApi.APIQueue;
import com.example.cardflix.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements APICallbacks, RecyclerViewInterface {
    private View root;
    private FragmentHomeBinding binding;
    private TextView totalPortfolio;
    private GlobalCardList myGlobalList;
    private int suggestedCardCallbackCounter = 0;
    ArrayList<MyCard> myOwnershipModels = new ArrayList<>();
    ArrayList<MyCard> suggestionModels = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        totalPortfolio = root.findViewById(R.id.tv_Portfolio_Total);
        TextView viewMoreSuggestions = root.findViewById(R.id.viewMoreSuggestions);
        TextView viewMoreMyCards = root.findViewById(R.id.viewMoreMyCards);
        APIQueue singletonQueue = APIQueue.getInstance(getContext().getApplicationContext());
        APICalls calls = new APICalls(this);
        myGlobalList = GlobalCardList.getInstance(getContext().getApplicationContext());
        setPortfolioPrice();
        for(int i = 0;i <5; i++){
            singletonQueue.addToRequestQueue(calls.getSuggestedCardStringRequest());
        }

        viewMoreSuggestions.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), MoreSuggestedCardsActivity.class);
            startActivity(intent);
        });

        viewMoreMyCards.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AllMyCardsActivity.class);
            startActivity(intent);
        });

        return root;
    }

    private void setCollectionModels(MyCard obj) {
            myOwnershipModels.add(obj);
    }

    private void setSuggestionModels(JSONObject obj) throws JSONException {
        suggestionModels.add(new MyCard(obj, null));
    }

    @Override
    public void onDestroyView() {
        myOwnershipModels.clear();
        suggestionModels.clear();
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        myOwnershipModels.clear();
        initialiseRecyclerViewCollection();
        setPortfolioPrice();
        super.onStart();
    }

    @Override
    public void cardsByNameCallback(JSONArray array) {

    }

    @Override
    public void suggestedCardCallback(JSONObject object) throws JSONException {
        suggestedCardCallbackCounter++;
        setSuggestionModels(object);
        if(suggestedCardCallbackCounter >= 5) {
            initialiseSuggestions();
        }
    }

    @Override
    public void filteredCardsCallback(JSONArray array) {


    }

    private void initialiseRecyclerViewCollection() {
        int value;
        if(myGlobalList.cardList.size() > 1) {
            value = 2;
        }else if (myGlobalList.cardList.size() <1){
            value = 0;
        }
        else{
            value = 1;
        }
        for (int i = 0; i < value; i++) {
            setCollectionModels(myGlobalList.cardList.get(i));
        }

        RecyclerView recyclerViewCollection = root.findViewById(R.id.rv_Besitz);
        Collection_RecyclerViewAdapter bAdapter = new Collection_RecyclerViewAdapter(root.getContext(), myOwnershipModels,this);
        recyclerViewCollection.setAdapter(bAdapter);
        recyclerViewCollection.setLayoutManager(new LinearLayoutManager(root.getContext()));
    }
    private void initialiseSuggestions() {
        RecyclerView recyclerViewSuggestions = root.findViewById(R.id.rv_Suggestions);
        Suggestion_RecyclerViewAdapter sAdapter = new Suggestion_RecyclerViewAdapter(root.getContext(),suggestionModels,this);
        recyclerViewSuggestions.setAdapter(sAdapter);
        recyclerViewSuggestions.setLayoutManager(new LinearLayoutManager(root.getContext(),LinearLayoutManager.HORIZONTAL,false));
    }

    @Override
    public void onRecyclerItemClick(int position, int type) {
        MyCard setView = null;
        if(type == 0){
            setView = myOwnershipModels.get(position);
        }
        if(type == 1){
            setView = suggestionModels.get(position);
        }
        Intent intent = new Intent(getActivity(), ExpandedView.class);
        intent.putExtra("objectValues", setView);
        startActivity(intent);
    }

    private void setPortfolioPrice(){
        float money = 0;
        for (MyCard card:myGlobalList.cardList) {
          money = money + Float.valueOf(card.getRarityCardsPrice().get(card.getRarityIndex())) * Float.valueOf(card.getAmount());
        }
        String textoutput = (float)(Math.round(money * 100.0) / 100.0) + "$";
        totalPortfolio.setText(textoutput);
    }

}