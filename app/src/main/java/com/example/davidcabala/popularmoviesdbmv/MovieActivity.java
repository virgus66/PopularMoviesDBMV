package com.example.davidcabala.popularmoviesdbmv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MovieActivity extends AppCompatActivity {

    private ImageView photo;
    private TextView title;
    private TextView release_date;
    private TextView vote_average;
    private TextView total_votes;
    private TextView overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        photo        = findViewById(R.id.movie_activity_imageView);
        title        = findViewById(R.id.movie_activity_title);
        release_date = findViewById(R.id.movie_activity_release);
        vote_average = findViewById(R.id.movie_activity_votes_av);
        total_votes  = findViewById(R.id.movie_activity_votes_total);
        overview     = findViewById(R.id.movie_activity_content);

        String incomingString = getIntent().getStringExtra("MOVIE_OBJECT");
        Gson gson = new Gson();
        Movie movie = gson.fromJson( incomingString, Movie.class );


        Picasso.get()
                .load( movie.getPosterPath() )
                .placeholder( R.mipmap.placeholder )
                .into( photo );

        title.setText( movie.getTitle() );
        release_date.setText( movie.getReleaseDate() );
        vote_average.setText( movie.getVoteAverage() );
        total_votes.setText( movie.getVoteCount() );
        overview.setText( movie.getOverview() );

        Log.d("---- == MOVIE J == ---", incomingString);

    }
}
