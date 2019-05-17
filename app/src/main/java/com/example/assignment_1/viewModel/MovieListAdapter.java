package com.example.assignment_1.viewModel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.assignment_1.R;
import com.example.assignment_1.model.MovieImpl;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    private ArrayList<MovieImpl> movies;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onPosterClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        listener = l;
    }

    public static class MovieListViewHolder extends RecyclerView.ViewHolder{
        public TextView movieTitle;
        public TextView movieYear;
        public ImageView posterImage;

        public MovieListViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieYear = itemView.findViewById(R.id.movieYear);
            posterImage = itemView.findViewById(R.id.posterImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            posterImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onPosterClick(position);
                        }
                    }
                }
            });
        }
    }

    public MovieListAdapter(ArrayList<MovieImpl> m){
        this.movies = m;
    }

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        MovieListViewHolder mvh = new MovieListViewHolder(v, listener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder movieListViewHolder, int i) {
        MovieImpl currentMovie = movies.get(i);
        movieListViewHolder.movieTitle.setText(currentMovie.getTitle());
        movieListViewHolder.movieYear.setText(currentMovie.getYear());
        movieListViewHolder.posterImage.setImageResource(currentMovie.getImgResource());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
