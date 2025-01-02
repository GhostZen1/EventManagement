package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventList = new ArrayList<>();
        eventList.add(new Event("Concert at Madison Square Garden", "January 15, 2024", "Concert"));
        eventList.add(new Event("Coldplay World Tour 2024", "March 15, 2024", "Concert"));
        eventList.add(new Event("Theater Play: Les Misérables", "December 1, 2024", "Theater"));
        eventList.add(new Event("Theater Play: The Lion King", "January 20, 2024", "Theater"));
        eventList.add(new Event("Sports Event: Basketball Finals", "February 2, 2024", "Sports"));
        eventList.add(new Event("Sports Event: Formula 1 Grand Prix", "September 29, 2024", "Sports"));
        eventList.add(new Event("Music Festival: Rock the World", "March 3, 2024", "Festival"));
        eventList.add(new Event("Other Festival: Art Basel 2024", "June 13-16, 2024", "Festival"));

        eventAdapter = new EventAdapter(eventList);
        recyclerView.setAdapter(eventAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(EventListActivity.this, MainActivity.class); // Go back to MainActivity
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
