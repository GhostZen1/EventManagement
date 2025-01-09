package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class AdminHomepage extends AppCompatActivity {

    private RecyclerView recyclerUser;
    private UserAdminAdapter userAdminAdapter;
    private SqLite dbHelper;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private List<UserAdmin> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_homepage);

        // Initialize SharedPreferences (optional)
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1); // Default is -1 if no user ID is found
        Log.d("AdminHomepage", "User ID from SharedPreferences: " + userId);

        // Initialize DrawerLayout and Toolbar
        drawerLayout = findViewById(R.id.myDrawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup drawer toggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // Setup NavigationView
        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent;
            if (item.getItemId() == R.id.nav_user) {
                intent = new Intent(AdminHomepage.this, AdminHomepage.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_eventType) {
                intent = new Intent(AdminHomepage.this, AdminEventType.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_event) {
                Toast.makeText(AdminHomepage.this, "Event", Toast.LENGTH_SHORT).show();
                intent = new Intent(AdminHomepage.this, AdminManageEvent.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_booking) {
                Toast.makeText(AdminHomepage.this, "Booking", Toast.LENGTH_SHORT).show();
                intent = new Intent(AdminHomepage.this, AdminManageBooking.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_logout) {
                intent = new Intent(AdminHomepage.this, LoginActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });

        // Initialize RecyclerView
        recyclerUser = findViewById(R.id.recyclerUser);
        recyclerUser.setLayoutManager(new LinearLayoutManager(this));

        // Initialize database helper
        dbHelper = new SqLite(this);

        // Fetch all users
        userList = dbHelper.getAllUsers();

        // Set up RecyclerView
        recyclerUser = findViewById(R.id.recyclerUser);
        recyclerUser.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter with delete click listener
        userAdminAdapter = new UserAdminAdapter(this, userList, position -> {
            // Handle delete user click
            deleteUser(userList.get(position).getUserId(), position);
        });

        recyclerUser.setAdapter(userAdminAdapter);
    }

    private void deleteUser(int userId, int position) {
        // Delete user from database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("user", "UserId = ?", new String[]{String.valueOf(userId)});
        db.close();

        // Remove the user from the list and notify the adapter
        userList.remove(position);
        userAdminAdapter.notifyItemRemoved(position);

        Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();
    }
}
