package com.example.davidcabala.popularmoviesdbmv;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.davidcabala.popularmoviesdbmv.interfaces.OnBottomReachedListener;
import com.example.davidcabala.popularmoviesdbmv.interfaces.OnMoviesAdapterItemClickListener;
import com.example.davidcabala.popularmoviesdbmv.utilities.MovieJsonUtility;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FetchMovieAsync.AsyncResponse, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private MoviesAdapter mMoviesAdapter;
    private Menu mMenu;
    private int currPage;
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    String endpoint = "/movie/popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currPage  = 1;

        mRecyclerView= (RecyclerView) findViewById(R.id.movies_recycler_view);
        mRecyclerView.setHasFixedSize(true);
/*        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);*/
        gridLayoutManager = new GridLayoutManager(MainActivity.this,3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        updateMovies();
    }

    @Override
    public void onClick(View v) {

        switch ( v.getId() ) {
            default:
        }

        if (currPage < 1) {
            currPage = 1;
        } else updateMovies();
    }

    public void updateMovies() {
        if (isNetworkAvailable()) {

            FetchMovieAsync async = new FetchMovieAsync();
            async.delegate = this;
            async.execute(endpoint, String.valueOf(currPage));

        } else {
            Toast.makeText(this, R.string.error_need_internet, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void processFinish(String json) {
        Log.v("------=== JSON ===-----", json);

        if ( movies.size() == 0 ) {
            movies = new MovieJsonUtility().parseMoviesJson(json);
            mMoviesAdapter = new MoviesAdapter(this, movies, new OnMoviesAdapterItemClickListener() {
                @Override
                public void OnItemClicked(int pos) {
                    Gson gson = new Gson();
                    String send_movie = gson.toJson(movies.get(pos));

                    Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                    intent.putExtra("MOVIE_OBJECT", send_movie);
                    MainActivity.this.startActivity(intent);
                }
            });

            mRecyclerView.setAdapter(mMoviesAdapter);
            mMoviesAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
                @Override
                public void OnBottomReached(int position) {
                    currPage++;
                    updateMovies();
                }
            });
        }
        else
        {
            ArrayList<Movie> more_movies;
            more_movies = new MovieJsonUtility().parseMoviesJson(json);
            movies.addAll(more_movies);
            mMoviesAdapter.notifyItemInserted( movies.size() - 1 );
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, mMenu);

        // Make menu items accessible
        mMenu = menu;
        updateSharedPrefs( getString(R.string.popularity), getSortOrder() );

        // Add menu items
        mMenu.add(Menu.NONE,
                R.string.popularity,
                Menu.NONE,
                null)
                .setVisible(true)
                .setIcon(R.drawable.popular_white)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        mMenu.add(Menu.NONE,
                R.string.vote_average,
                Menu.NONE,
                null)
                .setVisible(false)
                .setIcon(R.drawable.average_white)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.string.popularity:
                endpoint = "/movie/top_rated";
                updateMenu();
                currPage = 1;
                movies.clear();
                updateMovies();
                return true;

            case R.string.vote_average:
                endpoint = "/movie/popular";
                updateMenu();
                currPage = 1;
                movies.clear();
                updateMovies();
                return true;

            default:
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateMenu() {
        String sortBy = getSortBy();
        Log.d("------SORT_BY------", sortBy);

        if (sortBy.equals( getString(R.string.popularity) )) {
            mMenu.findItem( R.string.popularity ).setVisible(false);
            mMenu.findItem( R.string.vote_average ).setVisible(true);
            updateSharedPrefs( getString(R.string.vote_average), getSortOrder() );
        } else {
            mMenu.findItem( R.string.popularity ).setVisible(true);
            mMenu.findItem( R.string.vote_average ).setVisible(false);
            updateSharedPrefs( getString(R.string.popularity), getSortOrder() );
        }
    }

    private String getSortOrder() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        return prefs.getString("SORT_ORDER",
                "desc");
    }


    private String getSortBy() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        return prefs.getString("SORT_BY",
                "popularity");
    }

    private void updateSharedPrefs(String sortBy, String sortOrder) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("SORT_BY", sortBy);
        editor.putString("SORT_ORDER", sortOrder);
        editor.apply();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

}
