package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UserBooking extends AppCompatActivity {

    private TextView tvEventName, tvCategory, tvPrice, tvTotalPrice;
    private EditText slotInput;
    private double eventPrice, totalPrice;
    private Button btnConfirmBooking;
    private int userId, eventId, slotNumber;
    private ImageView eventImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_booking);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1); // Default is -1 if no user ID is found

        // Initialize the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable back navigation
        toolbar.setNavigationOnClickListener(v -> finish());

        eventImage = findViewById(R.id.eventImage);

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
        eventId = getIntent().getIntExtra("EVENT_ID", -1);
        String eventImage = getIntent().getStringExtra("EVENT_IMAGE");

        // Set the data to the TextViews
        tvPrice.setText("Price: RM " + eventPrice);
        tvEventName.setText("Event: " + eventName);
        tvCategory.setText("Category: " + category);

        loadImageFromName(eventImage);

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

        btnConfirmBooking.setOnClickListener(v -> {
            processPayment();
        });
    }


    // Method to load the event image
    private void loadImageFromName(String imageName) {
        if (imageName != null && !imageName.isEmpty()) {
            // Construct the file path in the 'images' directory
            String filePath = getFilesDir() + "/images/" + imageName;  // 'imageName' is the file name

            Log.d("Image", "File Path: " + filePath);  // Log the file path for debugging

            // Create a File object for the image
            File imageFile = new File(filePath);

            // Use Glide to load the image from internal storage if it exists, or use a placeholder if not found
            if (imageFile.exists()) {
                Glide.with(this)
                        .load(imageFile)  // Load the image from internal storage
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(eventImage);  // Set the image into the ImageView
            } else {
                Glide.with(this)
                        .load(R.drawable.placeholder)  // Fallback to a placeholder image if the file is not found
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(eventImage);  // Set the placeholder image into the ImageView
            }
        } else {
            // Fallback if image name is empty or null
            Glide.with(this)
                    .load(R.drawable.placeholder)  // Use placeholder
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(eventImage);  // Set the placeholder image
        }
    }


    private void updateTotalPrice(String input) {
        if (!input.isEmpty()) {
            try {
                slotNumber = Integer.parseInt(input);
                totalPrice = slotNumber * eventPrice;
                tvTotalPrice.setText(String.format("Total Price: RM %.2f", totalPrice));
            } catch (NumberFormatException e) {
                tvTotalPrice.setText("Invalid input");
            }
        } else {
            tvTotalPrice.setText("Total Price: RM 0.00");
        }
    }

    private void processPayment() {
        String url = getString(R.string.api_toyyibpay);  // Replace with your PHP endpoint

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ToyyibpayResponse", "Response: " + response); // Log the raw response for debugging
                        try {
                            // Try to parse the JSON response
                            JSONObject jsonResponse = new JSONObject(response);
                            Log.d("ToyyibpayResponse", "Parsed JSON: " + jsonResponse.toString()); // Log the parsed JSON for debugging

                            // Check if the response contains paymentURL
                            if (jsonResponse.has("paymentURL")) {
                                String paymentUrl = jsonResponse.getString("paymentURL");


                                Log.d("ToyyibpayResponse", "Payment URL found: " + paymentUrl); // Log the payment URL

                                if (paymentUrl != null) {
                                    // Save userId, eventId, and booking price to SharedPreferences
                                    SharedPreferences sharedPreferences = getSharedPreferences("UserBookingPrefs", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    // Save these values for later use
                                    editor.putInt("userId", userId);
                                    editor.putInt("eventId", eventId);
                                    editor.putInt("slotNumber", slotNumber);
                                    editor.putFloat("bookingPrice", (float) totalPrice); // Save totalPrice as a float
                                    editor.apply();
                                    Log.d("SharedPreferences", "Saved userId: " + userId + ", eventId: " + eventId + ", bookingPrice: " + totalPrice);

                                    // Redirect user to Toyyibpay's payment page
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(paymentUrl));
                                    startActivity(browserIntent);
                                } else {
                                    // Log if paymentURL is null
                                    Log.d("ToyyibpayResponse", "Payment URL is null");
                                    Toast.makeText(UserBooking.this, "Payment failed: " + jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Log if paymentURL is not present in the response
                                Log.d("ToyyibpayResponse", "No paymentURL in response. Message: " + jsonResponse.getString("message"));
                                Toast.makeText(UserBooking.this, "Error: " + jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // Log the error if JSON parsing fails
                            Log.e("ToyyibpayError", "JSON Parsing error", e);
                            Toast.makeText(UserBooking.this, "Error processing the response. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log the error from Volley network request
                        Log.e("ToyyibpayError", "Volley error: " + error.toString());
                        Toast.makeText(UserBooking.this, "Processing Toyyibpay failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "processToyyibpay");
                params.put("totalPrice", String.valueOf(totalPrice));

                return params;
            }
        };

        // Add request to the queue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}
