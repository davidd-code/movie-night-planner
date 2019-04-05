package me.daviddoan.planner.model;

public class EventModel {
    private static EventModel firstInstance = null;

    private EventModel(){}

    public static EventModel getInstance(){
        if(firstInstance == null){

            firstInstance = new EventModel();

        }
        return firstInstance;
    }
}
