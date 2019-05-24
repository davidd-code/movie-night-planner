package com.example.assignment_1.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.assignment_1.data.DatabaseHelper;
import com.example.assignment_1.model.Contact;
import com.example.assignment_1.model.EventImpl;
import com.example.assignment_1.viewModel.ContactListAdapter;

import static com.example.assignment_1.model.EventModel.contacts;
import static com.example.assignment_1.view.EventListActivity.update;

public class ContactItemClickListener implements ContactListAdapter.OnItemClickListener, View.OnClickListener {

    private Context context;
    private int eventIndex;
    private EventImpl currentEvent;
    private DatabaseHelper dbHelper;

    private static final String TAG = "ContactItemClickListener";

    public ContactItemClickListener(Context c, EventImpl event) {
        //this.eventIndex = i;
        this.context = c;
        this.currentEvent = event;
        dbHelper = DatabaseHelper.getHelper(context);
    }

    @Override
    public void onItemClick(int pos, int event_index) {
        final Contact currentContact = contacts.get(pos);

        if( !currentEvent.isAttending(currentContact) ){
            currentEvent.addAttendees(currentContact);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dbHelper.addAttendee(currentEvent, currentContact);
                }
            }).start();
        }else{
            Log.d(TAG, "Remove Attendee Clicked");
            for (Contact attendee : currentEvent.getAttendees()) {
                System.out.println("Attendees:\n"+attendee.getID()+", "+attendee.getFullName()+", "+attendee.getPhone());
            }
            System.out.println("CurrentContact:\n"+currentContact.getID()+", "+currentContact.getFullName()+", "+currentContact.getPhone());

            if(currentEvent.removeAttendee(currentContact))
                Log.d(TAG, "removed attendee");
            else
                Log.d(TAG, "attendee NOT removed");

            Log.d(TAG, "Attendees: " + currentEvent.getNumAttendees());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dbHelper.removeAttendee(currentEvent, currentContact);
                }
            }).start();
        }

    }

    public void onConfirmButtonClick(){
        System.out.println(currentEvent.getTitle());
        Intent intent = new Intent();
        intent.putExtra("EVENT_INDEX", eventIndex);
        ((Activity) context).setResult(Activity.RESULT_OK, intent);
        ((Activity)context).onBackPressed();
        //eventAdapter.notifyDataSetChanged();
        context.sendBroadcast(update);

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
