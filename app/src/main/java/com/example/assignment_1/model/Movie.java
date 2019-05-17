package com.example.assignment_1.model;

public interface Movie {

    //returns ID of movie
    public String getID();

    //returns Title of movie
    public String getTitle();

    public void setTitle(String t);

    //returns Year movie was released
    public String getYear();

    public void setYear(String y);

    //returns poster image source
    public String getPoster();

    public void setPoster(String p);
}
