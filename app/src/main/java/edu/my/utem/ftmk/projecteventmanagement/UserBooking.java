package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class UserBooking extends AppCompatActivity {

    private TextView tvEventName, tvCategory, tvPrice, tvTotalPrice;
    private EditText slotInput;
    private double eventPrice, totalPrice;
    private Button btnConfirmBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1); // Default is -1 if no user ID is found

        // Initialize the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable back navigation
        toolbar.setNavigationOnClickListener(v -> finish());

        // Initialize the TextViews
        tvEventName = findViewById(R.id.tvEventName);
        tvCategory = findViewById(R.id.tvCategory);
        tvPrice = findViewById(R.id.tvPrice);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);

        // Initialize the EditText
        slotInput = findViewById(R.id.etSlot);

        // Get the Intent that started this activity
        eventPrice = getIntent().getDoubleExtra("EVENT_PRICE", 0.0);
        String eventName = getIntent().getStringExtra("EVENT_NAME");
        String category = getIntent().getStringExtra("CATEGORY");
        int eventId = getIntent().getIntExtra("EVENT_ID", -1);

        // Set the data to the TextViews
        tvPrice.setText("Price: RM " + eventPrice);
        tvEventName.setText("Event: " + eventName);
        tvCategory.setText("Category: " + category);

        // Add a TextWatcher to calculate the total price dynamically
        slotInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed before text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Calculate the total price as the user types
                updateTotalPrice(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed after text is changed
            }
        });
    }

    private void updateTotalPrice(String input) {
        if (!input.isEmpty()) {
            try {
                int slotNumber = Integer.parseInt(input);
                totalPrice = slotNumber * eventPrice;
                tvTotalPrice.setText(String.format("Total Price: RM %.2f", totalPrice));
            } catch (NumberFormatException e) {
                tvTotalPrice.setText("Invalid input");
            }
        } else {
            tvTotalPrice.setText("Total Price: RM 0.00");
        }
    }
}
