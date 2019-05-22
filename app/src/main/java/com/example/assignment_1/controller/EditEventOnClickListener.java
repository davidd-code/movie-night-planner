package com.example.assignment_1.controller;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.assignment_1.data.DatabaseHelper;
import com.example.assignment_1.view.AddEditEventActivity;
import com.example.assignment_1.viewModel.EventListAdapter;

import static com.example.assignment_1.model.EventModel.events;

public class EditEventOnClickListener implements EventListAdapter.OnItemClickListener {

    private Context context;
    private EventListAdapter adapter;
    private DatabaseHelper dbHelper;

    public EditEventOnClickListener(Context c, EventListAdapter a) {
        this.context = c;
        this.adapter = a;
        dbHelper = DatabaseHelper.getHelper(context);
    }

    @Override
    public void onItemClick(int position) {
        //
        Intent intent = new Intent(context, AddEditEventActivity.class);
        intent.putExtra("ITEM_INDEX", position);
        context.startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        //
        events.remove(position);
        dbHelper.deleteEvent(Integer.toString(position));
        Toast.makeText(context, "Event Deleted", Toast.LENGTH_SHORT).show();
        adapter.notifyItemRemoved(position);
    }
}
