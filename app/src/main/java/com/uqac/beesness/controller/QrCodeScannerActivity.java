package com.uqac.beesness.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.uqac.beesness.R;

public class QrCodeScannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scanner);
    }
}