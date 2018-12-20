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
import com.example.davidcabala.popularmoviesdbmv.interfaces.OnMoviesAdapterItemClickListener;
import com.example.davidcabala.popularmoviesdbmv.utilities.MovieJsonUtility;
import com.google.gson.Gson;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements FetchMovieAsync.AsyncResponse, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private MoviesAdapter mMoviesAdapter;
    private Menu mMenu;
    private int currPage;
    private Button nextPage;
    private Button prevPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView= (RecyclerView) findViewById(R.id.movies_recycler_view);
        currPage  = 1;
        nextPage = (Button) findViewById(R.id.btn_next);
        prevPage = (Button) findViewById(R.id.btn_previous);

        nextPage.setOnClickListener(this);
        prevPage.setOnClickListener(this);


        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        updateMovies();

/*        Button crashButton = new Button(this);
        crashButton.setText("Crash!");
        crashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Crashlytics.getInstance().crash(); // Force a crash
            }
        });
        addContentView(crashButton, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));*/
    }

    @Override
    public void onClick(View v) {

        switch ( v.getId() ) {
            case R.id.btn_next:
                currPage++;
                break;
            case R.id.btn_previous:
                currPage--;
                break;

            default:
        }

        if (currPage < 1) {
            currPage = 1;
        } else updateMovies();
    }

    public void updateMovies() {
        if (isNetworkAvailable()) {
            String endpoint = "/discover/movie";
//            String endpoint = "/movie/top_rated";
//            String endpoint2 = "/movie/popular";

            FetchMovieAsync async = new FetchMovieAsync();
            async.delegate = this;

            async.execute(endpoint, getSortBy() +"." + getSortOrder(), String.valueOf(currPage));

        } else {
            Toast.makeText(this, R.string.error_need_internet, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void processFinish(String json) {
        Log.v("------=== JSON ===-----", json);

        final Movie[] movies = new MovieJsonUtility().parseMoviesJson(json);


        mMoviesAdapter = new MoviesAdapter(this, movies, new OnMoviesAdapterItemClickListener() {
            @Override
            public void OnItemClicked(int pos) {
                Gson gson = new Gson();
                String send_movie = gson.toJson(movies[pos]);

                Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                intent.putExtra("MOVIE_OBJECT", send_movie);
                MainActivity.this.startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mMoviesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, mMenu);

        // Make menu items accessible
        mMenu = menu;

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
                .setVisible(true)
                .setIcon(R.drawable.average_white)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        mMenu.add(Menu.NONE, // No group
                R.string.sort_asc, // ID
                Menu.NONE, // Sort order: not relevant
                null) // No text to display
                .setVisible(false)
                .setIcon(R.drawable.arr_down)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        mMenu.add(Menu.NONE,
                R.string.sort_desc,
                Menu.NONE,
                null)
                .setVisible(false)
                .setIcon(R.drawable.arr_up)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);


        // Update menu to show relevant items
        updateMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.string.popularity:
                updateSharedPrefs( getString(R.string.popularity), getSortOrder() );
                updateMenu();
                currPage = 1;
                updateMovies();
                return true;

            case R.string.vote_average:
                updateSharedPrefs( getString(R.string.vote_average), getSortOrder() );
                updateMenu();
                currPage = 1;
                updateMovies();
                return true;

            case R.string.sort_asc:
                updateSharedPrefs( getSortBy(), getString(R.string.sort_asc) );
                updateMenu();
                updateMovies();
                return true;

            case R.string.sort_desc:
                updateSharedPrefs( getSortBy(), getString(R.string.sort_desc) );
                updateMenu();
                updateMovies();
                return true;

            default:
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateMenu() {
        String sortOrder = getSortOrder();
        Log.d("------SORT_ORDER------", sortOrder);

        if (sortOrder.equals("asc")) {
            mMenu.findItem( R.string.sort_asc ).setVisible(false);
            mMenu.findItem( R.string.sort_desc ).setVisible(true);
        } else {
            mMenu.findItem( R.string.sort_desc ).setVisible(false);
            mMenu.findItem( R.string.sort_asc ).setVisible(true);
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
