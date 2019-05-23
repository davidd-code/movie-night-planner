package com.example.assignment_1.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.assignment_1.model.EventImpl;
import com.example.assignment_1.view.ViewMoviePosterActivity;
import com.example.assignment_1.viewModel.MovieListAdapter;

public class MovieDetailOnClickListener implements MovieListAdapter.OnItemClickListener {

    private Context context;
    private int eventIndex;

    private EventImpl currentEvent;

    public MovieDetailOnClickListener(Context c, int i) {
        this.context = c;
        this.eventIndex = i;
    }

    @Override
    public void onItemClick(int position) {
        //select movie
//        currentEvent = events.get(eventIndex);
//        currentEvent.setChosenMovie(movies.get(position));
        Intent intent = new Intent();
        intent.putExtra("EVENT_INDEX", eventIndex);
        intent.putExtra("MOVIE_INDEX", position);
        ((Activity)context).setResult(Activity.RESULT_OK, intent);

        ((Activity)context).finish();
    }

    @Override
    public void onPosterClick(int position) {
        Intent intent = new Intent(context, ViewMoviePosterActivity.class);
        intent.putExtra("ITEM_INDEX", position);
        context.startActivity(intent);
    }
}
