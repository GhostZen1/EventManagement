package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminItemEventAdapter extends RecyclerView.Adapter<AdminItemEventAdapter.AdminItemEventViewHolder> {

    private final List<Event> eventList;

    public AdminItemEventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }
    @NonNull
    @Override
    public AdminItemEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new AdminItemEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminItemEventViewHolder holder, int position) {
        // Assuming you have a list of events to bind
        Event event = eventList.get(position); // Replace with your data source
        holder.txtEventName.setText(event.getName()); // Assuming Event has a getName() method
        // Set up other views in the holder as needed
    }

    @Override
    public int getItemCount() {
        return eventList.size(); // Return the size of your data source
    }

    public static class AdminItemEventViewHolder extends RecyclerView.ViewHolder {
        public TextView txtEventName;
        public ImageButton Imgbtn;
        public AdminItemEventViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEventName = itemView.findViewById(R.id.txtEventname);
            Imgbtn = itemView.findViewById(R.id.imageButton2);

            Imgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                }
            });
        }
    }
}
