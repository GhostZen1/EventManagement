package edu.my.utem.ftmk.projecteventmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.bumptech.glide.Glide;

// EventAdapter to bind Event data to RecyclerView
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList;
    private OnItemClickListener mListener;

    // Constructor
    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    // ViewHolder to hold each item view
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView eventName, eventDate, eventCategory, eventPrice;
        public ImageView eventImage;

        public EventViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            eventName = itemView.findViewById(R.id.tvEventName); // Assuming you have these IDs in the layout
            eventDate = itemView.findViewById(R.id.tvEventDate);
            eventCategory = itemView.findViewById(R.id.tvEventCategory);
            eventPrice = itemView.findViewById(R.id.tvEventPrice);
            eventImage = itemView.findViewById(R.id.eventImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v, position); // Pass view and position to listener
                        }
                    }
                }
            });
        }
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the event item layout (replace with your layout file)
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.eventName.setText(event.getName());
        holder.eventDate.setText(event.getDate());
        holder.eventCategory.setText(event.getCategory());
        holder.eventPrice.setText("RM " + String.valueOf(event.getPrice()));

        // Extract the base name by removing the file extension
        String baseName = event.getEventImage().replaceFirst("\\.[^.]+$", ""); // Remove file extension

        // Get the resource ID for the drawable
        int resId = holder.itemView.getContext().getResources().getIdentifier(baseName, "drawable", holder.itemView.getContext().getPackageName());

        // Use Glide to load the image from drawable if exists, or use a placeholder if not found
        if (resId != 0) {
            Glide.with(holder.itemView.getContext())
                    .load(resId)  // Load the image from the drawable resource
                    .into(holder.eventImage);  // Assuming you have an ImageView with the ID 'eventImage' in your layout
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.placeholder)  // Fallback to placeholder image if not found
                    .into(holder.eventImage);  // Assuming you have an ImageView with the ID 'eventImage'
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
