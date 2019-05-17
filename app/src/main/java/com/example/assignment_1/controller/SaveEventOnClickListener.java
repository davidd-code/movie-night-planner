package com.example.assignment_1.controller;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_1.R;
import com.example.assignment_1.model.EventImpl;

import static com.example.assignment_1.model.EventModel.eventAdapter;

public class SaveEventOnClickListener implements View.OnClickListener {

    private Context context;
    private EventImpl currentEvent;


    public SaveEventOnClickListener(Context c, EventImpl event) {
        this.context = c;
        this.currentEvent = event;
    }

    @Override
    public void onClick(View v) {

        TextView eventName = ((Activity) context).findViewById(R.id.eventName);
        TextView eventVenue = ((Activity) context).findViewById(R.id.eventVenue);
        currentEvent.setTitle(String.valueOf(eventName.getText()));
        currentEvent.setVenue(String.valueOf(eventVenue.getText()));

        eventAdapter.notifyDataSetChanged();
        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        ((Activity)context).finish();
    }
}
