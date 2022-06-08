package com.example.cardflix.cardApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

// ?language=de for German

public class ApiCaller {
    private RequestQueue queue;
    private JSONObject result;

    public ApiCaller(){
        queue = Volley.newRequestQueue(null);
    }

    //Names separated by |
    public JSONObject getCardsByName(String queryString){
        String baseURL = "https://db.ygoprodeck.com/api/v7/cardinfo.php?name=";
        StringRequest stringRequest = createStringRequest(baseURL+queryString);
        queue.add(stringRequest);
        System.out.println(result.toString());
        return result;
    }

    public JSONObject getSuggestedCard(){
        String baseURL = "https://db.ygoprodeck.com/api/v7/randomcard.php";
        StringRequest stringRequest = createStringRequest(baseURL);
        queue.add(stringRequest);
        System.out.println(result.toString());
        return result;
    }

    public JSONObject getFilteredCards(String searchString){
        String baseURL = "https://db.ygoprodeck.com/api/v7/cardinfo.php?fname=";
        StringRequest stringRequest = createStringRequest(baseURL+searchString);
        queue.add(stringRequest);
        System.out.println(result.toString());
        return result;
    }

    private StringRequest createStringRequest(String url) {
        return new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJson = new JSONObject(response).getJSONObject("list");
                            result = responseJson;
                        } catch (JSONException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        System.out.println(error.getMessage());
                    }
                });
    }
}
