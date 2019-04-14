package me.daviddoan.planner.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

import me.daviddoan.planner.R;
import me.daviddoan.planner.controller.AddEventsController;
import me.daviddoan.planner.model.EventModel;
import me.daviddoan.planner.model.MovieImpl;

public class AddEventsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final int REQUEST_CODE = 999;
    private EditText titleEditText, venueTextView, locationTextView;
    private TextView startTimeTextView, startDateTextView, endTimeTextView, endDateTextView;
    private Calendar startDate, endDate;
    private TextView activeDateTextView, activeTimeTextView, movieSelectedTextView;
    private Intent movieIntent;
    private AddEventsController addEventsController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);

        // Assigning the text views inside of this activity
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        startTimeTextView = (TextView)findViewById(R.id.startTimeTextView);
        startDateTextView = (TextView)findViewById(R.id.startDateTextView);
        endTimeTextView = (TextView)findViewById(R.id.endTimeTextView);
        endDateTextView = (TextView)findViewById(R.id.endDateTextView);
        venueTextView = (EditText) findViewById(R.id.venueEditText);
        locationTextView = (EditText) findViewById(R.id.venueEditText);
        Button selectMovieBtn = (Button) findViewById(R.id.selectMovieButton);
        movieSelectedTextView = (TextView) findViewById(R.id.movieSelectedTextView);
        Button saveEventBtn = (Button) findViewById(R.id.saveEventButton);

        startDate = Calendar.getInstance();
        addEventsController = new AddEventsController(this);

        startTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveTimeTextView(startTimeTextView);
                DialogFragment startTimePicker = new TimePickerFragment();
                startTimePicker.show(getSupportFragmentManager(), "Start Time");
            }
        });

        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveDateTextView(startDateTextView);
                DialogFragment startDatePicker = new DatePickerFragment();
                startDatePicker.show(getSupportFragmentManager(), "Start Date");
            }
        });

        endTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveTimeTextView(endTimeTextView);
                DialogFragment startTimePicker = new TimePickerFragment();
                startTimePicker.show(getSupportFragmentManager(), "Start Time");
            }
        });

        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveDateTextView(endDateTextView);
                DialogFragment endDatePicker = new DatePickerFragment();
                endDatePicker.show(getSupportFragmentManager(), "End Date");
            }
        });

        selectMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieIntent = new Intent(getApplicationContext(), SelectMovieActivity.class);
                startActivityForResult(movieIntent, REQUEST_CODE);
            }
        });

        saveEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = Integer.toString(EventModel.getInstance().getEventListSize() + 1);
                String title = titleEditText.getText().toString();
                String startTime = startTimeTextView.getText().toString();
                String startDate = startDateTextView.getText().toString() + " " + startTime;
                String endTime = endTimeTextView.getText().toString();
                String endDate = endDateTextView.getText().toString() + " " + endTime;
                String venue = venueTextView.getText().toString();
                String location = locationTextView.getText().toString();
                String movieTitle = movieSelectedTextView.getText().toString();
                MovieImpl movie;

                switch(movieTitle){
                    case "Blade Runner":
                        movie = new MovieImpl("1", "Blade Runner", "1982", "BladeRunner1982.jpg");
                        break;
                    case "Hackers":
                        movie = new MovieImpl("2", "Hackers", "1995", "Hackers.jpg");
                        break;
                    default:
                        movie = null;
                }
                addEventsController.addEvent(id, title, startDate, endDate, venue, location, movie);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode) {
            case REQUEST_CODE:
                if(resultCode == Activity.RESULT_OK) {
                    String text = data.getStringExtra("Movie Title");
                    movieSelectedTextView.setText(text);
                }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String dateString = DateFormat.getDateInstance().format(c.getTime());
        activeDateTextView.setText(dateString);
    }

    public void setActiveDateTextView(TextView textView) {
        this.activeDateTextView = textView;
    }

    public void setActiveTimeTextView(TextView textView) {
        this.activeTimeTextView = textView;
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        activeTimeTextView.setText(hourOfDay + " : " + minute);
    }
}


