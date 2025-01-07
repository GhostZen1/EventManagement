package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActivityConcerttemp extends AppCompatActivity {

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

        // Fetch events from the database
        eventList = dbHelper.fetchEventsDetail();

        eventAdapter = new EventAdapter(eventList);
        recyclerView.setAdapter(eventAdapter);

        eventAdapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Event clickedEvent = eventList.get(position);

                Intent intent = new Intent(ActivityConcerttemp.this, BookingActivitytemp.class);
                intent.putExtra("EVENT_NAME", clickedEvent.getName());
                intent.putExtra("CATEGORY", clickedEvent.getCategory());
                intent.putExtra("CURRENT_ACTIVITY", "ActivityConcert");  // Pass the current activity name

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ActivityConcerttemp.this, MainActivitytemp.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
