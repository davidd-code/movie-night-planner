package me.daviddoan.planner.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.daviddoan.planner.R;
import me.daviddoan.planner.model.EventImpl;

/**
 * This class is the adapter for the event recycler list. It will also set the onclicklisteners
 * for each event item so that an edit dialog will open when an event is clicked.
 */
public class EventRecyclerListAdapter extends RecyclerView.Adapter<EventRecyclerListAdapter.RecyclerViewHolder>{
    private ArrayList<EventImpl> mEventList;
    private EventRecyclerListListener mEventRecyclerListListener;

    public EventRecyclerListAdapter(ArrayList<EventImpl> eventList, EventRecyclerListListener listener) {
        this.mEventList = eventList;
        this.mEventRecyclerListListener = listener;
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mPosterImageView;
        private TextView mEventTitleTextView;
        private TextView mEventVenueTextView;
        private TextView mEventStartDateTextView;
        private TextView mEventStartTimeTextView;
        private TextView mNumAttendeesTextView;

        EventRecyclerListListener eventRecyclerListListener;

        public RecyclerViewHolder(@NonNull View itemView, EventRecyclerListListener listener) {
            super(itemView);
            mPosterImageView = itemView.findViewById(R.id.moviePosterImageView);
            mEventTitleTextView = itemView.findViewById(R.id.eventTitleTextView);
            mEventVenueTextView = itemView.findViewById(R.id.eventVenueTextView);
            mEventStartDateTextView = itemView.findViewById(R.id.startDateDisplay);
            mEventStartTimeTextView = itemView.findViewById(R.id.startTimeTextView);
            mNumAttendeesTextView = itemView.findViewById(R.id.numAttendeesTextView);
            this.eventRecyclerListListener = listener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            eventRecyclerListListener.onEventClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_item, viewGroup, false);
        RecyclerViewHolder evh = new RecyclerViewHolder(v, mEventRecyclerListListener);
        return evh;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        EventImpl currentItem = mEventList.get(i);
        recyclerViewHolder.mEventTitleTextView.setText(currentItem.getTitle());
        recyclerViewHolder.mEventVenueTextView.setText(currentItem.getVenue());
        recyclerViewHolder.mEventStartDateTextView.setText(currentItem.getStartDateOnly());
        recyclerViewHolder.mEventStartTimeTextView.setText(currentItem.getStartTimeOnly());
        String numAttendees = Integer.toString(currentItem.getAttendees().size());
        recyclerViewHolder.mNumAttendeesTextView.setText(numAttendees + " attendees");
        if(currentItem.getMovie() != null) {
            switch(currentItem.getMovie().getTitle()) {
                case "Blade Runner":
                    recyclerViewHolder.mPosterImageView.setImageResource(R.drawable.blade_runner1982);
                    break;
                case "Hackers":
                    recyclerViewHolder.mPosterImageView.setImageResource(R.drawable.hackers);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }
    // interface used to set up onclicklistener
    public interface EventRecyclerListListener {
        void onEventClick(int position);
    }

}
