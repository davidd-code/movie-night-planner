package me.daviddoan.planner.controller;

import me.daviddoan.planner.model.EventImpl;
import me.daviddoan.planner.model.EventModel;
import me.daviddoan.planner.model.interfaces.Movie;
import me.daviddoan.planner.view.AddEventsActivity;

public class AddEventsController {
    private EventModel model;
    private AddEventsActivity view;

    public AddEventsController(AddEventsActivity view) {
        this.model = EventModel.getInstance();
        this.view = view;
    }

    public void addEvent(String id, String title, String startDate, String endDate, String venue, String location, Movie movie) {
        EventImpl newEvent = new EventImpl(id, title, startDate, endDate, venue, location, movie);
        this.model.addEvent(newEvent);
    }
}
