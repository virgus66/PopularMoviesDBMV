package com.example.davidcabala.popularmoviesdbmv.utilities;

import android.util.Log;

import com.example.davidcabala.popularmoviesdbmv.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieJsonUtility {

    public static Movie[] parseMoviesJson(String json) {

        try {
            JSONObject root = new JSONObject(json);
            JSONArray resultsArr = root.getJSONArray("results");


            Log.d("--------- JSON --------", resultsArr.getJSONObject(0).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
