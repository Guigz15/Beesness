package com.uqac.beesness.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.uqac.beesness.R;

public class ApiarieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiarie_details);

        String apiarieName = getIntent().getStringExtra("apiarie");
        ((TextView) findViewById(R.id.title_text)).setText(apiarieName);
    }
}