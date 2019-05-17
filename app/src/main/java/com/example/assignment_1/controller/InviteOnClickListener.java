package com.example.assignment_1.controller;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.assignment_1.R;
import com.example.assignment_1.view.ContactListFragment;

public class InviteOnClickListener implements View.OnClickListener {

    private Context context;
    private int eventIndex;

    public InviteOnClickListener(Context c, int i) {
        this.context = c;
        this.eventIndex = i;
    }

    @Override
    public void onClick(View v) {

        openFragment(eventIndex);
    }

    public void openFragment(int eventIndex) {
        ContactListFragment frag = ContactListFragment.newInstance(eventIndex);
        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_frame, frag, "ContactListFragment");
        transaction.commit();
    }
}
