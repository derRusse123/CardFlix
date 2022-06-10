package com.example.cardflix.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardflix.HomeActivity;
import com.example.cardflix.LoginActivity;
import com.example.cardflix.R;
import com.example.cardflix.cardApi.APICallbacks;
import com.example.cardflix.cardApi.APICalls;
import com.example.cardflix.cardApi.APIQueue;
import com.example.cardflix.databinding.FragmentHomeBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements APICallbacks {
    private View root;
    private FragmentHomeBinding binding;
    private TextView totalPortfolio;
    private int suggestedCardCallbackCounter = 0;
    ArrayList<BesitzModel> besitzModels = new ArrayList<>();
    ArrayList<SuggestionModel> suggestionModels = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
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
            besitzModels.add(new BesitzModel(name,cardtype,image));
    }

    private void setSuggestionModels(String name, String price, String image){
        suggestionModels.add(new SuggestionModel(name,price,image));
    }

    @Override
    public void onDestroyView() {
        besitzModels = new ArrayList<>();
        suggestionModels = new ArrayList<>();
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void cardsByNameCallback(JSONArray array) throws JSONException {
        for(int i = 0; i< array.length(); i++){
            setBesitzModels(array.getJSONObject(i).getString("name"),array.getJSONObject(i).getString("type"),array.getJSONObject(i).getJSONArray("card_images").getJSONObject(0).getString("image_url"));
        }
        initialiseRecyclerViewBesitz();
    }

    @Override
    public void suggestedCardCallback(JSONObject object) throws JSONException {
        suggestedCardCallbackCounter++;
        setSuggestionModels(object.getString("name"),object.getString("type"),object.getJSONArray("card_images").getJSONObject(0).getString("image_url"));
        if(suggestedCardCallbackCounter >= 5) {
            initialiseSuggestions();
        }
    }

    @Override
    public void filteredCardsCallback(JSONArray array) {


    }

    private void initialiseRecyclerViewBesitz(){
        //initalise Besitz
        RecyclerView recyclerViewBesitz = root.findViewById(R.id.rv_Besitz);

        Besitz_RecyclerViewAdapter bAdapter = new Besitz_RecyclerViewAdapter(root.getContext(),besitzModels);
        recyclerViewBesitz.setAdapter(bAdapter);
        recyclerViewBesitz.setLayoutManager(new LinearLayoutManager(root.getContext()));
    }
    private void initialiseSuggestions() {
        RecyclerView recyclerViewSuggestions = root.findViewById(R.id.rv_Suggestions);
        Suggestion_RecyclerViewAdapter sAdapter = new Suggestion_RecyclerViewAdapter(root.getContext(),suggestionModels);
        recyclerViewSuggestions.setAdapter(sAdapter);
        recyclerViewSuggestions.setLayoutManager(new LinearLayoutManager(root.getContext(),LinearLayoutManager.HORIZONTAL,false));
    }

}