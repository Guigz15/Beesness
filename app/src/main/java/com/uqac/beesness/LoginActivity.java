package com.uqac.beesness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.connection_button);
        loginButton.setOnClickListener(v -> {
           Intent intent = new Intent(this, MainActivity.class);
           startActivity(intent);
           finish();
        });
    }
}
