package com.uqac.beesness.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.uqac.beesness.R;

import org.osmdroid.views.MapView;

public class AddApiarieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apiarie);

        Button pinOnMapButton = findViewById(R.id.situerCarteButton);
        pinOnMapButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });
    }
}