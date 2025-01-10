package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfile extends AppCompatActivity {

    public EditText txtName, txtPassword, txtEmail, txtIc;
    public int userId;
    public Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        txtName = findViewById(R.id.txtName);
        txtPassword = findViewById(R.id.txtPassword);
        txtEmail = findViewById(R.id.txtEmail);
        txtIc = findViewById(R.id.txtIc);
        btnSave = findViewById(R.id.btnSaveProfile);

        // Get the userId from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        // Fetch user details from SQLite
        if (userId != -1) {
            SqLite sqLite = new SqLite(this);
            User user = sqLite.getUserDetails(userId);

            if (user != null) {
                // Set the user details in the EditText fields
                txtName.setText(user.getName());
                txtPassword.setText(user.getPassword());
                txtEmail.setText(user.getEmail());
                txtIc.setText(user.getIc());
            }
        }

        // Save button click listener to update user details
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collect updated data from EditText fields
                String updatedName = txtName.getText().toString();
                String updatedPassword = txtPassword.getText().toString();
                String updatedEmail = txtEmail.getText().toString();
                String updatedIc = txtIc.getText().toString();

                // Check if the fields are not empty
                if (!updatedName.isEmpty() && !updatedPassword.isEmpty() && !updatedEmail.isEmpty() && !updatedIc.isEmpty()) {
                    // Update the user details in the database
                    SqLite sqLite = new SqLite(UserProfile.this);
                    boolean isUpdated = sqLite.updateUserDetails(userId, updatedName, updatedPassword, updatedEmail, updatedIc);

                    if (isUpdated) {
                        Toast.makeText(UserProfile.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserProfile.this, "Failed to update profile.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserProfile.this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
