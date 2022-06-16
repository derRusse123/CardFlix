package com.example.cardflix.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardflix.ExpandedView;
import com.example.cardflix.GlobalCardList;
import com.example.cardflix.MainActivity;
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
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        totalPortfolio = root.findViewById(R.id.tv_Portfolio_Total);
        APIQueue singletonQueue = APIQueue.getInstance(getContext().getApplicationContext());
        APICalls calls = new APICalls(this);
        myGlobalList = GlobalCardList.getInstance(getContext().getApplicationContext());
        for(int i = 0;i <5; i++){
            singletonQueue.addToRequestQueue(calls.getSuggestedCardStringRequest());
        }
        totalPortfolio.setText("30000€");

        return root;
    }

    private void setBesitzModels(MyCard obj) {
            myOwnershipModels.add(obj);
    }

    private void setSuggestionModels(JSONObject obj) throws JSONException {
        suggestionModels.add(new MyCard(obj));
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
        initialiseRecyclerViewBesitz();
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

    private void initialiseRecyclerViewBesitz() {
        if(myGlobalList.cardList.size() > 1) {
            for (int i = 0; i < 2; i++) {
                setBesitzModels(myGlobalList.cardList.get(i));
            }
        }
        RecyclerView recyclerViewBesitz = root.findViewById(R.id.rv_Besitz);
        Besitz_RecyclerViewAdapter bAdapter = new Besitz_RecyclerViewAdapter(root.getContext(), myOwnershipModels,this);
        recyclerViewBesitz.setAdapter(bAdapter);
        recyclerViewBesitz.setLayoutManager(new LinearLayoutManager(root.getContext()));
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

    public void onClickViewMoreSuggestions(View view){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}