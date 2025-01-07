//package edu.my.utem.ftmk.projecteventmanagement;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.View;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import java.util.ArrayList;
//import java.util.List;
//
//public class OtherActivitytemp extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private EventAdapter eventAdapter;
//    private List<Event> eventList; // Use List<Event> instead of List<String>
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_event_listtemp);
//
//        // Set up the Toolbar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        // Enable the Up (Back) button in the toolbar
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show the back button
//        }
//
//        // Initialize RecyclerView
//        recyclerView = findViewById(R.id.recyclerViewEvents);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Initialize eventList with Event objects
//        eventList = new ArrayList<>();
//        eventList.add(new Event("Ultra Music Festival", "March 22-24, 2024", "Music Festival"));
//        eventList.add(new Event("Rock the World", "March 3, 2024", "Music Festival"));
//
//
//        // Set up the adapter
//        eventAdapter = new EventAdapter(eventList);
//        recyclerView.setAdapter(eventAdapter);
//
//        // Set up the ItemClickListener to handle the event item click
//        eventAdapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                // Get the clicked event data
//                Event clickedEvent = eventList.get(position);
//
//                // Create an Intent to open BookingActivity
//                Intent intent = new Intent(OtherActivitytemp.this, BookingActivitytemp.class);
//
//                // Pass the event data to BookingActivity
//                intent.putExtra("EVENT_NAME", clickedEvent.getName());
//                intent.putExtra("CATEGORY", clickedEvent.getCategory());
//                intent.putExtra("CURRENT_ACTIVITY", "OtherActivity");  // Pass the current activity name
//
//                // Start the BookingActivity
//                startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle the action of the back button
//        if (item.getItemId() == android.R.id.home) {
//            Intent intent = new Intent(OtherActivitytemp.this, MainActivitytemp.class); // Go back to MainActivity
//            startActivity(intent);
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
