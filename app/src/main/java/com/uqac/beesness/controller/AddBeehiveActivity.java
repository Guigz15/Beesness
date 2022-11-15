package com.uqac.beesness.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uqac.beesness.MainActivity;
import com.uqac.beesness.R;
import com.uqac.beesness.model.BeehiveModel;

import java.util.Objects;

public class AddBeehiveActivity extends AppCompatActivity {

    private EditText beehiveName, beehiveDetails, beehiveType, beeQueenOrigin, beeQueenBloodline, beeQueenBirthYear;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beehive);

        beehiveName = findViewById(R.id.rucheName);
        beehiveDetails = findViewById(R.id.rucheDetails);
        beehiveType = findViewById(R.id.rucheType);
        beeQueenOrigin = findViewById(R.id.reineOrigine);
        beeQueenBloodline = findViewById(R.id.reineLignee);
        beeQueenBirthYear = findViewById(R.id.reineAnnee);
        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(v -> saveBeehive());
    }

    private void saveBeehive() {
        String beehiveNameText = beehiveName.getText().toString();
        String beehiveDetailsText = beehiveDetails.getText().toString();
        String beehiveTypeText = beehiveType.getText().toString();
        String beeQueenOriginText = beeQueenOrigin.getText().toString();
        String beeQueenBloodlineText = beeQueenBloodline.getText().toString();
        String beeQueenBirthYearText = beeQueenBirthYear.getText().toString();

        if (beehiveNameText.isEmpty()) {
            beehiveName.setError("Veuillez entrer le nom de la ruche");
            beehiveName.requestFocus();
        }

        if (beehiveTypeText.isEmpty()) {
            beehiveType.setError("Veuillez entrer le type de la ruche");
            beehiveType.requestFocus();
        }

        if (beeQueenBirthYearText.isEmpty()) {
            beeQueenBirthYear.setError("Veuillez entrer l'année de naissance de la reine");
            beeQueenBirthYear.requestFocus();
        }

    }

}