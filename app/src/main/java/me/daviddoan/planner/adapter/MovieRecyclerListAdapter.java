package me.daviddoan.planner.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.daviddoan.planner.R;
import me.daviddoan.planner.model.MovieImpl;

/**
 * This is the movie reycler list adapter which shows the list of movies that the user can select
 */
public class MovieRecyclerListAdapter extends RecyclerView.Adapter<MovieRecyclerListAdapter.RecyclerViewHolder>{
    private ArrayList<MovieImpl> mMovieList;
    private MovieRecyclerListListener mRecyclerListener;

    public MovieRecyclerListAdapter(ArrayList<MovieImpl> movieList, MovieRecyclerListListener movieRecyclerListListener) {
        this.mMovieList = movieList;
        this.mRecyclerListener = movieRecyclerListListener;
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;
        private TextView mTextView1;
        private TextView mTextView2;
        MovieRecyclerListListener movieRecyclerListListener;

        private RecyclerViewHolder(@NonNull View itemView, MovieRecyclerListListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.moviePosterImageView);
            mTextView1 = itemView.findViewById(R.id.eventTitleTextView);
            mTextView2 = itemView.findViewById(R.id.movieYearTextView);
            itemView.setOnClickListener(this);
            this.movieRecyclerListListener = listener;

        }

        @Override
        public void onClick(View v) {
            movieRecyclerListListener.onMovieClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        RecyclerViewHolder evh = new RecyclerViewHolder(v, mRecyclerListener);
        return evh;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        MovieImpl currentItem = mMovieList.get(i);

        switch(currentItem.getPoster()) {
            case "BladeRunner1982.jpg":
                recyclerViewHolder.mImageView.setImageResource(R.drawable.blade_runner1982);
                break;
            case "Hackers.jpg":
                recyclerViewHolder.mImageView.setImageResource(R.drawable.hackers);
                break;
        }
        recyclerViewHolder.mTextView1.setText(currentItem.getTitle());
        recyclerViewHolder.mTextView2.setText(currentItem.getYear());
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public MovieImpl getMovie(ArrayList<MovieImpl> movieList, int position) {
        return movieList.get(position);
    }

    public interface MovieRecyclerListListener {
        void onMovieClick(int position);
    }

}
