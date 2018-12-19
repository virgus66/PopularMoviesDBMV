package com.example.davidcabala.popularmoviesdbmv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>{
    private Movie[] movies;

    public MoviesAdapter(Movie[] movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);

        return new MoviesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.mTitle.setText( movies[ position ].getTitle() );
        Picasso.get()
                .load( movies[ position ].getPosterPath() )
                .placeholder( R.mipmap.placeholder )
                .into(holder.mMovieImage);
        }

    @Override
    public int getItemCount() {
        return movies.length;
    }

    // custom viewholder
    public static class MoviesViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public ImageView mMovieImage;

        public MoviesViewHolder(View v) {
            super(v);

            mTitle      = (TextView) v.findViewById(R.id.movie_title);
            mMovieImage = (ImageView) v.findViewById(R.id.movie_item_poster_image);
        }
    }

/*    @Override
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
    }*/
}
