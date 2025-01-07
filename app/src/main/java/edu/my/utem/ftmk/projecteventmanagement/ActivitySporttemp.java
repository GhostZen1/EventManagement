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
//public class ActivitySporttemp extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private EventAdapter eventAdapter;
//    private List<Event> eventList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_event_listtemp);
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show the back button
//        }
//
//        recyclerView = findViewById(R.id.recyclerViewEvents);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        eventList = new ArrayList<>();
//        eventList.add(new Event("Basketball Finals", "February 2, 2024", "Sports"));
//        eventList.add(new Event("Formula 1 Grand Prix", "September 29, 2024", "Sports"));
//        eventList.add(new Event("NBA Finals 2024", "June 12, 2024", "Sports"));
//        eventList.add(new Event("Wimbledon 2024 (Men's Final)", "July 7, 2024", "Sports"));
//        eventList.add(new Event("FIFA World Cup Qualifiers", "October 18, 2024", "Sports"));
//
//        eventAdapter = new EventAdapter(eventList);
//        recyclerView.setAdapter(eventAdapter);
//
//        eventAdapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Event clickedEvent = eventList.get(position);
//
//                Intent intent = new Intent(ActivitySporttemp.this, BookingActivitytemp.class);
//
//                intent.putExtra("EVENT_NAME", clickedEvent.getName());
//                intent.putExtra("CATEGORY", clickedEvent.getCategory());
//                intent.putExtra("CURRENT_ACTIVITY", "ActivitySport");
//
//                startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            Intent intent = new Intent(ActivitySporttemp.this, MainActivitytemp.class); // Go back to MainActivity
//            startActivity(intent);
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
