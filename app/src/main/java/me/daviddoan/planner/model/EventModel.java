package me.daviddoan.planner.model;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import me.daviddoan.planner.adapter.EventRecyclerListAdapter;
import me.daviddoan.planner.view.MainActivity;

public class EventModel {
    private static EventModel firstInstance = null;
    private ArrayList<EventImpl> eventList = new ArrayList<>();
    private ArrayList<MovieImpl> movieList = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private EventRecyclerListAdapter.EventRecyclerListListener listener;

    private EventModel(){
        if(firstInstance == null) {
        }
    }

    public static EventModel getInstance(){
        if(firstInstance == null){
            firstInstance = new EventModel();

        }
        return firstInstance;
    }

    public void setRecyclerViewAdapter(MainActivity activity) {
        mAdapter = new EventRecyclerListAdapter(eventList, activity);
    }

    public MutableLiveData<ArrayList<EventImpl>> getEventList() {
        setEventList();

        MutableLiveData<ArrayList<EventImpl>> data = new MutableLiveData<>();
        data.setValue(eventList);

        return data;
    }

    public RecyclerView.Adapter getEventAdapter() {
        return mAdapter;
    }

    public MutableLiveData<ArrayList<MovieImpl>> getMovieList() {
        setMovieList();

        MutableLiveData<ArrayList<MovieImpl>> data = new MutableLiveData<>();
        data.setValue(movieList);

        return data;
    }

    private void setEventList() {
        if(eventList.size() == 0) {
            eventList.add(new EventImpl
                    ("1", "Freaky Friday", "2/01/2019 1:00:00 AM",
                            "2/01/2019 3:00:00 AM", "RMIT Capitol Theatre",
                            "-37.814795, 144.966119", null));
            eventList.add(new EventImpl("2", "Scary Saturday", "3/01/2019 2:00:00 AM",
                    "3/01/2019 4:00:00 AM", "HOYTS The District Docklands",
                    "-37.811363, 144.936967", null));
        }

    }

    public int getEventListSize() {
        return this.eventList.size();
    }

    private void setMovieList() {
        if(movieList.size() == 0) {
            movieList.add(new MovieImpl("1", "Blade Runner", "1982", "BladeRunner1982.jpg"));
            movieList.add(new MovieImpl("2", "Hackers", "1995", "Hackers.jpg"));
        }

    }

    public EventImpl getEventInstance(int position) {
        return this.getEventList().getValue().get(position);
    }

    public void addEvent(EventImpl newEvent) {
        this.eventList.add(newEvent);
        mAdapter.notifyDataSetChanged();
    }

}
