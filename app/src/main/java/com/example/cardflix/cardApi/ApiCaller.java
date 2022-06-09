package com.example.cardflix.cardApi;

import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cardflix.HomeActivity;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

// ?language=de for German

public class ApiCaller {
    private RequestQueue queue;
    private HomeActivity home;

    public ApiCaller(HomeActivity home){
        this.home = home;
        this.queue = Volley.newRequestQueue(home);
    }

    // Query String like "Fire Dragon|Ice Wizard|Big Chungus"
    // callback with JSONArray to home.receiveCardsByName
    public void getCardsByName(String queryString){
        String baseURL = "https://db.ygoprodeck.com/api/v7/cardinfo.php?name=";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL+queryString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("data");
                            home.receiveCardsByName(array);
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
        queue.add(stringRequest);
    }

    // callback with JSONObject to home.receiveSuggestedCard
    public void getSuggestedCard(){
        String baseURL = "https://db.ygoprodeck.com/api/v7/randomcard.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            home.receiveSuggestedCard(object);
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
        queue.add(stringRequest);
    }

    // callback with JSONArray to home.receiveFilteredCards
    public void getFilteredCards(String searchString){
        String baseURL = "https://db.ygoprodeck.com/api/v7/cardinfo.php?fname=";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL+searchString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("data");
                            home.receiveFilteredCards(array);
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
        queue.add(stringRequest);
    }
}
