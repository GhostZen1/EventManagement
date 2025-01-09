package edu.my.utem.ftmk.projecteventmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminAddEvent extends AppCompatActivity {

    EditText txtName, txtDate, txtPrice, txtCategory;
    Button btm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_event);

        txtName = findViewById(R.id.txtName);
        txtDate = findViewById(R.id.txtDate);
        txtPrice = findViewById(R.id.txtPrice);
        txtCategory = findViewById(R.id.txtCategory);
        btm = findViewById(R.id.buttonSubmit);

//        btm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }
}