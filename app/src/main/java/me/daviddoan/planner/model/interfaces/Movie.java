package me.daviddoan.planner.model.interfaces;

public interface Movie {
    String getId();
    void setId(String id);

    String getTitle();
    void setTitle(String title);

    String getYear();
    void setYear(String year);

    String getPoster();
    void setPoster(String poster);
}
