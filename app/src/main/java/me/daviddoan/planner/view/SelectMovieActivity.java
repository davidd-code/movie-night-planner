package me.daviddoan.planner.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import me.daviddoan.planner.R;
import me.daviddoan.planner.adapter.EventRecyclerListAdapter;
import me.daviddoan.planner.adapter.MovieRecyclerListAdapter;
import me.daviddoan.planner.model.EventImpl;
import me.daviddoan.planner.model.MovieImpl;
import me.daviddoan.planner.viewmodel.AppViewModel;

/**
 * This activity opens when the user clicks on the "select movie" button. The user will then be
 * presented with the list of movies available in the application. Once the user clicks on a movie
 * then this activity will close and the movie selected will be associated with the event.
 */
public class SelectMovieActivity extends AppCompatActivity implements MovieRecyclerListAdapter.MovieRecyclerListListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AppViewModel mAppViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_movie);

        // set up tool bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Set up ViewModel object
        mAppViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        mAppViewModel.init();
        mAppViewModel.getMovieList().observe(this, new Observer<ArrayList<MovieImpl>>() {
            @Override
            public void onChanged(@Nullable ArrayList<MovieImpl> movies) {
                mAdapter.notifyDataSetChanged();
            }
        });
        // Set up recycler list view
        mRecyclerView = findViewById(R.id.moviesRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MovieRecyclerListAdapter(mAppViewModel.getMovieList().getValue(), this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    // When movie list item is clicked, close this activity and send information through to previous
    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent();

        intent.putExtra("Movie Title" , mAppViewModel.getMovie(mAppViewModel.getMovieArrayList(), position).getTitle());
        setResult(RESULT_OK, intent);
        finish();
    }
}
