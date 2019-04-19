package me.daviddoan.planner.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import me.daviddoan.planner.model.EventImpl;
import me.daviddoan.planner.model.EventModel;
import me.daviddoan.planner.model.MovieImpl;
import me.daviddoan.planner.model.interfaces.Movie;

/**
 * This class is responsible for making changes to the EventModel
 */
public class EventsController {
    boolean sorted, sortedAsc;

    public EventsController() {
        this.sorted = false;
    }

    public void addEvent(String id, String title, String startDate, String endDate, String venue,
                         String location, Movie movie, ArrayList<String[]> attendees) {
        EventImpl newEvent = new EventImpl(id, title, startDate, endDate, venue, location, movie);
        newEvent.setAttendees(attendees);
        EventModel.getInstance().getEventList().getValue().add(newEvent);
        EventModel.getInstance().notifyEventAdapter();
    }
    // Edit currently existing event
    public void editEvent(String id, String title, String startDate, String endDate, String venue, String location, String movieTitle) {
        for(EventImpl eventElement: EventModel.getInstance().getEventList().getValue()) {
            if(eventElement.getId().equals(id)) {
                eventElement.setTitle(title);
                eventElement.setStartDate(startDate);
                eventElement.setEndDate(endDate);
                eventElement.setVenue(venue);
                eventElement.setLocation(location);
                for(MovieImpl movieElement: EventModel.getInstance().getMovieList().getValue()) {
                    if(movieElement.getTitle().equals(movieTitle)) {
                        eventElement.setMovie(movieElement);
                    }
                }
            }
        }
        EventModel.getInstance().notifyEventAdapter();
    }

    private class EventComparator implements Comparator<EventImpl> {

        @Override
        public int compare(EventImpl event1, EventImpl event2) {
            if(event1.getStartDate().before(event2.getStartDate())) {
                return -1;
            }
            if(event1.getStartDate().before(event2.getStartDate())){
                return 1;
            }
            return 0;
        }
    }
    public boolean isListSortedAsc() {
        return sortedAsc;
    }

    public void sortAscending() {
        sortedAsc = true;
        EventComparator ec = new EventComparator();
        Collections.sort(EventModel.getInstance().getEventList().getValue(), ec.reversed());
        EventModel.getInstance().notifyEventAdapter();
    }

    public void sortDescending() {
        sortedAsc = false;
        EventComparator ec = new EventComparator();
        Collections.sort(EventModel.getInstance().getEventList().getValue(), ec);
        EventModel.getInstance().notifyEventAdapter();
    }

    public void deleteEvent(EventImpl eventToDelete) {
        if(EventModel.getInstance().getEventList().getValue().contains(eventToDelete)) {
            EventModel.getInstance().getEventList().getValue().remove(eventToDelete);
            EventModel.getInstance().notifyEventAdapter();
        }
    }
}
