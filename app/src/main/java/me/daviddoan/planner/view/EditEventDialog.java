package me.daviddoan.planner.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import me.daviddoan.planner.R;
import me.daviddoan.planner.model.EventImpl;
import me.daviddoan.planner.model.EventModel;

/**
 * This class is used when the user clicks on an event in the list of events from the main activity.
 * The dialog will also allow the user to edit the details of the event including the contacts
 * invited and the movie associated with the event.
 */
public class EditEventDialog extends AppCompatDialogFragment {
    public static final int REQUEST_CODE = 111;
    private EditText edit_eventTitle, edit_eventVenue, edit_eventLocation;
    private TextView edit_startDate, edit_startTime, edit_endDate, edit_endTime, edit_movieDisplay;
    private EditEventDialogListener listener;
    private TextView activeTextView;
    private FragmentActivity myContext;
    private Button contactsBtn, deleteEventBtn;


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
        contactsBtn = view.findViewById(R.id.contactsBtn);
        deleteEventBtn = view.findViewById(R.id.deleteEventBtn);

        // set up onclick listeners
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

        contactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactIntent = new Intent(getContext(), ViewContactsActivity.class);
                contactIntent.putExtra("currentEventId", getArguments().getString("ID"));
                startActivityForResult(contactIntent, 987);
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

        // Set delete button onclick listener
        deleteEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // alert the user with a warning before removing the event
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete Event?")
                        .setMessage("This action is permanent and cannot be undone")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes,
                                new DialogInterface.OnClickListener() {
                            // If user clicks ok on warning dialog, remove event from the list
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getContext(), "Event Deleted",
                                        Toast.LENGTH_SHORT).show();
                                EventImpl eventToDelete =
                                        EventModel.getInstance()
                                                .getEventFromId(getArguments().getString("ID"));
                                EventModel.getInstance().getController().deleteEvent(eventToDelete);
                                dismiss();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
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

    public interface EditEventDialogListener {
        void applyUpdate(String title, String startDate, String endDate, String venue, String location, String movieTitle);
    }
}
