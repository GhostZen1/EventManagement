package edu.my.utem.ftmk.projecteventmanagement;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserBookingAdapter extends RecyclerView.Adapter<UserBookingAdapter.UserBookingViewHolder> {

    private EventAdapter.OnItemClickListener Listener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(EventAdapter.OnItemClickListener listener) {
        Listener = listener;
    }

    @NonNull
    @Override
    public UserBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserBookingViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class UserBookingViewHolder extends RecyclerView.ViewHolder {

        public TextView txtEventName, txtEventCategory, txtEventPrice, txtBookingDate, txtBookingPrice;
        public UserBookingViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            txtEventName = itemView.findViewById(R.id.txtEventName);
            txtEventCategory = itemView.findViewById(R.id.txtEventCategory);
            txtEventPrice = itemView.findViewById(R.id.txtEventPrice);
            txtBookingDate = itemView.findViewById(R.id.txtBookingDate);
            txtBookingPrice = itemView.findViewById(R.id.txtBookingPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(view, position); // Pass view and position to listener
                        }
                    }
                }
            });
        }
    }
}
