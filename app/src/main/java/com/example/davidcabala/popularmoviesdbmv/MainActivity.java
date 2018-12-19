package com.example.davidcabala.popularmoviesdbmv;

import android.content.Context;
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
import com.example.davidcabala.popularmoviesdbmv.utilities.MovieJsonUtility;

public class MainActivity extends AppCompatActivity implements FetchMovieAsync.AsyncResponse {

    private RecyclerView mRecyclerView;
    private MoviesAdapter mMoviesAdapter;

    private Menu mMenu;
    private int currPage;
    private String sortBy;
    private String gsortOrder;

    TextView tvJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView= (RecyclerView) findViewById(R.id.movies_recycler_view);

        tvJSON = findViewById(R.id.json);
        currPage  = 2;
        sortBy    = "vote_average";
        gsortOrder = "desc";


        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        updateMovies();

//        Button crashButton = new Button(this);
//        crashButton.setText("Crash!");
//        crashButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Crashlytics.getInstance().crash(); // Force a crash
//            }
//        });
//        addContentView(crashButton, new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void updateMovies() {
        if (isNetworkAvailable()) {
            String endpoint = "/discover/movie";
//            String endpoint = "/movie/top_rated";
//            String endpoint2 = "/movie/popular";

            FetchMovieAsync async = new FetchMovieAsync();
            async.delegate = this;

            async.execute(endpoint, sortBy+"."+gsortOrder, String.valueOf(currPage+53));

        } else {
            Toast.makeText(this, R.string.error_need_internet, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void processFinish(String json) {
        Log.v("---------JSON-4--------", json);

        tvJSON.setText("");

        Movie[] movies = new MovieJsonUtility().parseMoviesJson(json);

        for (Movie m : movies) {
            String s =
                              m.getVoteCount()   + "\n"
                            + m.getId()          + "\n"
                            + m.getVideo()       + "\n"
                            + m.getVoteAverage() + "\n"
                            + m.getTitle()       + "\n"
                            + m.getPopularity()  + "\n"
                            + m.getPosterPath()  + "\n"
                            + m.getOriginalLan() + "\n"
                            + m.getOriginalTit() + "\n"
                            + m.getOverview()    + "\n"
                            + m.getReleaseDate() +"\n\n";
            tvJSON.append(s);
        }


        mMoviesAdapter = new MoviesAdapter(movies);
        mRecyclerView.setAdapter(mMoviesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, mMenu);

        // Make menu items accessible
        mMenu = menu;

        // Add menu items
        mMenu.add(Menu.NONE,
                3,
                Menu.NONE,
                null)
                .setVisible(true)
                .setIcon(R.mipmap.ic_launcher_foreground)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        mMenu.add(Menu.NONE, // No group
                1, // ID
                Menu.NONE, // Sort order: not relevant
                null) // No text to display
                .setVisible(false)
                .setIcon(R.mipmap.tesla)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        mMenu.add(Menu.NONE,
                2,
                Menu.NONE,
                null)
                .setVisible(false)
                .setIcon(R.mipmap.ic_launcher)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);


        // Update menu to show relevant items
        updateMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                updateSharedPrefs("","asc");
                updateMenu();
                //getMoviesFromTMDb(getSortMethod());
                gsortOrder = "asc";
                updateMovies();
                return true;
            case 2:
                updateSharedPrefs("","desc");
                updateMenu();
                //getMoviesFromTMDb(getSortMethod());
                gsortOrder = "desc";
                updateMovies();
                return true;
            case 3:
                if (sortBy.equals("vote_average")) {
                    sortBy = "popularity";
                } else sortBy = "vote_average";

                updateSharedPrefs(sortBy, gsortOrder);
                updateMenu();
                //getMoviesFromTMDb(getSortMethod());
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
            mMenu.findItem(1).setVisible(false);
            mMenu.findItem(2).setVisible(true);
        } else {
            mMenu.findItem(2).setVisible(false);
            mMenu.findItem(1).setVisible(true);
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
                "desc");
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
