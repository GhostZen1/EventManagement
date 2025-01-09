package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdminItemEventAdapter extends RecyclerView.Adapter<AdminItemEventAdapter.AdminItemEventViewHolder> {

    private final List<Event> eventList;
    private final Context context;


    public AdminItemEventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList != null ? eventList : new ArrayList<>();
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

        holder.Imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) { // Check if the position is valid
                    showDeleteConfirmationDialog(currentPosition);
                }
            }
        });
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
        }
    }

    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context); // Use the context passed to the adapter
        builder.setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteItem(int position) {
        // Remove the item from the list
        eventList.remove(position);
        // Notify the adapter that the data has changed
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, eventList.size()); // Optional: update the range
        // TODO: Implement SQL deletion logic
    }
}
