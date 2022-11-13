package com.uqac.beesness.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.uqac.beesness.R;

public class ApiaryDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiary_details);

        String apiaryName = getIntent().getStringExtra("apiary");
        ((TextView) findViewById(R.id.title_text)).setText(apiaryName);
    }
}