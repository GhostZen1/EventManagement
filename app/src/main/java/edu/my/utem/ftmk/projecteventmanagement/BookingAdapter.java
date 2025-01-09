package edu.my.utem.ftmk.projecteventmanagement;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.UserBookingViewHolder> {

    private List<Booking> bookingList;
    private OnItemClickListener listener;

    // Constructor to initialize the booking list
    public BookingAdapter(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    // Interface for item click handling
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // Set the click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_booking, parent, false); // Replace with your actual item layout resource
        return new UserBookingViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserBookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        // Log the values to check if they are correct
        Log.d("BookingAdapter", "Event Name: " + booking.getEventName());
        Log.d("BookingAdapter", "Event Category: " + booking.getEventCategory());
        Log.d("BookingAdapter", "Event Price: " + booking.getEventPrice());
        Log.d("BookingAdapter", "Booking Date: " + booking.getBookingDate());
        Log.d("BookingAdapter", "Booking Price: " + booking.getBookingPrice());
        Log.d("BookingAdapter", "Booking Slot: " + booking.getBookingSlot());
        Log.d("BookingAdapter", "Event Image: " + booking.getEventImage());

        // Bind booking details to the ViewHolder
        holder.txtEventName.setText(booking.getEventName());
        holder.txtEventCategory.setText(booking.getEventCategory());
        holder.txtEventPrice.setText(String.format("RM %.2f", booking.getEventPrice()));
        holder.txtBookingDate.setText(booking.getBookingDate());
        holder.txtBookingPrice.setText(String.format("RM %.2f", booking.getBookingPrice()));
        holder.txtBookingSlot.setText(String.format("Slot: %d", booking.getBookingSlot()));

        // Extract the base name by removing the file extension
        String baseName = booking.getEventImage().replaceFirst("\\.[^.]+$", ""); // Remove file extension

        // Get the resource ID for the drawable
        int resId = holder.itemView.getContext().getResources().getIdentifier(baseName, "drawable", holder.itemView.getContext().getPackageName());

        // Use Glide to load the image from drawable if exists, or use a placeholder if not found
        if (resId != 0) {
            Glide.with(holder.itemView.getContext())
                    .load(resId)  // Load the image from the drawable resource
                    .into(holder.eventImage);  // Assuming you have an ImageView with the ID 'eventImage'
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.placeholder)  // Fallback to placeholder image if not found
                    .into(holder.eventImage);  // Assuming you have an ImageView with the ID 'eventImage'
        }
    }


    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    // ViewHolder class
    public static class UserBookingViewHolder extends RecyclerView.ViewHolder {

        public TextView txtEventName, txtEventCategory, txtEventPrice, txtBookingDate, txtBookingPrice, txtBookingSlot;
        public ImageView eventImage;

        public UserBookingViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.eventImage);
            txtEventName = itemView.findViewById(R.id.txtEventName);
            txtEventCategory = itemView.findViewById(R.id.txtEventCategory);
            txtEventPrice = itemView.findViewById(R.id.txtEventPrice);
            txtBookingDate = itemView.findViewById(R.id.txtBookingDate);
            txtBookingPrice = itemView.findViewById(R.id.txtBookingPrice);
            txtBookingSlot = itemView.findViewById(R.id.txtBookingSlot);

            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(view, position);
                    }
                }
            });
        }
    }
}
