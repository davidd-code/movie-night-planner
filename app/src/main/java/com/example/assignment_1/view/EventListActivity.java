package com.example.assignment_1.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.assignment_1.R;
import com.example.assignment_1.controller.AddEventOnClickListener;
import com.example.assignment_1.controller.EditEventOnClickListener;
import com.example.assignment_1.data.DatabaseHelper;
import com.example.assignment_1.model.CustomComparator;

import java.util.Collections;

import static com.example.assignment_1.model.EventModel.eventAdapter;
import static com.example.assignment_1.model.EventModel.events;

public class EventListActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    private RecyclerView eRecyclerView;
    private RecyclerView.LayoutManager eLayoutManager;
    private Toolbar toolbar;
    private ImageView addEventButton;
    private Button calendarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        toolbar = findViewById(R.id.event_list_toolbar);
        setSupportActionBar(toolbar);
        buildRecyclerView();
        setButtons();

        dbHelper = DatabaseHelper.getHelper(this);
        dbHelper.open();
        dbHelper.close();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        eventAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                dbHelper.syncDatabase();
            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //eventAdapter.setOnItemClickListener(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.sort_ascending:
                sortAscending();
                Toast.makeText(this, "Ascending", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sort_descending:
                sortDescending();
                Toast.makeText(this, "Descending", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setButtons() {
        addEventButton = findViewById(R.id.addEventButton);
        calendarButton = findViewById(R.id.calendar_button);
        addEventButton.setOnClickListener(new AddEventOnClickListener(this, eventAdapter));
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventAdapter.notifyDataSetChanged();
                calendarButton.setText(Integer.toString(eventAdapter.getItemCount()));
            }
        });
    }

    public void buildRecyclerView(){
        eRecyclerView = findViewById(R.id.eRecyclerView);
        eRecyclerView.setHasFixedSize(true);
        eLayoutManager = new LinearLayoutManager(this);

        eRecyclerView.setLayoutManager(eLayoutManager);
        eRecyclerView.setAdapter(eventAdapter);

        eventAdapter.setOnItemClickListener(new EditEventOnClickListener(this, eventAdapter));
    }

    public void sortAscending(){
        CustomComparator c = new CustomComparator();
        Collections.sort(events, c.reversed());
        eventAdapter.notifyDataSetChanged();
    }

    public void sortDescending(){
        Collections.sort(events, new CustomComparator());
        eventAdapter.notifyDataSetChanged();
    }

}
