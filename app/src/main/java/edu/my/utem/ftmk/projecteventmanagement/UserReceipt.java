package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserReceipt extends AppCompatActivity {

    public int userId, eventId, slotNumber;
    public float bookingPrice;
    public double eventPrice;
    public String eventName, eventDate, eventTypeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_receipt);

        // Retrieve the shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserBookingPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        eventId = sharedPreferences.getInt("eventId", -1);
        bookingPrice = sharedPreferences.getFloat("bookingPrice", 0.0f);
        slotNumber = sharedPreferences.getInt("slotNumber", 0);

        Log.d("SharedPreferencesInReceipt", "Saved in receipt - userId: " + userId + ", eventId: " + eventId + ", bookingPrice: " + bookingPrice + ", slotNumber: " + slotNumber);
        Toast.makeText(UserReceipt.this, "Saved in receipt - userId: " + userId + ", eventId: " + eventId + ", bookingPrice: " + bookingPrice + ", slotNumber: " + slotNumber, Toast.LENGTH_SHORT).show();

        if (userId != -1 && eventId != -1) {
            // Add the booking to the database
            SqLite dbHelper = new SqLite(this);
            long bookingId = dbHelper.addBooking(userId, eventId, bookingPrice, slotNumber);

            if (bookingId != -1) {
                Toast.makeText(this, "Booking successfully added with ID: " + bookingId, Toast.LENGTH_SHORT).show();
                Event event = dbHelper.getEventDetailsByBookingId((int) bookingId);

                if (event != null) {
                    // Now you can access the event details:
                    eventName = event.getName();
                    eventDate = event.getDate();
                    eventTypeName = event.getCategory();
                    eventPrice = event.getPrice();

                    // Display event details in your layout (use TextViews, etc.)
                    // Example:
                    Toast.makeText(this, "Event: " + eventName + "\nDate: " + eventDate +
                            "\nType: " + eventTypeName + "\nPrice: " + eventPrice, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to add booking!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Invalid booking data!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); // Update intent
        handleIncomingIntent(intent); // Handle new intent

        Log.d("SharedPreferencesInReceipt", "Saved in receipt - userId: " + userId + ", eventId: " + eventId + ", bookingPrice: " + bookingPrice);
    }

    private void handleIncomingIntent(Intent intent) {
        Uri data = intent.getData();
        if (data != null && data.getScheme().equals("yourapp") && data.getHost().equals("payment-complete")) {
            // Process the payment result (e.g., confirm the payment, show receipt)
            String paymentStatus = data.getQueryParameter("status"); // Retrieve the payment status
            Log.d("FPX PAYMENT STATUS", "FPX PAYMENT STATUS: " + paymentStatus);
            // Retrieve the saved data from SharedPreferences


        }
    }
}
