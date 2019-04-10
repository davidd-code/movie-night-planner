package me.daviddoan.planner.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import me.daviddoan.planner.model.EventImpl;
import me.daviddoan.planner.model.EventModel;

public class AppViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<EventImpl>> mEventList;
    private EventModel mEventModel;

    public void init() {
        if(mEventList != null) {
            return;
        }
        mEventModel = EventModel.getInstance();
        mEventList = mEventModel.getEventList();
    }

    public AppViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<EventImpl>> getEventList() {
        return mEventList;
    }
}
