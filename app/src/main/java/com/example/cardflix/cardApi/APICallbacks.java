package com.example.cardflix.cardApi;

import org.json.JSONArray;
import org.json.JSONObject;

public interface APICallbacks {
    public void cardsByNameCallback(JSONArray array);
    public void suggestedCardCallback(JSONObject object);
    public void filteredCardsCallback(JSONArray array);
}
