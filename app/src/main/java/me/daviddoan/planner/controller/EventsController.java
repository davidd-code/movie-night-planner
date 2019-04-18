package me.daviddoan.planner.controller;

import java.util.ArrayList;

import me.daviddoan.planner.model.EventImpl;
import me.daviddoan.planner.model.EventModel;
import me.daviddoan.planner.model.interfaces.Movie;
import me.daviddoan.planner.view.AddEventsActivity;

public class EventsController {
    private EventModel model;
    private AddEventsActivity view;

    public EventsController() {
        this.model = EventModel.getInstance();
    }

    public void addEvent(String id, String title, String startDate, String endDate, String venue,
                         String location, Movie movie, ArrayList<String[]> attendees) {
        EventImpl newEvent = new EventImpl(id, title, startDate, endDate, venue, location, movie);
        newEvent.setAttendees(attendees);
        this.model.addEvent(newEvent);
    }

    public void editEvent(String id, String title, String startDate, String endDate, String venue, String location, String movieTitle) {
        this.model.editEvent(id, title, startDate, endDate, venue, location, movieTitle);
    }
}
