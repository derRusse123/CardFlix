package com.example.cardflix.cardApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;

public interface APICallbacks {
    public void cardsByNameCallback(JSONArray array) throws JSONException, IOException;
    public void suggestedCardCallback(JSONObject object) throws JSONException;
    public void filteredCardsCallback(JSONArray array);
}
