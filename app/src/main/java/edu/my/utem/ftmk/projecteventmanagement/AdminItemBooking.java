package edu.my.utem.ftmk.projecteventmanagement;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminItemBooking extends RecyclerView.Adapter<AdminItemBooking.AdminItemBookingHolder>{

    @NonNull
    @Override
    public AdminItemBookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminItemBookingHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AdminItemBookingHolder extends RecyclerView.ViewHolder {
        public TextView Username,EventName,BookingDate, BookingPrice, slotNum;

        public AdminItemBookingHolder(@NonNull View itemView) {
            super(itemView);
            Username = itemView.findViewById(R.id.txtUserName);
            EventName = itemView.findViewById(R.id.txtEventName);
            BookingDate = itemView.findViewById(R.id.txtBookingDate);
            BookingPrice = itemView.findViewById(R.id.txtBookingPrice);
            slotNum = itemView.findViewById(R.id.txtSlot);
        }
    }
}
