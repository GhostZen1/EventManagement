package edu.my.utem.ftmk.projecteventmanagement;

import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminItemEventTypeAdapter extends RecyclerView.Adapter<AdminItemEventTypeAdapter.AdminItemEventTypeHolder> {
    @NonNull
    @Override
    public AdminItemEventTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminItemEventTypeHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AdminItemEventTypeHolder extends RecyclerView.ViewHolder{
        Image img;
        TextView EventTypeName;
        public AdminItemEventTypeHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
