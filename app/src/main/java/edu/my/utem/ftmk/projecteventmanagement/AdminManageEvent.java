package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

    public class AdminManageEvent extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private AdminItemEventAdapter adapter;
    private SqLite dbHelper;
    private List<Event> eventList;
    private Button btnAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_event);

        btnAddEvent = findViewById(R.id.btnAddEvent);
        btnAddEvent.setOnClickListener(v -> {
            Intent intent = new Intent(AdminManageEvent.this, AdminAddEvent.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new SqLite(this);
        eventList = dbHelper.fetchAllEvents(); // Pass the event type ID as needed

        adapter = new AdminItemEventAdapter(this, eventList);

        // Initialize adapter with delete click listener
        adapter.setOnEventDeleteClickListener(position -> {
            // Handle delete event click
            deleteEvent(eventList.get(position).getId(), position);
        });

        recyclerView.setAdapter(adapter);

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

        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent;
            if (item.getItemId() == R.id.nav_user) {
                intent = new Intent(AdminManageEvent.this, AdminHomepage.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_eventType) {
                intent = new Intent(AdminManageEvent.this, AdminManageEventType.class);
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

    private void deleteEvent(int eventId, int position) {
        // Show a confirmation dialog before deleting
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete this event?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    // Delete event from database
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    int rowsDeleted = db.delete("event", "eventId = ?", new String[]{String.valueOf(eventId)});
                    db.close();

                    if (rowsDeleted > 0) {
                        // Remove the event from the list and notify the adapter
                        eventList.remove(position);
                        adapter.notifyItemRemoved(position);
                        Toast.makeText(AdminManageEvent.this, "Event deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminManageEvent.this, "Failed to delete event", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", (dialog, id) -> {
                    // Dismiss the dialog if "No" is clicked
                    dialog.cancel();
                })
                .create()
                .show();
    }
}
