package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
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

public class AdminManageEventType extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private AdminItemEventTypeAdapter adapter;
    private SqLite dbHelper;
    private List<EventType> eventTypeList;
    private Button btnAddEventType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_event_type);

        btnAddEventType = findViewById(R.id.btnAddEventType);
        btnAddEventType.setOnClickListener(v -> {
            Intent intent = new Intent(AdminManageEventType.this, AdminAddEventType.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new SqLite(this);
        eventTypeList = dbHelper.getEventCategories();

        adapter = new AdminItemEventTypeAdapter(this, eventTypeList);

        // Initialize adapter with delete click listener
        adapter.setOnEventTypeDeleteClickListener(position -> {
            // Handle delete event type click
            deleteEventType(eventTypeList.get(position).getId(), position);
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
                intent = new Intent(AdminManageEventType.this, AdminHomepage.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_eventType) {
                intent = new Intent(AdminManageEventType.this, AdminManageEventType.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_event) {
                intent = new Intent(AdminManageEventType.this, AdminManageEvent.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_booking) {
                intent = new Intent(AdminManageEventType.this, AdminManageBooking.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_logout) {
                intent = new Intent(AdminManageEventType.this, LoginActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    private void deleteEventType(int eventTypeId, int position) {
        // Show a confirmation dialog before deleting
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete this event type?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    // Delete event type from database
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    int rowsDeleted = db.delete("eventType", "id = ?", new String[]{String.valueOf(eventTypeId)});
                    db.close();

                    if (rowsDeleted > 0) {
                        // Remove the event type from the list and notify the adapter
                        eventTypeList.remove(position);
                        adapter.notifyItemRemoved(position);
                        Toast.makeText(AdminManageEventType.this, "Event type deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminManageEventType.this, "Failed to delete event type", Toast.LENGTH_SHORT).show();
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
