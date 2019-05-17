package com.example.assignment_1.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_1.R;
import com.example.assignment_1.controller.DateTimePickOnClickListener;
import com.example.assignment_1.controller.InviteOnClickListener;
import com.example.assignment_1.controller.SelectMovieOnClickListener;
import com.example.assignment_1.model.EventImpl;
import com.example.assignment_1.model.MovieImpl;

import static com.example.assignment_1.controller.SelectMovieOnClickListener.MOVIE_REQUEST_CODE;
import static com.example.assignment_1.model.EventModel.eventAdapter;
import static com.example.assignment_1.model.EventModel.events;
import static com.example.assignment_1.model.EventModel.movies;


//implementation of GetLocationDialog temporary until assignment 2
public class AddEditEventActivity extends AppCompatActivity implements GetLocationDialog.LocationDialogListener {

    private TextView eventName, venueName, movieName,startDate, endDate, numAttendees;
    private ImageView startDateButton, endDateButton, selectMovieButton, locationButton, inviteButton;

    private EventImpl currentEvent;
    private int eventIndex;

    private Toolbar toolbar;
    private FrameLayout fragment_frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        toolbar = findViewById(R.id.event_toolbar);
        setSupportActionBar(toolbar);

        requestPermissions();
        fragment_frame = findViewById(R.id.fragment_frame);
        setButtons();
        checkForActivityResult(eventIndex);
        setListeners();


        temp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(!events.contains(currentEvent)){
            events.add(currentEvent);
        }
        switch(item.getItemId()) {

            case R.id.save_event:
                saveEvent();
                this.finish();
                break;
            case R.id.save_close_event:
                saveEvent();
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveEvent(){
        currentEvent.setTitle(String.valueOf(eventName.getText()));
        currentEvent.setVenue(String.valueOf(venueName.getText()));
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        eventAdapter.notifyDataSetChanged();
    }

    public void checkForActivityResult(int index){
        if(index > -1){
            Intent intent = getIntent();
            eventIndex = intent.getIntExtra("ITEM_INDEX", -1);
            if(eventIndex == events.size()){
                currentEvent = new EventImpl();
            } else {
                currentEvent = events.get(eventIndex);
            }
            setTextDetails();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intentData){
        super.onActivityResult(requestCode, resultCode, intentData);
        if(requestCode == MOVIE_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK) {
                eventIndex = intentData.getIntExtra("EVENT_INDEX", -1);
//                currentEvent = events.get(eventIndex);
                int position = intentData.getIntExtra("MOVIE_INDEX", -1);
                currentEvent.setChosenMovie(movies.get(position));
                setTextDetails();
            }
        }
    }

    public void setTextDetails(){
        eventName = findViewById(R.id.eventName);
        venueName = findViewById(R.id.venueName);
        movieName = findViewById(R.id.newMovie);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        numAttendees = findViewById(R.id.numAttendees);

        if(currentEvent.getTitle() != null){
            eventName.setText(currentEvent.getTitle());
        }
        if(currentEvent.getVenue() != null) {
            venueName.setText(currentEvent.getVenue());
        }
        if(currentEvent.getChosenMovie() != null){
            MovieImpl chosenMovie = currentEvent.getChosenMovie();
            movieName.setText(chosenMovie.getTitle());
        }
        if(currentEvent.getStartDate() != null) {
            startDate.setText(currentEvent.stringFormatLocalDateTime(currentEvent.getStartDate()));
            endDateButton.setEnabled(true);
        }
        if(currentEvent.getEndDate() != null) {
            endDate.setText(currentEvent.stringFormatLocalDateTime(currentEvent.getEndDate()));
        }
        if(currentEvent.getNumAttendees() > 0){
            numAttendees.setText(Integer.toString(currentEvent.getNumAttendees()));
        }
    }

    public void setButtons(){
        startDateButton = findViewById(R.id.startDateButton);
        endDateButton = findViewById(R.id.endDateButton);
        selectMovieButton = findViewById(R.id.newMovieButton);
        inviteButton = findViewById(R.id.inviteButton);

        locationButton = findViewById(R.id.addLocationButton);
    }

    public void setListeners(){
        startDateButton.setOnClickListener(new DateTimePickOnClickListener(this, eventIndex, currentEvent));
        endDateButton.setOnClickListener(new DateTimePickOnClickListener(this, eventIndex, currentEvent));
        endDateButton.setEnabled(false);
        movieName.setOnClickListener(new SelectMovieOnClickListener(this, eventIndex));
        selectMovieButton.setOnClickListener(new SelectMovieOnClickListener(this, eventIndex));
        inviteButton.setOnClickListener(new InviteOnClickListener(this, eventIndex));
        //numAttendees.setOnClickListener();
    }

    public void requestPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 53);
    }


    //temporary code below this line
    public void temp(){
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog() {
        GetLocationDialog locationDialog = new GetLocationDialog();
        Bundle args = new Bundle();
        String[] location = currentEvent.getLocation().split(",");
        args.putString("latitude", location[0]);
        args.putString("longitude", location[1]);
        locationDialog.setArguments(args);
        locationDialog.show(getSupportFragmentManager(), "get location");
    }

    @Override
    public void applyTexts(String location) {
        currentEvent.setLocation(location);
        Toast.makeText(this, "Location Updated", Toast.LENGTH_SHORT).show();
    }


}
