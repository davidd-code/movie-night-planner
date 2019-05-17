package com.example.assignment_1.model;

import java.util.Comparator;

public class CustomComparator implements Comparator<EventImpl> {
    @Override
    public int compare(EventImpl event1, EventImpl event2) {
        return event1.getStartDate().compareTo(event2.getStartDate());
    }
}
