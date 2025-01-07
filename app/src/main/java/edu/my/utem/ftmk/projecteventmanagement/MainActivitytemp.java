//package edu.my.utem.ftmk.projecteventmanagement;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.widget.Button;
//import android.widget.LinearLayout;
//
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.drawerlayout.widget.DrawerLayout;
//import com.google.android.material.navigation.NavigationView;
//
//import java.util.List;
//
//public class MainActivitytemp extends AppCompatActivity {
//
//    private LinearLayout buttonContainer;
//    private SqLite dbHelper;
//    private DrawerLayout drawerLayout;
//    private ActionBarDrawerToggle actionBarDrawerToggle;
//    private NavigationView navigationView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maintemp);
//
//        // Set up DrawerLayout and Toolbar
//        drawerLayout = findViewById(R.id.myDrawerLayout);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        // Set up ActionBarDrawerToggle
//        actionBarDrawerToggle = new ActionBarDrawerToggle(
//                this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//
//        // Enable home button (hamburger icon) in action bar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        // Ensure the hamburger icon is visible in the ActionBar
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//
//        // Set up navigation view
////        navigationView = findViewById(R.id.navigation);
////        navigationView.setNavigationItemSelectedListener(item -> {
////            Intent intent;
////            if (item.getItemId() == R.id.nav_home) {
////                intent = new Intent(MainActivitytemp.this, MainActivitytemp.class);
////                startActivity(intent);
////                return true;
////            } else if (item.getItemId() == R.id.nav_event_categories) {
////                Toast.makeText(MainActivitytemp.this, "Event Categories", Toast.LENGTH_SHORT).show();
//////                intent = new Intent(MainActivity.this, EventCategoriesActivity.class);
//////                startActivity(intent);
////                return true;
////            } else if (item.getItemId() == R.id.nav_settings) {
////                Toast.makeText(MainActivitytemp.this, "Settings", Toast.LENGTH_SHORT).show();
//////                intent = new Intent(MainActivity.this, SettingsActivity.class);
//////                startActivity(intent);
////                return true;
////            } else if (item.getItemId() == R.id.nav_logout) {
////                // Handle logout here
////                return true;
////            }
////            return false;
////        });
//
//
//        // Set up button container for event categories
//        buttonContainer = findViewById(R.id.buttonContainer);
//        dbHelper = new SqLite(this);
//
//        List<EventType> categories = dbHelper.getEventCategories();
//        for (EventType event : categories) {
//            Button button = new Button(this);
//            button.setText(event.getName());
//            button.setOnClickListener(v -> openEventCategory(event.getId(), event.getName()));
//            buttonContainer.addView(button);
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void openEventCategory(int id, String name) {
//        Class<?> activityClass;
//
//        switch (name) {
//            case "Concerts":
//                activityClass = UserEvent.class;
//                break;
//            case "Sports":
//                activityClass = ActivitySporttemp.class;
//                break;
//            case "Theater":
//                activityClass = ActivityTheatertemp.class;
//                break;
//            default:
//                activityClass = OtherActivitytemp.class;
//                break;
//        }
//
//        Intent intent = new Intent(MainActivitytemp.this, activityClass);
//        intent.putExtra("CATEGORY_ID", id); // Pass ID
//        intent.putExtra("CATEGORY_NAME", name); // Pass Name
//        startActivity(intent);
//    }
//}
