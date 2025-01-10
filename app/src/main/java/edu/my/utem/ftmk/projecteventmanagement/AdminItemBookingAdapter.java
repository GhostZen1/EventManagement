package edu.my.utem.ftmk.projecteventmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminItemBookingAdapter extends RecyclerView.Adapter<AdminItemBookingAdapter.AdminItemBookingHolder> {

    private List<Booking2> bookingList; // List of Booking2 objects

    // Constructor to initialize the adapter with the list of bookings
    public AdminItemBookingAdapter(List<Booking2> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public AdminItemBookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each row
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_booking, parent, false);
        return new AdminItemBookingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminItemBookingHolder holder, int position) {
        // Get the booking at the current position
        Booking2 booking = bookingList.get(position);

        // Bind the data to the TextViews
        holder.Username.setText("User ID: " + booking.getUserId());
        holder.EventName.setText("Event ID: " + booking.getEventId());
        holder.BookingDate.setText("Booking Date: " + booking.getBookingDate());
        holder.BookingPrice.setText("Booking Price: " + booking.getBookingPrice());
        holder.slotNum.setText("Slot Number: " + booking.getSlotNumber());
    }

    @Override
    public int getItemCount() {
        return bookingList.size(); // Return the size of the list
    }

    public class AdminItemBookingHolder extends RecyclerView.ViewHolder {
        public TextView Username, EventName, BookingDate, BookingPrice, slotNum;

        public AdminItemBookingHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the TextViews
            Username = itemView.findViewById(R.id.tvUserId);
            EventName = itemView.findViewById(R.id.tvEventId);
            BookingDate = itemView.findViewById(R.id.tvBookingDate);
            BookingPrice = itemView.findViewById(R.id.tvBookingPrice);
            slotNum = itemView.findViewById(R.id.tvSlotNumber);
        }
    }
}
