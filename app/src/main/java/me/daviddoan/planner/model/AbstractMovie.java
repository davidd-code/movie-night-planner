package me.daviddoan.planner.model;

import me.daviddoan.planner.model.interfaces.Movie;

public abstract class AbstractMovie implements Movie {

    private String id, title, year, poster;

    public AbstractMovie(String id, String title, String year, String poster) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.poster = poster;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPoster() {
        return this.poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
