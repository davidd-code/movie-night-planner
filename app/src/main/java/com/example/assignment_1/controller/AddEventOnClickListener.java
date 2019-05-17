package com.example.assignment_1.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.assignment_1.model.EventImpl;
import com.example.assignment_1.view.AddEditEventActivity;
import com.example.assignment_1.viewModel.EventListAdapter;

import static com.example.assignment_1.model.EventModel.events;

public class AddEventOnClickListener implements View.OnClickListener {

    private Context context;
    private EventListAdapter adapter;

    public AddEventOnClickListener(Context c, EventListAdapter a) {
        this.context = c;
        this.adapter = a;
    }

    @Override
    public void onClick(View v) {
        int index = events.size();

        events.add(index, new EventImpl());
        Intent intent = new Intent(context, AddEditEventActivity.class);
        intent.putExtra("ITEM_INDEX", index);
        context.startActivity(intent);
        adapter.notifyItemInserted(index);
    }
}
