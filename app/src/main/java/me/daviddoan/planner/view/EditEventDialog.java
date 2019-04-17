package me.daviddoan.planner.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

import me.daviddoan.planner.R;

public class EditEventDialog extends AppCompatDialogFragment {
    public static final int REQUEST_CODE = 111;
    private EditText edit_eventTitle, edit_eventVenue, edit_eventLocation;
    private TextView edit_startDate, edit_startTime, edit_endDate, edit_endTime, edit_movieDisplay;
    private EditEventDialogListener listener;
    private TextView activeTextView;
    private FragmentActivity myContext;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_event, null);

        builder.setView(view).setTitle("Edit Event").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = edit_eventTitle.getText().toString();
                String startTime = edit_startDate.getText().toString() + " " + edit_startTime.getText().toString();
                String endTime = edit_endDate.getText().toString() + " " + edit_endTime.getText().toString();
                String venue = edit_eventVenue.getText().toString();
                String location = edit_eventLocation.getText().toString();
                String movieTitle = edit_movieDisplay.getText().toString();
                listener.applyUpdate(title, startTime, endTime, venue, location, movieTitle);
            }
        });

        // Assign view items
        edit_eventTitle = view.findViewById(R.id.edit_eventTitle);
        edit_eventVenue = view.findViewById(R.id.edit_eventVenue);
        edit_eventLocation = view.findViewById(R.id.edit_eventLocation);
        edit_startDate = view.findViewById(R.id.edit_startDate);
        edit_startTime = view.findViewById(R.id.edit_startTime);
        edit_endDate = view.findViewById(R.id.edit_endDate);
        edit_endTime = view.findViewById(R.id.edit_endTime);
        edit_movieDisplay = view.findViewById(R.id.edit_movieDisplay);
        Button edit_eventMovieBtn = view.findViewById(R.id.edit_eventMovieBtn);

        edit_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveTextView(edit_startDate);
                DialogFragment startDatePicker = new DatePickerFragment();
                startDatePicker.show(myContext.getSupportFragmentManager(), "Edit Date");
            }
        });

        edit_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveTextView(edit_endDate);
                DialogFragment endDatePicker = new DatePickerFragment();
                endDatePicker.show(myContext.getSupportFragmentManager(), "Edit Date");
            }
        });

        edit_startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveTextView(edit_startTime);
                DialogFragment startTimePicker = new TimePickerFragment();
                startTimePicker.show(myContext.getSupportFragmentManager(), "Start Time");
            }
        });

        edit_endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveTextView(edit_endTime);
                DialogFragment startTimePicker = new TimePickerFragment();
                startTimePicker.show(myContext.getSupportFragmentManager(), "End Time");
            }
        });

        // Set Edit Movie button to open new activity
        edit_eventMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movieIntent = new Intent(getContext(), SelectMovieActivity.class);
                startActivityForResult(movieIntent, REQUEST_CODE);
            }
        });

        // Set view items to the values of the event that is being edited
        edit_eventTitle.setText(getArguments().getString("Event Title"));
        edit_eventVenue.setText(getArguments().getString("Venue"));
        edit_eventLocation.setText(getArguments().getString("Location"));
        String[] startDateArray = getArguments().getString("Start Date").split(" ");
        edit_startDate.setText(startDateArray[0]);
        String startTime = startDateArray[1] + " " + startDateArray[2];
        edit_startTime.setText(startTime);
        String[] endDateArray = getArguments().getString("End Date").split(" ");
        edit_endDate.setText(endDateArray[0]);
        String endDate = endDateArray[1] + " " + endDateArray[2];
        edit_endTime.setText(endDate);
        edit_movieDisplay.setText(getArguments().getString("Movie"));

        return builder.create();

    }

    // Sets the movie selected for current event
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case REQUEST_CODE:
                if(resultCode == Activity.RESULT_OK) {
                    String text = data.getStringExtra("Movie Title");
                    edit_movieDisplay.setText(text);
                }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myContext =(FragmentActivity) context;
        try {
            listener = (EditEventDialogListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement EditEventDialogListener");
        }
    }

    public void setActiveTextView(TextView textView) {
        this.activeTextView = textView;
    }

    public TextView getActiveTextView() {
        return this.activeTextView;
    }

    public void showNewDate(TextView textView, String dateString) {
        textView.setText(dateString);
    }


    public interface EditEventDialogListener {
        void applyUpdate(String title, String startDate, String endDate, String venue, String location, String movieTitle);
    }
}
