package me.daviddoan.planner.model;

import me.daviddoan.planner.model.interfaces.Movie;

public class EventImpl extends AbstractEvent{

    public EventImpl(String id, String title, String startDate, String endDate, String venue, String location, Movie movie) {
        super(id, title, startDate, endDate, venue, location, movie);
    }

//    public EventImpl(int imageResource, String text1, String text2) {
//        this.mImageResource = imageResource;
//        this.mText1 = text1;
//        this.mText2 = text2;
//    }

}
