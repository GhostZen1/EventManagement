package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText etName = findViewById(R.id.etSignupName);
        EditText etEmail = findViewById(R.id.etSignupEmail);
        EditText etPassword = findViewById(R.id.etSignupPassword);
        Button btnSignup = findViewById(R.id.btnSignup);
        TextView tvLogin = findViewById(R.id.tvLogin);
        EditText etIC = findViewById(R.id.etSignupIC);

        btnSignup.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String ic = etIC.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || ic.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                SqLite db = new SqLite(getApplicationContext());
                User user = new User(name,password,email,ic,"user");
                long userId = db.addUser(user);
                if(userId == 0){
                    Toast.makeText(this, "Signup Failed", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
