package me.daviddoan.planner.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import me.daviddoan.planner.R;
import me.daviddoan.planner.controller.EventsController;
import me.daviddoan.planner.model.EventModel;
import me.daviddoan.planner.model.MovieImpl;

public class AddEventsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final int SELECT_MOVIE = 999;
    public static final int SELECT_CONTACT = 101;
    private EditText titleEditText, venueTextView, locationTextView;
    private TextView startTimeTextView, startDateTextView, endTimeTextView, endDateTextView;
    private Calendar startDate, endDate;
    private Button addContactBtn;
    private TextView activeDateTextView, activeTimeTextView, movieSelectedTextView;
    private Intent movieIntent;
    private EventsController eventsController;
    private ArrayList<String[]> contacts = new ArrayList<>();


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
        addContactBtn = (Button) findViewById(R.id.addContactBtn);
        Button selectMovieBtn = (Button) findViewById(R.id.selectMovieButton);
        movieSelectedTextView = (TextView) findViewById(R.id.movieSelectedTextView);
        Button saveEventBtn = (Button) findViewById(R.id.saveEventButton);

        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        eventsController = new EventsController();

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
                startActivityForResult(movieIntent, SELECT_MOVIE);
            }
        });

        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(i, SELECT_CONTACT);
            }
        });

        saveEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = Integer.toString(EventModel.getInstance().getEventListSize() + 1);
                String title = titleEditText.getText().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
                dateFormat.setTimeZone(startDate.getTimeZone());
                String startDateString = dateFormat.format(startDate.getTime());
                String endDateString = dateFormat.format(endDate.getTime());
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

                eventsController.addEvent(id, title, startDateString, endDateString, venue, location, movie, contacts);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode) {
            case SELECT_MOVIE:
                if(resultCode == Activity.RESULT_OK) {
                    String text = data.getStringExtra("Movie Title");
                    movieSelectedTextView.setText(text);
                }
                break;
            case SELECT_CONTACT:
                if(resultCode == Activity.RESULT_OK) {
                    String[] newContact = new String[2];
                    Context context = getApplicationContext();
                    CharSequence text;
                    int duration = Toast.LENGTH_SHORT;
//                    Works for phone / name only
                    Uri contactUri = data.getData();
                    String[] nameProjection = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
                    Cursor nameCursor = getApplicationContext().getContentResolver().query(contactUri, nameProjection,
                            null, null, null);
                    String name="";
//                     If the cursor returned is valid, get the phone number
                    if(nameCursor != null && nameCursor.moveToFirst()) {
                        name = nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        newContact[0] = name;
                    }
                    String[] phoneProjection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
                    Cursor phoneCursor = getApplicationContext().getContentResolver().query(contactUri, phoneProjection,
                            null, null, null);
                    if(phoneCursor != null && phoneCursor.moveToFirst()) {
                        int phoneIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String phoneNo = phoneCursor.getString(phoneIndex);
                        newContact[1] = phoneNo;
                        if(!contacts.contains(newContact)) {
                            contacts.add(newContact);
                            text = name + " has been added to this event";
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        } else {
                            text = "That contact has already been invited to this event";
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }



                }
        }
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String displayDate;
        if(activeDateTextView == startDateTextView) {
            startDate.set(Calendar.YEAR, year);
            startDate.set(Calendar.MONTH, month);
            startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateFormat.setTimeZone(startDate.getTimeZone());
            displayDate = dateFormat.format(startDate.getTime());
        } else {
            endDate.set(Calendar.YEAR, year);
            endDate.set(Calendar.MONTH, month);
            endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            endDate.setTimeZone(endDate.getTimeZone());
            displayDate = dateFormat.format(endDate.getTime());
        }
        activeDateTextView.setText(displayDate);
    }

    public void setActiveDateTextView(TextView textView) {
        this.activeDateTextView = textView;
    }

    public void setActiveTimeTextView(TextView textView) {
        this.activeTimeTextView = textView;
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        if(activeTimeTextView == startTimeTextView) {
            if(hourOfDay > 12) {
                startDate.set(Calendar.HOUR, hourOfDay - 12);
                startDate.set(Calendar.AM_PM, 1);
            } else {
                startDate.set(Calendar.HOUR, hourOfDay);
                startDate.set(Calendar.AM_PM, 0);
            }
            startDate.set(Calendar.MINUTE, minute);
            dateFormat.setTimeZone(startDate.getTimeZone());
            activeTimeTextView.setText(dateFormat.format(startDate.getTime()));
        } else {
            if(hourOfDay > 12) {
                endDate.set(Calendar.HOUR, hourOfDay - 12);
                endDate.set(Calendar.AM_PM, 1);
            } else {
                endDate.set(Calendar.HOUR, hourOfDay);
                endDate.set(Calendar.AM_PM, 0);
            }
            endDate.set(Calendar.MINUTE, minute);
            dateFormat.setTimeZone(endDate.getTimeZone());
            activeTimeTextView.setText(dateFormat.format(endDate.getTime()));
        }
    }
}


