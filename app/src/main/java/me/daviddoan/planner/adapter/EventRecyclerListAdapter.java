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

public class EventRecyclerListAdapter extends RecyclerView.Adapter<EventRecyclerListAdapter.RecyclerViewHolder>{
    public ArrayList<EventImpl> mEventList;

    public EventRecyclerListAdapter(ArrayList<EventImpl> eventList) {
        this.mEventList = eventList;
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.moviePosterImageView);
            mTextView1 = itemView.findViewById(R.id.movieTitleTextView);
            mTextView2 = itemView.findViewById(R.id.textView2);

        }
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_item, viewGroup, false);
        RecyclerViewHolder evh = new RecyclerViewHolder(v);
        return evh;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        EventImpl currentItem = mEventList.get(i);
        recyclerViewHolder.mTextView1.setText(currentItem.getTitle());
        recyclerViewHolder.mTextView2.setText(currentItem.getVenue());
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }


}
