package edu.my.utem.ftmk.projecteventmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BookingActivity extends AppCompatActivity {

    private TextView tvEventName, tvCategory;
    private Spinner spinnerSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Initialize the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable back navigation
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                finish(); // Closes the current activity and goes back to the previous one
            }
        });

        // Initialize the TextViews
        tvEventName = findViewById(R.id.tvEventName);
        tvCategory = findViewById(R.id.tvCategory);

        // Initialize the Spinner
        spinnerSeats = findViewById(R.id.spinnerSeats);

        // Get the Intent that started this activity
        String eventName = getIntent().getStringExtra("EVENT_NAME");
        String category = getIntent().getStringExtra("CATEGORY");

        // Set the data to the TextViews
        tvEventName.setText("Event: " + eventName);
        tvCategory.setText("Category: " + category);

        // Populate the Spinner with data
        String[] seatOptions = {"Seat 1", "Seat 2", "Seat 3", "Seat 4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, seatOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSeats.setAdapter(adapter);
    }
}
