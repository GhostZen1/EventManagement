package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdminItemEventAdapter extends RecyclerView.Adapter<AdminItemEventAdapter.AdminItemEventViewHolder> {

    private final List<Event> eventList;

    public AdminItemEventAdapter(List<Event> eventList) {
        this.eventList = eventList != null ? eventList : new ArrayList<>(); // Handle null list
    }

    @NonNull
    @Override
    public AdminItemEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_event, parent, false);
        return new AdminItemEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminItemEventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.txtEventName.setText(event.getName());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class AdminItemEventViewHolder extends RecyclerView.ViewHolder {
        public TextView txtEventName;
        public ImageButton Imgbtn;

        public AdminItemEventViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEventName = itemView.findViewById(R.id.txtEventname);
            Imgbtn = itemView.findViewById(R.id.imageButton2);

            if (txtEventName == null) {
                Log.e("AdminItemEventAdapter", "txtEventName is null");
            }
        }
    }
}
