package me.daviddoan.planner.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

import me.daviddoan.planner.R;

//public class AddEventsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

public class AddEventsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextView startTimeTextView, startDateTextView, endTimeTextView, endDateTextView;
    private Calendar startDate, endDate;
    private TextView activeDateTextView, activeTimeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);

        DatePickerDialog.OnDateSetListener start_dateListener, end_dateListener;


        // Assigning the text views inside of this activity
        startTimeTextView = (TextView)findViewById(R.id.startTimeTextView);
        startDateTextView = (TextView)findViewById(R.id.startDateTextView);
        endTimeTextView = (TextView)findViewById(R.id.endTimeTextView);
        endDateTextView = (TextView)findViewById(R.id.endDateTextView);
        Button selectMovieBtn = (Button) findViewById(R.id.selectMovieButton);
        Button saveEventBtn = (Button) findViewById(R.id.saveEventButton);

        startDate = Calendar.getInstance();

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
                Intent movieIntent = new Intent(getApplicationContext(), SelectMovieActivity.class);
                startActivity(movieIntent);
            }
        });
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


