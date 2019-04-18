package me.daviddoan.planner.model;

import android.arch.lifecycle.MutableLiveData;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import me.daviddoan.planner.adapter.EventRecyclerListAdapter;
import me.daviddoan.planner.view.MainActivity;

public class EventModel {
    private static EventModel firstInstance = null;
    private ArrayList<EventImpl> eventList;
    private ArrayList<MovieImpl> movieList;
    private RecyclerView.Adapter mEventAdapter;

    private EventModel(){
        if(firstInstance == null) {
            eventList = new ArrayList<>();
            movieList = new ArrayList<>();
        }
    }

    public static EventModel getInstance(){
        if(firstInstance == null){
            firstInstance = new EventModel();

        }
        return firstInstance;
    }

    public void setEventRecyclerViewAdapter(MainActivity activity) {
        mEventAdapter = new EventRecyclerListAdapter(eventList, activity);
    }



    public MutableLiveData<ArrayList<EventImpl>> getEventList() {

        MutableLiveData<ArrayList<EventImpl>> data = new MutableLiveData<>();
        data.setValue(eventList);

        return data;
    }

    public ArrayList<String[]> getContactsFromId(String id) {
        ArrayList<String[]> attendees = new ArrayList<>();
        for(EventImpl event: this.getEventList().getValue()) {
            if(event.getId().equals(id)) {
                attendees = event.getAttendees();
            }
        }
        return attendees;
    }

    public EventImpl getEventFromId(String id) {
        EventImpl matchingEvent = null;
        for(EventImpl event: this.getEventList().getValue()) {
            if(event.getId().equals(id)) {
                matchingEvent = event;
            }
        }
        return matchingEvent;
    }

    public RecyclerView.Adapter getEventAdapter() {
        return mEventAdapter;
    }

    public MutableLiveData<ArrayList<MovieImpl>> getMovieList() {

        MutableLiveData<ArrayList<MovieImpl>> data = new MutableLiveData<>();
        data.setValue(movieList);

        return data;
    }

    public int getEventListSize() {
        return this.eventList.size();
    }


    public EventImpl getEventInstance(int position) {
        return this.getEventList().getValue().get(position);
    }

    public void addEvent(EventImpl newEvent) {
        this.eventList.add(newEvent);
        mEventAdapter.notifyDataSetChanged();
    }

    public void editEvent(String id, String title, String startDate, String endDate, String venue, String location, String movieTitle) {
        for(EventImpl eventElement: eventList) {
            if(eventElement.getId().equals(id)) {
                eventElement.setTitle(title);
                eventElement.setStartDate(startDate);
                eventElement.setEndDate(endDate);
                eventElement.setVenue(venue);
                eventElement.setLocation(location);
                for(MovieImpl movieElement: movieList) {
                    if(movieElement.getTitle().equals(movieTitle)) {
                        eventElement.setMovie(movieElement);
                    }
                }
            }
        }
        mEventAdapter.notifyDataSetChanged();
    }
}
