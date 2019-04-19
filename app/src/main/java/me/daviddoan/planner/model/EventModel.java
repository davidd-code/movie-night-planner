package me.daviddoan.planner.model;

import android.arch.lifecycle.MutableLiveData;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import me.daviddoan.planner.adapter.EventRecyclerListAdapter;
import me.daviddoan.planner.controller.EventsController;
import me.daviddoan.planner.view.MainActivity;

public class EventModel {
    private static EventModel firstInstance = null;
    private ArrayList<EventImpl> eventList;
    private ArrayList<MovieImpl> movieList;
    private RecyclerView.Adapter mEventAdapter;
    private EventsController controller;

    private EventModel(){
        if(firstInstance == null) {
            controller = new EventsController();
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

    public EventsController getController() {
        return this.controller;
    }


    public MutableLiveData<ArrayList<EventImpl>> getEventList() {

        MutableLiveData<ArrayList<EventImpl>> data = new MutableLiveData<>();
        data.setValue(eventList);

        return data;
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



    public void notifyEventAdapter() {
        mEventAdapter.notifyDataSetChanged();
    }
}
