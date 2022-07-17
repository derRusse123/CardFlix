package com.example.cardflix.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardflix.ExpandedView;
import com.example.cardflix.MyCard;
import com.example.cardflix.R;
import com.example.cardflix.RecyclerViewInterface;
import com.example.cardflix.cardApi.APICallbacks;
import com.example.cardflix.cardApi.APICalls;
import com.example.cardflix.cardApi.APIQueue;
import com.example.cardflix.databinding.FragmentSearchBinding;

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
    ArrayList<MyCard> searchCardModels = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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
        searchCardModels.clear();
        super.onDestroyView();
        binding = null;
    }

    private void initialiseRecyclerViewSearch(){
        Search_RecyclerViewAdapter searchAdapter = new Search_RecyclerViewAdapter(root.getContext(),searchCardModels,this);
        recyclerViewSearch.setAdapter(searchAdapter);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(root.getContext()));
    }

    private void setSearchCardModels(JSONObject obj) throws JSONException {
        searchCardModels.add(new MyCard(obj, null));
    }

    @Override
    public void cardsByNameCallback(JSONArray array) throws JSONException, IOException {

    }

    @Override
    public void suggestedCardCallback(JSONObject object) throws JSONException {

    }

    @Override
    public void filteredCardsCallback(JSONArray array) throws JSONException {
        searchCardModels.clear();
        for(int i = 0; i< array.length(); i++){
            setSearchCardModels(array.getJSONObject(i));
        }
    initialiseRecyclerViewSearch();
    }

    @Override
    public void onRecyclerItemClick(int position) {
        Intent intent = new Intent(getActivity(), ExpandedView.class);
        intent.putExtra("objectValues", searchCardModels.get(position));
        startActivity(intent);
    }
}