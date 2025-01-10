package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class AdminManageBooking extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private AdminItemBookingAdapter adapter;
    private SqLite dbHelper;
    private List<Booking2> bookingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_booking);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up DrawerLayout and Toolbar
        drawerLayout = findViewById(R.id.main);
        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);

        dbHelper = new SqLite(this);
        bookingList = dbHelper.viewBooking();  // Fetch all bookings from the database


        // Initialize adapter with the booking data
        adapter = new AdminItemBookingAdapter(bookingList);

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);


        // Set up ActionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // Enable home button (hamburger icon) in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up navigation view
        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent;
            if (item.getItemId() == R.id.nav_user) {
                intent = new Intent(AdminManageBooking.this, AdminHomepage.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_eventType) {
                intent = new Intent(AdminManageBooking.this, AdminManageEventType.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_event) {
                intent = new Intent(AdminManageBooking.this, AdminManageEvent.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_booking) {
                intent = new Intent(AdminManageBooking.this, AdminManageBooking.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_logout) {
                intent = new Intent(AdminManageBooking.this, LoginActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });

    }
}