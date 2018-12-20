package com.example.davidcabala.popularmoviesdbmv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidcabala.popularmoviesdbmv.interfaces.OnMoviesAdapterItemClickListener;
import com.squareup.picasso.Picasso;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>{
    private Context ctx;
    private Movie[] movies;

    public OnMoviesAdapterItemClickListener adapterClickListener;

    public MoviesAdapter(Context ctx, Movie[] movies, OnMoviesAdapterItemClickListener adapterClickListener) {
        this.ctx    = ctx;
        this.movies = movies;
        this.adapterClickListener = adapterClickListener;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);

        return new MoviesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, final int position) {
        holder.mTitle.setText( movies[ position ].getTitle() );
        Picasso.get()
                .load( movies[ position ].getPosterPath() )
                .placeholder( R.mipmap.placeholder )
                .into(holder.mMovieImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adapter onclick interface - fire function on MainActivity
                adapterClickListener.OnItemClicked( position);
            }
        });
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
}
