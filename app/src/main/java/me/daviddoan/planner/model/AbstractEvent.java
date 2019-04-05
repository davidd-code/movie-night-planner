package me.daviddoan.planner.model;

import me.daviddoan.planner.model.interfaces.Event;
import me.daviddoan.planner.model.interfaces.Movie;

public class AbstractEvent implements Event {

    private String id, title, startDate, endDate, venue, location;
    private Movie movie;

    public AbstractEvent(String id, String title, String startDate, String endDate, String venue,
                         String location, Movie movie) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venue = venue;
        this.location = location;
        this.movie = movie;
    }
}
