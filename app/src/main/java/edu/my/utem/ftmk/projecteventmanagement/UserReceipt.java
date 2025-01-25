package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserReceipt extends AppCompatActivity {

    private int userId, eventId, slotNumber;
    private float bookingPrice;
    private double eventPrice;
    private String eventName, eventDate, eventTypeName;
    private TextView txtEventName, txtEventDate, txtBookingSlot, txtEventType, txtEventPrice, txtBookingPrice, txtBookingSlot2, txtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_receipt);

        // Initialize UI components
        txtEventName = findViewById(R.id.txtEventName);
        txtEventDate = findViewById(R.id.txtEventDate);
        txtBookingSlot = findViewById(R.id.txtBookingSlot);
        txtEventType = findViewById(R.id.txtEventType);
        txtEventPrice = findViewById(R.id.txtEventPrice);
        txtBookingPrice = findViewById(R.id.txtBookingPrice);
        txtBookingSlot2 = findViewById(R.id.txtBookingSlot2);
        txtUsername = findViewById(R.id.txtusername);

        // Retrieve the shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserBookingPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        eventId = sharedPreferences.getInt("eventId", -1);
        bookingPrice = sharedPreferences.getFloat("bookingPrice", 0.0f);
        slotNumber = sharedPreferences.getInt("slotNumber", 0);

        Log.d("UserReceipt", "userId: " + userId + ", eventId: " + eventId + ", bookingPrice: " + bookingPrice + ", slotNumber: " + slotNumber);


        // Fetch username and set it
        if (userId != -1) {
            SqLite dbHelper = new SqLite(this);
            String username = dbHelper.getUsernameByUserId(userId);
            txtUsername.setText(username != null ? username : "N/A");
        } else {
            txtUsername.setText("Invalid User");
        }

        if (userId != -1 && eventId != -1) {
            // Perform the booking operation in the background
            new AddBookingTask().execute();
        } else {
            Toast.makeText(this, "Invalid booking data!", Toast.LENGTH_SHORT).show();
        }
    }

    // AsyncTask to handle database operations in the background
    private class AddBookingTask extends AsyncTask<Void, Void, Event> {

        @Override
        protected Event doInBackground(Void... voids) {
            // Initialize the database helper
            SqLite dbHelper = new SqLite(UserReceipt.this);

            // Add the booking in the database and return the booking ID
            long bookingId = dbHelper.addBooking(userId, eventId, bookingPrice, slotNumber);

            if (bookingId != -1) {
                // Fetch the event details using the booking ID
                return dbHelper.getEventDetailsByBookingId((int) bookingId);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Event event) {
            super.onPostExecute(event);

            if (event != null) {
                // Retrieve event details
                eventName = event.getName();
                eventDate = event.getDate();
                eventTypeName = event.getCategory();
                eventPrice = event.getPrice();

                // Update the UI components with event details
                txtEventName.setText(eventName != null ? eventName : "N/A");
                txtEventDate.setText(eventDate != null ? eventDate : "N/A");
                txtBookingSlot.setText(String.valueOf(slotNumber));
                txtEventType.setText(eventTypeName != null ? eventTypeName : "N/A");
                txtEventPrice.setText(String.format("RM %.2f", eventPrice));
                txtBookingPrice.setText(String.format("RM %.2f", bookingPrice));
                txtBookingSlot2.setText(String.valueOf(slotNumber));

                // Display a confirmation Toast
                Toast.makeText(UserReceipt.this, "Event: " + eventName + "\nDate: " + eventDate +
                        "\nType: " + eventTypeName + "\nPrice: RM " + eventPrice, Toast.LENGTH_SHORT).show();
            } else {
                // If booking failed or event details couldn't be fetched
                Toast.makeText(UserReceipt.this, "Failed to retrieve booking details!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
