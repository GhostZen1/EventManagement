package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class AdminHomepage extends AppCompatActivity {

    private LinearLayout buttonContainer;
    private SqLite dbHelper;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_homepage);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1); // Default is -1 if no user ID is found
        Log.d("AdminHomepage", "User ID from SharedPreferences: " + userId);

        // Set up DrawerLayout and Toolbar
        drawerLayout = findViewById(R.id.myDrawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up ActionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // Enable home button (hamburger icon) in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Ensure the hamburger icon is visible in the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Set up navigation view
        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent;
            if (item.getItemId() == R.id.nav_user) {
                intent = new Intent(AdminHomepage.this, AdminHomepage.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_eventType) {
                Toast.makeText(AdminHomepage.this, "Event Type", Toast.LENGTH_SHORT).show();
//                intent = new Intent(MainActivity.this, EventCategoriesActivity.class);
//                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_event) {
                Toast.makeText(AdminHomepage.this, "Event", Toast.LENGTH_SHORT).show();
//                intent = new Intent(AdminHomepage.this, SettingsActivity.class);
//                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_booking) {
                Toast.makeText(AdminHomepage.this, "Booking", Toast.LENGTH_SHORT).show();
//                intent = new Intent(AdminHomepage.this, SettingsActivity.class);
//                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_logout) {
                // Handle logout here
                return true;
            }
            return false;
        });


    }

}
