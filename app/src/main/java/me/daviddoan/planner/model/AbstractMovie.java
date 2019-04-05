package me.daviddoan.planner.model;

import me.daviddoan.planner.model.interfaces.Movie;

public class AbstractMovie implements Movie {

    private String id, title, year, poster;

    public AbstractMovie(String id, String title, String year, String poster) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.poster = poster;
    }
}
