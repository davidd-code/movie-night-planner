package me.daviddoan.planner.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import me.daviddoan.planner.R;
import me.daviddoan.planner.adapter.RecyclerListAdapter;
import me.daviddoan.planner.model.EventImpl;
import me.daviddoan.planner.viewmodel.EventViewModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EventViewModel mEventViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up ViewModel object
        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        mEventViewModel.init();
        mEventViewModel.getEventList().observe(this, new Observer<ArrayList<EventImpl>>() {
            @Override
            public void onChanged(@Nullable ArrayList<EventImpl> events) {
                mAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerListAdapter(mEventViewModel.getEventList().getValue());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
