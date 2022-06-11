package com.example.cardflix.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.example.cardflix.databinding.FragmentSearchBinding;
import com.example.cardflix.ui.home.Besitz_RecyclerViewAdapter;
import com.example.cardflix.ui.home.SuggestionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SearchFragment extends Fragment implements APICallbacks, RecyclerViewInterface {

    private FragmentSearchBinding binding;
    private View root;
    private EditText searchText;
    private RecyclerView recyclerViewSearch;
    ArrayList<SearchCardModel> searchCardModels = new ArrayList<>();
    private ArrayList<JSONObject> objToPass = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        recyclerViewSearch =  root.findViewById(R.id.rv_Search);
        APIQueue singletonQueue = APIQueue.getInstance(getContext().getApplicationContext());
        APICalls calls = new APICalls(this);
        searchText = root.findViewById(R.id.et_Search_Search);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if( charSequence.length() > 2){
                        singletonQueue.addToRequestQueue(calls.getFilteredCardsStringRequest(charSequence.toString()));
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        searchCardModels = new ArrayList<>();
        objToPass = new ArrayList<>();
        super.onDestroyView();
        binding = null;
    }

    private void initialiseRecyclerViewSearch(){
        Search_RecyclerViewAdapter searchAdapter = new Search_RecyclerViewAdapter(root.getContext(),searchCardModels,this);
        recyclerViewSearch.setAdapter(searchAdapter);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(root.getContext()));
    }

    private void setSearchCardModels(String name, String image){
        searchCardModels.add(new SearchCardModel(name,image));
    }

    @Override
    public void cardsByNameCallback(JSONArray array) throws JSONException, IOException {

    }

    @Override
    public void suggestedCardCallback(JSONObject object) throws JSONException {

    }

    @Override
    public void filteredCardsCallback(JSONArray array) throws JSONException {
        searchCardModels = new ArrayList<>();
        objToPass = new ArrayList<>();
        for(int i = 0; i< array.length(); i++){
            objToPass.add(array.getJSONObject(i));
            setSearchCardModels(array.getJSONObject(i).getString("name"),array.getJSONObject(i).getJSONArray("card_images").getJSONObject(0).getString("image_url"));
        }
    initialiseRecyclerViewSearch();
    }

    @Override
    public void onRecyclerItemClick(int position) {
        Intent intent = new Intent(getActivity(), ExpandedView.class);
        intent.putExtra("objectValues", objToPass.get(position).toString());
        startActivity(intent);
    }
}