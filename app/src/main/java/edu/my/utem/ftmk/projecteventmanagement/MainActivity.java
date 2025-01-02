package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout buttonContainer;
    private SqLite dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonContainer = findViewById(R.id.buttonContainer);
        dbHelper = new SqLite(this);

        List<EventType> categories = dbHelper.getEventCategories();

        for (EventType event : categories) {
            Button button = new Button(this);
            button.setText(event.getName());

            button.setOnClickListener(v -> openEventCategory(event.getId(), event.getName()));

            buttonContainer.addView(button);
        }
    }

    private void openEventCategory(int id, String name) {
        Class<?> activityClass;

        switch (name) {
            case "Concerts":
                activityClass = ActivityConcert.class;
                break;
            case "Sports":
                activityClass = ActivitySport.class;
                break;
            case "Theater":
                activityClass = ActivityTheater.class;
                break;
            default:
                activityClass = OtherActivity.class;
                break;
        }

        Intent intent = new Intent(MainActivity.this, activityClass);
        intent.putExtra("CATEGORY_ID", id); // Pass ID
        intent.putExtra("CATEGORY_NAME", name); // Pass Name
        startActivity(intent);
    }
}
