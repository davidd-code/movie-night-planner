package me.daviddoan.planner.adapter;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.daviddoan.planner.R;
import me.daviddoan.planner.model.EventImpl;
import me.daviddoan.planner.model.MovieImpl;

public class MovieRecyclerListAdapter extends RecyclerView.Adapter<MovieRecyclerListAdapter.RecyclerViewHolder>{
    public ArrayList<MovieImpl> mMovieList;

    public MovieRecyclerListAdapter(ArrayList<MovieImpl> movieList) {
        this.mMovieList = movieList;
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.moviePosterImageView);
            mTextView1 = itemView.findViewById(R.id.movieTitleTextView);
            mTextView2 = itemView.findViewById(R.id.movieYearTextView);

        }
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        RecyclerViewHolder evh = new RecyclerViewHolder(v);
        return evh;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        MovieImpl currentItem = mMovieList.get(i);

        // Set the movie poster
//        Resources res;
//        recyclerViewHolder.mImageView.setImageResource(currentItem.getPoster());
        recyclerViewHolder.mTextView1.setText(currentItem.getTitle());
        recyclerViewHolder.mTextView2.setText(currentItem.getYear());
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }


}
