package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserBookingHistory extends AppCompatActivity {

    public int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_booking_history);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        Log.d("UserHomepage", "User ID from SharedPreferences: " + userId);

        SqLite sqLite = new SqLite(this);
        List<Booking> bookingHistory = sqLite.getBookingHistory(userId); // Pass the current user ID

        BookingAdapter adapter = new BookingAdapter(bookingHistory);
        RecyclerView recyclerView = findViewById(R.id.RecyclerBookingHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }
}