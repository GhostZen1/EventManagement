package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.List;

public class UserHomepage extends AppCompatActivity{

    private LinearLayout buttonContainer;
    private SqLite dbHelper;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_homepage);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        Log.d("UserHomepage", "User ID from SharedPreferences: " + userId);

        // Set up DrawerLayout and Toolbar
        drawerLayout = findViewById(R.id.myDrawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigation);
            navigationView.setNavigationItemSelectedListener(item -> {
                Intent intent;
                if (item.getItemId() == R.id.nav_homepage) {
                    intent = new Intent(UserHomepage.this, UserHomepage.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.nav_profile) {
                    intent = new Intent(UserHomepage.this, UserProfile.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.nav_booking_history) {
                    intent = new Intent(UserHomepage.this, UserBookingHistory.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.nav_logout) {
                    // Handle logout here
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();  // Clear all preferences
                    editor.apply();  // Apply changes asynchronously

                    // Navigate to LoginActivity
                    intent = new Intent(UserHomepage.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the activity stack
                    startActivity(intent);
                    finish(); // Finish current activity so user cannot return to it with the back button
                    Toast.makeText(UserHomepage.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });

        // Set up button container for event categories
        buttonContainer = findViewById(R.id.buttonContainer);
        dbHelper = new SqLite(this);

// Fetch event categories from the database
        List<EventType> categories = dbHelper.getEventCategories();
        for (EventType event : categories) {
            // Add ImageView for the event type image
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    600)); // Set height for the image
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Construct the file path in the 'images' directory
            String filePath = getFilesDir() + "/images/" + event.getImageName();  // event.getImageName() is the file name

            Log.d("Image", "File Path: " + filePath);  // Log the file path for debugging

            // Create a File object for the image
            File imageFile = new File(filePath);

            // Use Glide to load the image from internal storage if it exists, or use a placeholder if not found
            if (imageFile.exists()) {
                Glide.with(this)
                        .load(imageFile)  // Load the image from internal storage
                        .into(imageView);  // Set the image into the ImageView
            } else {
                Glide.with(this)
                        .load(R.drawable.placeholder)  // Fallback to a placeholder image if the file is not found
                        .into(imageView);  // Set the placeholder image into the ImageView
            }

            buttonContainer.addView(imageView); // Add ImageView to the container

            // Add Button for the event type
            Button button = new Button(this);
            button.setText(event.getName());
            button.setOnClickListener(v -> {
                // Open UserEvent activity with the event type ID
                Intent intent = new Intent(UserHomepage.this, UserEvent.class);
                intent.putExtra("eventTypeId", event.getId());
                startActivity(intent);
            });

            buttonContainer.addView(button); // Add Button to the container
        }

    }



}
