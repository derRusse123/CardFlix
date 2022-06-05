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
    ArrayList<BesitzModel> besitzModels = new ArrayList<>();
    int[] besitzImages = {com.google.android.material.R.drawable.btn_radio_on_mtrl,com.google.android.material.R.drawable.btn_radio_on_mtrl,com.google.android.material.R.drawable.abc_btn_check_to_on_mtrl_015};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        System.out.println("Aufgerufennnnnnnnnnnnnnnnnnn");
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView = root.findViewById(R.id.rv_Besitz);
        setBesitzModels();
        Besitz_RecyclerViewAdapter mAdapter = new Besitz_RecyclerViewAdapter(root.getContext(),besitzModels);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        return root;


    }

    private void setBesitzModels(){
        String[] besitzNames = getResources().getStringArray(R.array.testListeTitle);
        String[] besitzBeschreibungen = getResources().getStringArray(R.array.testListeBeschreibung);

        for(int i = 0; i<besitzNames.length; i++){
            besitzModels.add(new BesitzModel(besitzNames[i],besitzBeschreibungen[i],besitzImages[i]));
        }

    }

    @Override
    public void onDestroyView() {
        besitzModels = new ArrayList<>();
        super.onDestroyView();
        binding = null;
    }


}