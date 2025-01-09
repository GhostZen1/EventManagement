package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class AdminItemEventAdapter extends RecyclerView.Adapter<AdminItemEventAdapter.EventViewHolder> {

    private List<Event> eventList;
    private Context context;
    private OnEventDeleteClickListener onEventDeleteClickListener;

    public AdminItemEventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.tvEventId.setText("Event ID : " + String.valueOf(event.getId()));
        holder.tvEventName.setText(event.getName());
        holder.tvEventDate.setText(event.getDate());
        holder.tvEventCategory.setText(event.getCategory());
        holder.tvEventPrice.setText("RM " + String.format("%.2f", event.getPrice()));

        // Construct the file path in the 'images' directory
        String filePath = holder.itemView.getContext().getFilesDir() + "/images/" + event.getEventImage();  // event.getEventImage() is the file name

        Log.d("Image", "File Path: " + filePath);  // Log the file path for debugging")

//        File imageFile = new File("/data/data/edu.my.utem.ftmk.projecteventmanagement/files/images/IMG-20250110-WA0002.jpg");
//        if (imageFile.exists()) {
//            Glide.with(context)
//                    .load(imageFile)
//                    .into(holder.eventImage);
//        }


        // Create a File object for the image
        File imageFile = new File(filePath);

        // Use Glide to load the image from internal storage if it exists, or use a placeholder if not found
        if (imageFile.exists()) {
            Glide.with(holder.itemView.getContext())
                    .load(imageFile)  // Load the image from internal storage
                    .into(holder.eventImage);  // Assuming you have an ImageView with the ID 'eventImage'
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.placeholder)  // Fallback to a placeholder image if the file is not found
                    .into(holder.eventImage);  // Assuming you have an ImageView with the ID 'eventImage'
        }


        // Handle delete icon click
        holder.ivDeleteEvent.setOnClickListener(v -> {
            if (onEventDeleteClickListener != null) {
                onEventDeleteClickListener.onDeleteClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView tvEventId, tvEventName, tvEventDate, tvEventCategory, tvEventPrice;
        ImageView eventImage, ivDeleteEvent;

        public EventViewHolder(View itemView) {
            super(itemView);
            tvEventId = itemView.findViewById(R.id.tvEventId);
            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvEventDate = itemView.findViewById(R.id.tvEventDate);
            tvEventCategory = itemView.findViewById(R.id.tvEventCategory);
            tvEventPrice = itemView.findViewById(R.id.tvEventPrice);
            eventImage = itemView.findViewById(R.id.eventImage);
            ivDeleteEvent = itemView.findViewById(R.id.ivDeleteEvent);
        }
    }

    // Setter for the delete click listener
    public void setOnEventDeleteClickListener(OnEventDeleteClickListener listener) {
        this.onEventDeleteClickListener = listener;
    }

    public interface OnEventDeleteClickListener {
        void onDeleteClick(int position);
    }
}
