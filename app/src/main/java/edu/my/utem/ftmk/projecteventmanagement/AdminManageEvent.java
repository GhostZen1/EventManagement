package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class AdminManageEvent extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private List<Event> list; // Declare the list here
    private AdminItemEventAdapter adapter; // Declare the adapter here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_event);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load the list of events
        loadEventList();

        // Set up the adapter with the loaded list
        adapter = new AdminItemEventAdapter(list);
        recyclerView.setAdapter(adapter);

        // Set up DrawerLayout and Toolbar
        drawerLayout = findViewById(R.id.DrawerLayoutAdminManageEvent);
        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);

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
                intent = new Intent(AdminManageEvent.this, AdminHomepage.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_eventType) {
                intent = new Intent(AdminManageEvent.this, AdminEventType.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_event) {
                intent = new Intent(AdminManageEvent.this, AdminManageEvent.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_booking) {
                intent = new Intent(AdminManageEvent.this, AdminManageBooking.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_logout) {
                intent = new Intent(AdminManageEvent.this, LoginActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    private void loadEventList() {
        SqLite dbHelper = new SqLite(this);
        list = dbHelper.getListEvent();
    }
}