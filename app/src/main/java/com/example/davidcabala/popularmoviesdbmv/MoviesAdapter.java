package com.example.davidcabala.popularmoviesdbmv;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>{
    private Movie[] movies;

    public MoviesAdapter(Movie[] movies) {
        this.movies = movies;
    }

    // custom viewholder
    public static class MoviesViewHolder extends RecyclerView.ViewHolder {
        public ImageView mMovieImage;

        public MoviesViewHolder(ImageView v) {
            super(v);
            this.mMovieImage = v;
        }
    }

    @Override
    public MoviesAdapter.MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);

        MoviesViewHolder vh = new MoviesViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        //holder.mMovieImage.setText( movies[position].getPosterPath() );

        Picasso.get()
                .load( movies[position].getPosterPath() )
                .into(holder.mMovieImage);
    }

    @Override
    public int getItemCount() {
        return movies.length;
    }
}
