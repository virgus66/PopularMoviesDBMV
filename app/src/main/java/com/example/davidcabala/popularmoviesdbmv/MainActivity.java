package com.example.davidcabala.popularmoviesdbmv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new FetchMovieAsync("6b6d667ed3e438074ddf460ffe25a8ca").execute();
    }
}
