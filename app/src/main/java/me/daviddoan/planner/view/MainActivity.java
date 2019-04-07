package me.daviddoan.planner.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import me.daviddoan.planner.R;
import me.daviddoan.planner.viewmodel.RecyclerListAdapter;
import me.daviddoan.planner.model.EventImpl;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<EventImpl> exampleList = new ArrayList<>();
        exampleList.add(new EventImpl
                ("1", "Freaky Friday", "2/01/2019 1:00:00 AM",
                        "2/01/2019 3:00:00 AM", "RMIT Capitol Theatre",
                        "-37.814795, 144.966119", null));
        exampleList.add(new EventImpl("2", "Scary Saturday", "3/01/2019 2:00:00 AM",
                "3/01/2019 4:00:00 AM", "HOYTS The District Docklands",
                "-37.811363, 144.936967", null));
//        exampleList.add(new EventImpl(R.drawable.ic_battery_90, "Line 5", "Line 6"));
//        exampleList.add(new EventImpl(R.drawable.ic_av_timer, "Line 3", "Line 4"));

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerListAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
