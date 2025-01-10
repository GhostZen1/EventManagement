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

public class AdminItemEventTypeAdapter extends RecyclerView.Adapter<AdminItemEventTypeAdapter.EventTypeViewHolder> {

    private List<EventType> eventTypeList;
    private Context context;
    private OnEventTypeDeleteClickListener onEventTypeDeleteClickListener;

    public AdminItemEventTypeAdapter(Context context, List<EventType> eventTypeList) {
        this.context = context;
        this.eventTypeList = eventTypeList;
    }

    @Override
    public EventTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_event_type_item, parent, false);
        return new EventTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventTypeViewHolder holder, int position) {
        EventType eventType = eventTypeList.get(position);
        holder.tvEventTypeId.setText("ID: " + eventType.getId());
        holder.tvEventTypeName.setText(eventType.getName());

        // Construct file path for the image
        String filePath = context.getFilesDir() + "/images/" + eventType.getImageName();

        Log.d("Image", "File Path: " + filePath); // Log file path for debugging

        // Create a File object and use Glide to load the image
        File imageFile = new File(filePath);
        if (imageFile.exists()) {
            Glide.with(context)
                    .load(imageFile)
                    .into(holder.eventTypeImage);
        } else {
            Glide.with(context)
                    .load(R.drawable.placeholder) // Fallback placeholder
                    .into(holder.eventTypeImage);
        }

        // Handle delete icon click
        holder.ivDeleteEventType.setOnClickListener(v -> {
            if (onEventTypeDeleteClickListener != null) {
                onEventTypeDeleteClickListener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventTypeList.size();
    }

    public static class EventTypeViewHolder extends RecyclerView.ViewHolder {
        TextView tvEventTypeId, tvEventTypeName;
        ImageView eventTypeImage, ivDeleteEventType;

        public EventTypeViewHolder(View itemView) {
            super(itemView);
            tvEventTypeId = itemView.findViewById(R.id.tvEventTypeId);
            tvEventTypeName = itemView.findViewById(R.id.tvEventTypeName);
            eventTypeImage = itemView.findViewById(R.id.eventTypeImage);
            ivDeleteEventType = itemView.findViewById(R.id.ivDeleteEventType);
        }
    }

    // Setter for the delete click listener
    public void setOnEventTypeDeleteClickListener(OnEventTypeDeleteClickListener listener) {
        this.onEventTypeDeleteClickListener = listener;
    }

    public interface OnEventTypeDeleteClickListener {
        void onDeleteClick(int position);
    }
}
