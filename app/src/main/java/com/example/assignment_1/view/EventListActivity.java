package com.example.assignment_1.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.assignment_1.R;
import com.example.assignment_1.controller.AddEventOnClickListener;
import com.example.assignment_1.controller.EditEventOnClickListener;
import com.example.assignment_1.controller.MapOnClickListener;
import com.example.assignment_1.model.CustomComparator;
import com.example.assignment_1.model.EventModel;
import com.example.assignment_1.model.FileLoader;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Collections;

import static com.example.assignment_1.model.EventModel.eventAdapter;
import static com.example.assignment_1.model.EventModel.events;

public class EventListActivity extends AppCompatActivity {

    private FileLoader fl = new FileLoader();

    private RecyclerView eRecyclerView;
    private RecyclerView.LayoutManager eLayoutManager;
    private Toolbar toolbar;
    private ImageView addEventButton;
    private Button calendarButton;

    private static final String TAG="EventListActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        toolbar = findViewById(R.id.event_list_toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState == null) {
            loadData();
        }



        buildRecyclerView();
        setButtons();

        if(isServicesOK()) {
            init();
        }
    }

    private void init() {

    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(EventListActivity.this);

        if(available == ConnectionResult.SUCCESS) {
            //everything is fine and user can make map requests
            Log.d(TAG, "isServicesOK: Google Play services is working");
            return true;
        } else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(EventListActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CustomComparator c = new CustomComparator();
        switch(item.getItemId()){
            case R.id.sort_ascending:
                c.sortAscending();
                Toast.makeText(this, "Ascending", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sort_descending:
                c.sortDescending();
                Toast.makeText(this, "Descending", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadData(){
        InputStream isEvents = getResources().openRawResource(R.raw.events);
        InputStream isMovies = getResources().openRawResource(R.raw.movies);
        BufferedReader brEvents = new BufferedReader(new InputStreamReader(isEvents));
        BufferedReader brMovies = new BufferedReader(new InputStreamReader(isMovies));

        try{
            EventModel.loadEvents(fl.loadFile(brEvents));
            EventModel.loadMovies(fl.loadFile(brMovies), this);
        }catch(ParseException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                brEvents.close();
                brMovies.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void setButtons() {
        addEventButton = findViewById(R.id.addEventButton);
        calendarButton = findViewById(R.id.calendar_button);
        ImageView mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new MapOnClickListener(this));
        addEventButton.setOnClickListener(new AddEventOnClickListener(this, eventAdapter));
    }

    public void buildRecyclerView(){
        eRecyclerView = findViewById(R.id.eRecyclerView);
        eRecyclerView.setHasFixedSize(true);
        eLayoutManager = new LinearLayoutManager(this);

        eRecyclerView.setLayoutManager(eLayoutManager);
        eRecyclerView.setAdapter(eventAdapter);

        eventAdapter.setOnItemClickListener(new EditEventOnClickListener(this, eventAdapter));
    }

}
