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


import me.daviddoan.planner.R;
import me.daviddoan.planner.adapter.EventRecyclerListAdapter;
import me.daviddoan.planner.model.EventImpl;
import me.daviddoan.planner.model.EventModel;
import me.daviddoan.planner.viewmodel.AppViewModel;

public class MainActivity extends AppCompatActivity implements EventRecyclerListAdapter.EventRecyclerListListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView.Adapter mAdapter;
        RecyclerView mRecyclerView;
        AppViewModel mAppViewModel;
        RecyclerView.LayoutManager mLayoutManager;

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
//        mAppViewModel.getEventList().observe(this, new Observer<ArrayList<EventImpl>>() {
//            @Override
//            public void onChanged(@Nullable ArrayList<EventImpl> events) {
//                mAdapter.notifyDataSetChanged();
//            }
//        });
        EventModel.getInstance().setRecyclerViewAdapter(this);
        mRecyclerView = findViewById(R.id.eventsRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = EventModel.getInstance().getEventAdapter();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onEventClick(int position) {
        openEditDialog(position);
    }

    public void openEditDialog(int position) {
//        Intent intent = new Intent(getApplicationContext(), EditEventDialog.class);
//        intent.putExtra("Event Title", EventModel.getInstance().getEventInstance(position).getTitle());
//        intent.putExtra("Start Date", EventModel.getInstance().getEventInstance(position).getStartDate());
//        intent.putExtra("End Date", EventModel.getInstance().getEventInstance(position).getEndDate());
//        intent.putExtra("Venue", EventModel.getInstance().getEventInstance(position).getVenue());
//        intent.putExtra("Location", EventModel.getInstance().getEventInstance(position).getLocation());
//        intent.putExtra("Movie", EventModel.getInstance().getEventInstance(position).getMovie().getTitle());
        EditEventDialog editEventDialog = new EditEventDialog();

        Bundle args = new Bundle();
        args.putString("Event Title", EventModel.getInstance().getEventInstance(position).getTitle());
        args.putString("Start Date", EventModel.getInstance().getEventInstance(position).getStartDate());
        args.putString("End Date", EventModel.getInstance().getEventInstance(position).getEndDate());
        args.putString("Venue", EventModel.getInstance().getEventInstance(position).getVenue());
        args.putString("Location", EventModel.getInstance().getEventInstance(position).getLocation());
        if(EventModel.getInstance().getEventInstance(position).getMovie() != null) {
            args.putString("Movie", EventModel.getInstance().getEventInstance(position).getMovie().getTitle());
        }

        editEventDialog.setArguments(args);

        editEventDialog.show(getSupportFragmentManager(), "Edit Event");
    }
}
