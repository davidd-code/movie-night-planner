package me.daviddoan.planner.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import me.daviddoan.planner.R;
import me.daviddoan.planner.model.EventImpl;

public class EditEventDialog extends AppCompatDialogFragment {
    EditText edit_eventTitle, edit_eventVenue, edit_eventLocation;
    TextView edit_startDate, edit_startTime, edit_endDate, edit_endTime, edit_movieDisplay;
    Button edit_eventMovieBtn;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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
        edit_eventMovieBtn = view.findViewById(R.id.edit_eventMovieBtn);

//        Intent intent = getActivity().getIntent();
//        if(intent.hasExtra("Event Title")) {
//            edit_eventTitle.setText(intent.getStringExtra("Event Title"));
//        }

        return builder.create();

    }
}
