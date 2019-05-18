package com.example.assignment_1.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.assignment_1.model.EventImpl;
import com.example.assignment_1.viewModel.ContactListAdapter;

import static com.example.assignment_1.model.EventModel.contacts;
import static com.example.assignment_1.model.EventModel.eventAdapter;
import static com.example.assignment_1.model.EventModel.events;

public class ContactItemClickListener implements ContactListAdapter.OnItemClickListener, View.OnClickListener {

    private Context context;
    private int eventIndex;
    private EventImpl currentEvent;

    public ContactItemClickListener(Context c, EventImpl currentEvent) {
//        this.eventIndex = i;
        this.currentEvent = currentEvent;
        this.context = c;
    }

    @Override
    public void onItemClick(int pos, int event_index) {
        if( !currentEvent.isAttending(contacts.get(pos)) ){
            currentEvent.addAttendees(contacts.get(pos));
        }else{
            currentEvent.removeAttendee(contacts.get(pos));
        }
    }

    public void onConfirmButtonClick(){
        Intent intent = new Intent();
        intent.putExtra("EVENT_INDEX", eventIndex);
        ((Activity) context).setResult(Activity.RESULT_OK, intent);
//        ((Activity)context).finish();
        ((Activity)context).onBackPressed();


    }

    @Override
    public void onClick(View v) {
        onConfirmButtonClick();
    }

    @Override
    public int getEventIndex(){
        return this.eventIndex;
    }
}
