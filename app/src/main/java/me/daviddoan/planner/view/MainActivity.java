package me.daviddoan.planner.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import me.daviddoan.planner.R;
import me.daviddoan.planner.adapter.RecyclerListAdapter;
import me.daviddoan.planner.model.EventImpl;
import me.daviddoan.planner.viewmodel.AppViewModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AppViewModel mAppViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView addEventsBtn = (ImageView)findViewById(R.id.addEventsBtn);
        addEventsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), AddEventsActivity.class);
                startActivity(startIntent);
            }
        });


        // Set up ViewModel object
        mAppViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        mAppViewModel.init();
        mAppViewModel.getEventList().observe(this, new Observer<ArrayList<EventImpl>>() {
            @Override
            public void onChanged(@Nullable ArrayList<EventImpl> events) {
                mAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerListAdapter(mAppViewModel.getEventList().getValue());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
