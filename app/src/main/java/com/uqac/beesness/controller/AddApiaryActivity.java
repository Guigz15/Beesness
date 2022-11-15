package com.uqac.beesness.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;
import com.uqac.beesness.MainActivity;
import com.uqac.beesness.R;
import com.uqac.beesness.model.ApiaryModel;
import com.uqac.beesness.model.LocationModel;

import java.util.Objects;

public class AddApiaryActivity extends AppCompatActivity {

    private EditText apiaryName, apiaryEnvironment, apiaryDescription, apiaryLongitude, apiaryLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apiary);

        apiaryName = findViewById(R.id.apiaryName);
        apiaryEnvironment = findViewById(R.id.apiaryEnvironmentDetails);
        apiaryDescription = findViewById(R.id.apiaryDescriptionDetails);
        apiaryLongitude = findViewById(R.id.longitude);
        apiaryLatitude = findViewById(R.id.latitude);

        Button pinOnMapButton = findViewById(R.id.situerCarteButton);
        pinOnMapButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> saveApiary());
    }

    private void saveApiary() {
        String apiaryNameText = apiaryName.getText().toString();
        String apiaryEnvironmentText = apiaryEnvironment.getText().toString();
        String apiaryDescriptionText = apiaryDescription.getText().toString();
        String apiaryLongitudeText = apiaryLongitude.getText().toString();
        String apiaryLatitudeText = apiaryLatitude.getText().toString();

        if (apiaryNameText.isEmpty()) {
            apiaryName.setError("Veuillez entrer un nom de rucher");
            apiaryName.requestFocus();
        }

        if (apiaryLongitudeText.isEmpty()) {
            apiaryLongitude.setError("Veuillez entrer une longitude");
            apiaryLongitude.requestFocus();
        }

        if (apiaryLatitudeText.isEmpty()) {
            apiaryLatitude.setError("Veuillez entrer une latitude");
            apiaryLatitude.requestFocus();
        }

        LocationModel location = null;
        if (!apiaryLongitudeText.isEmpty() && !apiaryLatitudeText.isEmpty() && locationIsValid(apiaryLongitudeText, apiaryLatitudeText)) {
            location = new LocationModel(Double.parseDouble(apiaryLatitudeText), Double.parseDouble(apiaryLongitudeText));
        }

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        if (!apiaryNameText.isEmpty() && location != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            String idApiary = reference.child("Apiaries").push().getKey();

            ApiaryModel apiary = new ApiaryModel(idApiary, apiaryNameText, apiaryEnvironmentText, apiaryDescriptionText, location, userId);

            assert idApiary != null;
            reference.child("Apiaries").child(idApiary)
                    .setValue(apiary)
                    .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Rucher ajouté", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(this, "Erreur lors de l'ajout du rucher", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private boolean locationIsValid(String longitudeText, String latitudeText) {
        double longitude = Double.parseDouble(longitudeText);
        double latitude = Double.parseDouble(latitudeText);
        boolean isValid = true;
        if (longitude < -180.0 || longitude > 180.0) {
            apiaryLongitude.setError("Veuillez entrer une longitude valide");
            apiaryLongitude.requestFocus();
            isValid = false;
        }
        if (latitude < -90.0 || latitude > 90.0) {
            apiaryLatitude.setError("Veuillez entrer une latitude valide");
            apiaryLatitude.requestFocus();
            isValid = false;
        }
        return isValid;
    }
}