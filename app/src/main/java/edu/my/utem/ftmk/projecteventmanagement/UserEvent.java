package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserEvent extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;
    private SqLite dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_listtemp);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new SqLite(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get eventTypeId from the intent
        int eventTypeId = getIntent().getIntExtra("eventTypeId", -1);

        // Fetch events for the specific event type
        eventList = dbHelper.fetchEventsByType(eventTypeId);

        // Set up the adapter
        eventAdapter = new EventAdapter(eventList);
        recyclerView.setAdapter(eventAdapter);

        eventAdapter.setOnItemClickListener((view, position) -> {
            Event clickedEvent = eventList.get(position);

            Intent intent = new Intent(UserEvent.this, UserBooking.class);
            intent.putExtra("EVENT_ID", clickedEvent.getId());
            intent.putExtra("EVENT_NAME", clickedEvent.getName());
            intent.putExtra("EVENT_PRICE", clickedEvent.getPrice());
            intent.putExtra("CATEGORY", clickedEvent.getCategory());
            startActivity(intent);
        });
    }
}
