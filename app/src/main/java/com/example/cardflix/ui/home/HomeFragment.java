package com.example.cardflix.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardflix.R;
import com.example.cardflix.databinding.FragmentHomeBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TextView totalPortfolio;
    ArrayList<BesitzModel> besitzModels = new ArrayList<>();
    ArrayList<SuggestionModel> suggestionModels = new ArrayList<>();

    int[] besitzImages = {com.google.android.material.R.drawable.btn_radio_on_mtrl,com.google.android.material.R.drawable.btn_radio_on_mtrl};
    int[] suggestionImages = {com.google.android.material.R.drawable.btn_radio_on_mtrl,com.google.android.material.R.drawable.btn_radio_on_mtrl,com.google.android.material.R.drawable.abc_btn_check_to_on_mtrl_015, R.drawable.ic_dashboard_black_24dp, R.drawable.ic_home_black_24dp};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        totalPortfolio = root.findViewById(R.id.tv_Portfolio_Total);
        totalPortfolio.setText("30000€");
        initialiseRecycleViews(root);
        return root;


    }

    private void initialiseRecycleViews(View root) {
        //initalise Besitz
        RecyclerView recyclerViewBesitz = root.findViewById(R.id.rv_Besitz);
        setBesitzModels();
        Besitz_RecyclerViewAdapter bAdapter = new Besitz_RecyclerViewAdapter(root.getContext(),besitzModels);
        recyclerViewBesitz.setAdapter(bAdapter);
        recyclerViewBesitz.setLayoutManager(new LinearLayoutManager(root.getContext()));

        //initalise Suggestions
        RecyclerView recyclerViewSuggestions = root.findViewById(R.id.rv_Suggestions);
        setSuggestionModels();
        Suggestion_RecyclerViewAdapter sAdapter = new Suggestion_RecyclerViewAdapter(root.getContext(),suggestionModels);
        recyclerViewSuggestions.setAdapter(sAdapter);
        recyclerViewSuggestions.setLayoutManager(new LinearLayoutManager(root.getContext(),LinearLayoutManager.HORIZONTAL,false));
    }

    private void setBesitzModels(){
        String[] besitzNames = getResources().getStringArray(R.array.testListeTitle);
        String[] besitzBeschreibungen = getResources().getStringArray(R.array.testListeBeschreibung);

        for(int i = 0; i<besitzNames.length; i++){
            besitzModels.add(new BesitzModel(besitzNames[i],besitzBeschreibungen[i],besitzImages[i]));
        }

    }

    private void setSuggestionModels(){
    String[] suggestionNames = getResources().getStringArray(R.array.testListSuggestionNames);
        String[] suggestionPrice = getResources().getStringArray(R.array.testListSuggestionPrice);

        for(int i = 0; i<suggestionNames.length; i++){
            suggestionModels.add(new SuggestionModel(suggestionNames[i],suggestionPrice[i],suggestionImages[i]));
        }
    }

    @Override
    public void onDestroyView() {
        besitzModels = new ArrayList<>();
        suggestionModels = new ArrayList<>();
        super.onDestroyView();
        binding = null;
    }


}