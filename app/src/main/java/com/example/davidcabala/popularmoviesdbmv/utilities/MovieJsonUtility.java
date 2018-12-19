package com.example.davidcabala.popularmoviesdbmv.utilities;

import android.util.Log;

import com.example.davidcabala.popularmoviesdbmv.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieJsonUtility {

    public static Movie[] parseMoviesJson(String json) {


        try {
            JSONObject root = new JSONObject(json);
            JSONArray resultsArr = root.getJSONArray("results");


            Log.d("-------- LENGTH -------", resultsArr.length()+"");
            Movie[] movies = new Movie[ resultsArr.length() ];
            //List<Movie> movies = new ArrayList<Movie>();



            for (int i=0; i<resultsArr.length(); i++ ) {
                JSONObject movie = resultsArr.getJSONObject(i);

                String id           = movie.getString("id");
                String title        = movie.getString("title");
                String vote_count   = movie.getString("vote_count");
                String video        = movie.getString("video");
                String vote_average = movie.getString("vote_average");
                String popularity   = movie.getString("popularity");
                String poster_path  = movie.getString("poster_path");
                String original_lan = movie.getString("original_language");
                String original_tit = movie.getString("original_title");
                String overview     = movie.getString("overview");
                String release_date = movie.getString("release_date");

                movies[i] = new Movie(id,title,vote_count,video,vote_average,popularity,poster_path,original_lan,original_tit,overview,release_date);

                Log.d("--------- JSON --------",
                            movies[i].getVoteCount()   +" - "
                                + movies[i].getId()          +" - "
                                + movies[i].getVideo()       +" - "
                                + movies[i].getVoteAverage() +" - "
                                + movies[i].getTitle()       +" - "
                                + movies[i].getPopularity()  +" - "
                                + movies[i].getPosterPath()  +" - "
                                + movies[i].getOriginalLan() +" - "
                                + movies[i].getOriginalTit() +" - "
                                + movies[i].getOverview()    +" - "
                                + movies[i].getReleaseDate());
            }

            return movies;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
