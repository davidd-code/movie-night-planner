package com.assignment1.model;

public class MovieImpl extends com.assignment1.model.AbstractMovie {

    public MovieImpl(String id, String t, String y, String p){
        this.movieID = id;
        this.movieTitle = t;
        this.year = y;
        this.poster = p;
    }

    @Override
    public String getID() {
        return this.movieID;
    }

    @Override
    public String getTitle() {
        return this.movieTitle;
    }

    @Override
    public void setTitle(String t) {
        this.movieTitle = t;
    }

    @Override
    public String getYear() {
        return this.year;
    }

    @Override
    public void setYear(String y) {
        this.year = y;
    }

    @Override
    public String getPoster(){
        return this.poster;
    }

    @Override
    public void setPoster(String p) {
        this.poster = p;
    }

}
