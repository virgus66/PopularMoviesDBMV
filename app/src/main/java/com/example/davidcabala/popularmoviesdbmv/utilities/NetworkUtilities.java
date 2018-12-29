package com.example.davidcabala.popularmoviesdbmv.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.davidcabala.popularmoviesdbmv.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtilities {

    private static final String moviesUrl = "https://api.themoviedb.org/3";
    final static String API_KEY_PARAM = "api_key";
    final static String SORT_BY_PARAM = "sort_by";
    final static String API_KEY = "6b6d667ed3e438074ddf460ffe25a8ca"; // change the key to your own

    public static URL buildUrl(String endpoint, String sortBy, String page) {

        Uri builtUri = Uri.parse(moviesUrl + endpoint).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(SORT_BY_PARAM, sortBy)
                .appendQueryParameter("page", page)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
