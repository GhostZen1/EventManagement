package edu.my.utem.ftmk.projecteventmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminUpdateEvent extends AppCompatActivity {

    TextView txtName,txtDate,txtPrice,txtType;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_update_event);

        txtType = findViewById(R.id.txtEventType);
        txtName = findViewById(R.id.txtEventName);
        txtDate = findViewById(R.id.txtEventDate);
        txtPrice = findViewById(R.id.txtEventPrice);
        btnUpdate = findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}