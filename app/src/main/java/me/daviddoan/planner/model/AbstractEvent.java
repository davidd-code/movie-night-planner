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

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getStartDate() {
        return this.startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return this.endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String getVenue() {
        return this.venue;
    }

    @Override
    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public Movie getMovie() {
        return this.movie;
    }

    @Override
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
