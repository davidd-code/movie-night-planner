package com.example.assignment_1.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.assignment_1.view.EventLocationActivity;

public class MapOnClickListener implements View.OnClickListener{

    private Context context;

    public MapOnClickListener(Context c) {
        this.context = c;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, EventLocationActivity.class);
        context.startActivity(intent);
    }
}
