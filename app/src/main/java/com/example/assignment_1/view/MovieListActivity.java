package com.example.assignment_1.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.assignment_1.R;
import com.example.assignment_1.controller.MovieDetailOnClickListener;
import com.example.assignment_1.viewModel.MovieListAdapter;

import static com.example.assignment_1.controller.SelectMovieOnClickListener.MOVIE_REQUEST_CODE;
import static com.example.assignment_1.model.EventModel.movies;

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MovieListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private int eventIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        buildRecyclerView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intentData){
        super.onActivityResult(requestCode, resultCode, intentData);
        if(requestCode == MOVIE_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK) {
                eventIndex = intentData.getIntExtra("EVENT_INDEX", -1);
                System.out.println("::::::::::::::::::::::::     " + eventIndex + "     :::::::::::::::::::::::::::");
            }
        }else{
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>> >>>>>>>>>>>>>   " + eventIndex);
        }
    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MovieListAdapter(movies);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Intent intent = getIntent();
        eventIndex = intent.getIntExtra("EVENT_INDEX", -1);

        if (eventIndex == -1) {
            System.out.println("::::::::::::::::::::::::::ERROR NO EVENT INDEX:::::::::::::::::::::::::::::::::::");
        }else{
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>   " + eventIndex);
        }
        mAdapter.setOnItemClickListener(new MovieDetailOnClickListener(this, eventIndex));
    }
}
