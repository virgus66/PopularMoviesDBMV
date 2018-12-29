package com.example.davidcabala.popularmoviesdbmv;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.davidcabala.popularmoviesdbmv.utilities.MovieJsonUtility;
import com.example.davidcabala.popularmoviesdbmv.utilities.NetworkUtilities;

import java.net.URL;
import java.util.Arrays;

public class FetchMovieAsync extends AsyncTask<String, Void, String>{

    private String mEndpoint;
    public AsyncResponse delegate = null;



    // interface to catch data in MainActivity
    public interface AsyncResponse {
        void processFinish(String output);
    }



    public FetchMovieAsync() {
        super();
    }

    @Override
    protected String doInBackground(String... params) {
        URL movieRequestUrl = NetworkUtilities.buildUrl(params[0], params[1]);

        try {
            Log.d("-----------URL---------", movieRequestUrl.toString());
            String jsonMovieResponse = NetworkUtilities
                    .getResponseFromHttpUrl(movieRequestUrl);

            return jsonMovieResponse;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        delegate.processFinish(s);
    }
}
