package com.example.assignment_1.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.assignment_1.view.MovieListActivity;

public class SelectMovieOnClickListener implements View.OnClickListener {

    public static final int MOVIE_REQUEST_CODE = 7;
    private Context context;
    private int eventIndex;

    public SelectMovieOnClickListener(Context c, int i) {
        this.context = c;
        this.eventIndex = i;
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(context, MovieListActivity.class);
        intent.putExtra("EVENT_INDEX", eventIndex);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>::::::::::     " + eventIndex);
        ((Activity)context).startActivityForResult(intent, MOVIE_REQUEST_CODE);
    }
}
