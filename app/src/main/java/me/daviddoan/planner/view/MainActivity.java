package me.daviddoan.planner.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.daviddoan.planner.R;
import me.daviddoan.planner.adapter.EventRecyclerListAdapter;
import me.daviddoan.planner.controller.EventsController;
import me.daviddoan.planner.model.EventModel;
import me.daviddoan.planner.model.FileLoader;
import me.daviddoan.planner.viewmodel.AppViewModel;

/**
 * This class is the main activity that the app will be launched from. It will display the list
 * of events that have been added to the system. This class uses the EventRecyclerListAdapter
 * class for the recycler list.
 */
public class MainActivity extends AppCompatActivity implements
        EventRecyclerListAdapter.EventRecyclerListListener, EditEventDialog.EditEventDialogListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditEventDialog editEventDialog;
    String editEventId;
    Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Use the custom toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        RecyclerView.Adapter mAdapter;
        RecyclerView mRecyclerView;
        AppViewModel mAppViewModel;
        RecyclerView.LayoutManager mLayoutManager;

        // Set button for adding new events to the list of events
        ImageView addEventsBtn = (ImageView)findViewById(R.id.addEventsBtn);
        addEventsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the add events activity when this button is clicked
                Intent startIntent = new Intent(getApplicationContext(), AddEventsActivity.class);
                startActivity(startIntent);
            }
        });

        // Set up ViewModel object
        mAppViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        mAppViewModel.init();

        // Set up recycler list
        EventModel.getInstance().setEventRecyclerViewAdapter(this);
        mRecyclerView = findViewById(R.id.eventsRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = EventModel.getInstance().getEventAdapter();

        // Load events and movies from file
        FileLoader fileLoader = new FileLoader(this);
        fileLoader.loadEvents(EventModel.getInstance().getEventList().getValue());
        fileLoader.loadMovies(EventModel.getInstance().getMovieList().getValue());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // Set button to sort events by date
        Button sortBtn = (Button) findViewById(R.id.sortBtn);
        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sorts the list in ascending or descending order when button clicked
                if(EventModel.getInstance().getController().isListSortedAsc()) {
                    EventModel.getInstance().getController().sortDescending();
                } else {
                    EventModel.getInstance().getController().sortAscending();
                }
            }
        });
    }

    // When an event item in the recycler list is clicked
    @Override
    public void onEventClick(int position) {
        openEditDialog(position);
    }

    // Opens the edit event dialog which shows the details of each event
    public void openEditDialog(int position) {
        editEventDialog = new EditEventDialog();
        // Sends the details of the current event to the edit dialog
        args = new Bundle();
        // Sends information of the even clicked to the edit event dialog
        editEventId = EventModel.getInstance().getEventInstance(position).getId();
        args.putString("ID", EventModel.getInstance().getEventInstance(position).getId());
        args.putString("Event Title",
                EventModel.getInstance().getEventInstance(position).getTitle());
        args.putString("Start Date",
                EventModel.getInstance().getEventInstance(position).getStartDateString());
        args.putString("End Date",
                EventModel.getInstance().getEventInstance(position).getEndDateString());
        args.putString("Venue", EventModel.getInstance().getEventInstance(position).getVenue());
        args.putString("Location",
                EventModel.getInstance().getEventInstance(position).getLocation());
        // If the event clicked has a movie associated with it
        if(EventModel.getInstance().getEventInstance(position).getMovie() != null) {
            args.putString("Movie",
                    EventModel.getInstance().getEventInstance(position).getMovie().getTitle());
        }

        editEventDialog.setArguments(args);
        // open edit dialog
        editEventDialog.show(getSupportFragmentManager(), "Edit Event");
    }

    // applies update to the event selected
    @Override
    public void applyUpdate
    (String title, String startDate, String endDate, String venue,
     String location, String movieTitle) {
        EventsController controller = new EventsController();
        controller.editEvent(editEventId,title, startDate, endDate, venue, location, movieTitle);
    }

    // receives the user input from the edit dialog and updates the time of the event being editted
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String displayDate;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dateFormat.setTimeZone(calendar.getTimeZone());
        displayDate = dateFormat.format(calendar.getTime());
        editEventDialog.getActiveTextView().setText(displayDate);
    }
    // same as function above
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();

        if(hourOfDay > 12) {
            calendar.set(Calendar.HOUR, hourOfDay - 12);
            calendar.set(Calendar.AM_PM, 1);
        } else {
            calendar.set(Calendar.HOUR, hourOfDay);
            calendar.set(Calendar.AM_PM, 0);
        }
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        dateFormat.setTimeZone(calendar.getTimeZone());
        editEventDialog.getActiveTextView().setText(dateFormat.format(calendar.getTime()));
    }
}
