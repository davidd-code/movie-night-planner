package com.example.assignment_1.controller;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.assignment_1.R;
import com.example.assignment_1.model.EventImpl;
import com.example.assignment_1.view.ContactListFragment;

public class InviteOnClickListener implements View.OnClickListener {

    private Context context;
    private EventImpl currentEvent;


    public InviteOnClickListener(Context c, EventImpl currentEvent) {
        this.context = c;
        this.currentEvent = currentEvent;
    }

    @Override
    public void onClick(View v) {

        openFragment(currentEvent);
    }

    public void openFragment(EventImpl currentEvent) {
        ContactListFragment frag = ContactListFragment.newInstance(currentEvent);
        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_frame, frag, "ContactListFragment");
        transaction.commit();
    }
}
