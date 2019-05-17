package com.example.assignment_1.model;

public abstract class AbstractMovie implements Movie {

    protected String movieID;
    protected String movieTitle;
    protected String year;
    protected String poster;

    public AbstractMovie(){}
    public AbstractMovie(String id, String t, String y, String p){}

    //returns ID of movie
    public String getID(){
        return movieID;
    }

    //returns Title of movie
    public String getTitle(){
        return movieTitle;
    }

    public void setTitle(String t){
        this.movieTitle = t;
    }

    //returns Year movie was released
    public String getYear(){
        return year;
    }

    public void setYear(String y){
        this.year = y;
    }

    public String getPoster(){
        return poster;
    }

    public void setPoster(String p){
        this.poster = p;
    }
}
