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
    private int suggestedCardCallbackCounter = 0;
    ArrayList<BesitzModel> myOwnershipModels = new ArrayList<>();
    ArrayList<SuggestionModel> suggestionModels = new ArrayList<>();
    ArrayList<JSONObject> objectsToPass = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        totalPortfolio = root.findViewById(R.id.tv_Portfolio_Total);
        APIQueue singletonQueue = APIQueue.getInstance(getContext().getApplicationContext());
        APICalls calls = new APICalls(this);
        singletonQueue.addToRequestQueue(calls.getCardsByNameStringRequest("Predaplant Bufolicula|Spellbinding Circle"));
        for(int i = 0;i <5; i++){
            singletonQueue.addToRequestQueue(calls.getSuggestedCardStringRequest());
        }
        totalPortfolio.setText("30000€");
        return root;


    }

    private void setBesitzModels(String name, String cardtype, String image){
            myOwnershipModels.add(new BesitzModel(name,cardtype,image));
    }

    private void setSuggestionModels(String name, String price, String image){
        suggestionModels.add(new SuggestionModel(name,price,image));
    }

    @Override
    public void onDestroyView() {
        myOwnershipModels = new ArrayList<>();
        suggestionModels = new ArrayList<>();
        objectsToPass = new ArrayList<>();
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void cardsByNameCallback(JSONArray array) throws JSONException {
        for(int i = 0; i< array.length(); i++){
            setBesitzModels(array.getJSONObject(i).getString("name"),array.getJSONObject(i).getString("type"),array.getJSONObject(i).getJSONArray("card_images").getJSONObject(0).getString("image_url"));
            objectsToPass.add(array.getJSONObject(i));
        }
        initialiseRecyclerViewBesitz();
    }

    @Override
    public void suggestedCardCallback(JSONObject object) throws JSONException {
        suggestedCardCallbackCounter++;
        objectsToPass.add(object);
        setSuggestionModels(object.getString("name"),object.getString("type"),object.getJSONArray("card_images").getJSONObject(0).getString("image_url"));
        if(suggestedCardCallbackCounter >= 5) {
            initialiseSuggestions();
        }
    }

    @Override
    public void filteredCardsCallback(JSONArray array) {


    }

    private void initialiseRecyclerViewBesitz(){
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
    public void onRecyclerItemClick(int position) {
       Intent intent = new Intent(getActivity(), ExpandedView.class);
       intent.putExtra("objectValues", objectsToPass.get(position).toString());
       startActivity(intent);
    }
}