package com.example.davidcabala.popularmoviesdbmv;


import android.os.AsyncTask;
import android.util.Log;

import com.example.davidcabala.popularmoviesdbmv.utilities.NetworkUtilities;

import java.net.URL;

public class FetchMovieAsync extends AsyncTask<String, Void, String[]>{

    private final String mApiKey;

    public FetchMovieAsync(String apiKey) {
        mApiKey = apiKey;
    }

    @Override
    protected String[] doInBackground(String[] objects) {
//        if (objects.length == 0) {
//            return null;
//        }

        String endpoint = "/movie/top_rated";

        URL movieRequestUrl = NetworkUtilities.buildUrl(endpoint, mApiKey);


        try {
            Log.d("-----------URL---------", movieRequestUrl.toString());
            String jsonMovieResponse = NetworkUtilities
                    .getResponseFromHttpUrl(movieRequestUrl);

//            String[] simpleJsonMovieData = OpenWeatherJsonUtils
//                    .getSimpleWeatherStringsFromJson(MainActivity.this, jsonMovieResponse);
//
//            return simpleJsonMovieData;

            Log.v("---------JSON--------", jsonMovieResponse);
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
