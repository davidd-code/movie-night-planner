package me.daviddoan.planner.model;

import me.daviddoan.planner.model.interfaces.Movie;

public class EventImpl extends AbstractEvent{
    private int mImageResource;
    private String mText1;
    private String mText2;

    public EventImpl(String id, String title, String startDate, String endDate, String venue, String location, Movie movie) {
        super(id, title, startDate, endDate, venue, location, movie);
    }

//    public EventImpl(int imageResource, String text1, String text2) {
//        this.mImageResource = imageResource;
//        this.mText1 = text1;
//        this.mText2 = text2;
//    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }
}
