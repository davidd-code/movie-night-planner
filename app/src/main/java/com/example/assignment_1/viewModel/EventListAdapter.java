package com.example.assignment_1.viewModel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.assignment_1.R;
import com.example.assignment_1.model.EventImpl;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventListViewHolder> {

    private ArrayList<EventImpl> events;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        listener = l;
    }

    public static class EventListViewHolder extends RecyclerView.ViewHolder{
        public TextView eventTitle, eventVenue, eventDate, eventMovie, numAttendees;
        public ImageView deleteImage;

        public EventListViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventVenue = itemView.findViewById(R.id.eventVenue);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventMovie = itemView.findViewById(R.id.newMovie);
            numAttendees = itemView.findViewById(R.id.numAttendees);
            deleteImage = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

        }
    }

    public EventListAdapter(ArrayList<EventImpl> e){
        this.events = e;
    }

    @NonNull
    @Override
    public EventListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_item, viewGroup, false);
        EventListViewHolder evh = new EventListViewHolder(v, listener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull EventListViewHolder eventListViewHolder, int i) {
        EventImpl currentEvent = events.get(i);
        eventListViewHolder.eventTitle.setText(currentEvent.getTitle());
        eventListViewHolder.eventVenue.setText(currentEvent.getVenue());
        eventListViewHolder.numAttendees.setText(Integer.toString(currentEvent.getNumAttendees()));
        if(currentEvent.getChosenMovie() != null){
            eventListViewHolder.eventMovie.setText(currentEvent.getChosenMovie().getTitle());
        }
        if(currentEvent.getStartDate() != null) {
            eventListViewHolder.eventDate.setText(currentEvent.stringFormatLocalDateTime(currentEvent.getStartDate()));
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
