package me.daviddoan.planner.model.interfaces;

public interface Event {
    String getId();
    void setId(String id);

    String getTitle();
    void setTitle(String title);

    String getStartDate();
    void setStartDate(String startDate);

    String getEndDate();
    void setEndDate(String endDate);

    String getVenue();
    void setVenue(String venue);

    String getLocation();
    void setLocation(String location);

    Movie getMovie();
    void setMovie(Movie movie);
}
