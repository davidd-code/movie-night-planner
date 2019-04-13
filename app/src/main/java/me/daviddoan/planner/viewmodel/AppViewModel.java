package me.daviddoan.planner.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import me.daviddoan.planner.model.EventImpl;
import me.daviddoan.planner.model.EventModel;
import me.daviddoan.planner.model.MovieImpl;

public class AppViewModel extends AndroidViewModel {

    private EventModel mEventModel;
    private MutableLiveData<ArrayList<EventImpl>> mEventList;
    private MutableLiveData<ArrayList<MovieImpl>> mMovieList;

    public void init() {
        if(mEventList != null && mMovieList != null) {
            return;
        }
        mEventModel = EventModel.getInstance();
        mMovieList = mEventModel.getMovieList();
        mEventList = mEventModel.getEventList();
    }

    public AppViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<EventImpl>> getEventList() {
        return mEventList;
    }

    public LiveData<ArrayList<MovieImpl>> getMovieList() {
        return mMovieList;
    }

    public ArrayList<MovieImpl> getMovieArrayList() {
        return mMovieList.getValue();
    }

    public MovieImpl getMovie(ArrayList<MovieImpl> movieList, int position) {
        return movieList.get(position);
    }
}
