package com.example.cardflix.cardApi;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

// ?language=de for German

public class APICalls {
    private APICallbacks callbacks;
    public APICalls(APICallbacks backs){
        callbacks = backs;
    }

    // Query String like "Fire Dragon|Ice Wizard|Big Chungus"
    // callback with JSONArray to home.receiveCardsByName
    public StringRequest getCardsByNameStringRequest(String queryString){
        String baseURL = "https://db.ygoprodeck.com/api/v7/cardinfo.php?name=";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL+queryString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("data");
                            callbacks.cardsByNameCallback(array);
                        } catch (JSONException | IOException e) {
                            // Couldn't parse JSON
                            System.out.println(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Not good
                        System.out.println(error.getMessage());
                    }
                });
        return stringRequest;
    }

    // callback with JSONObject to home.receiveSuggestedCard
    public StringRequest getSuggestedCardStringRequest(){
        String baseURL = "https://db.ygoprodeck.com/api/v7/randomcard.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            callbacks.suggestedCardCallback(object);
                        } catch (JSONException e) {
                            // Couldn't parse JSON
                            System.out.println(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Not good
                        System.out.println(error.getMessage());
                    }
                });
        return stringRequest;
    }

    // callback with JSONArray to home.receiveFilteredCards
    public StringRequest getFilteredCardsStringRequest(String searchString){
        String baseURL = "https://db.ygoprodeck.com/api/v7/cardinfo.php?fname=";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL+searchString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("data");
                            callbacks.filteredCardsCallback(array);
                        } catch (JSONException e) {
                            // Couldn't parse JSON
                            System.out.println(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Not good
                        System.out.println(error.getMessage());
                    }
                });
        return stringRequest;
    }
}
