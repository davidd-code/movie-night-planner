package com.example.assignment_1.model;

import java.util.Collections;
import java.util.Comparator;

import static com.example.assignment_1.model.EventModel.eventAdapter;
import static com.example.assignment_1.model.EventModel.events;

public class CustomComparator implements Comparator<EventImpl> {
    @Override
    public int compare(EventImpl event1, EventImpl event2) {
        return event1.getStartDate().compareTo(event2.getStartDate());
    }

    public void sortAscending() {
        Collections.sort(events, this.reversed());
        eventAdapter.notifyDataSetChanged();
    }

    public void sortDescending() {
        Collections.sort(events, this);
        eventAdapter.notifyDataSetChanged();
    }
}
