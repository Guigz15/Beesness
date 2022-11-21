package com.uqac.beesness.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.uqac.beesness.R;
import com.uqac.beesness.database.DAOApiaries;
import com.uqac.beesness.model.ApiaryModel;
import com.uqac.beesness.model.LocationModel;

import java.util.HashMap;
import java.util.Objects;

public class AddUpdateApiaryActivity extends AppCompatActivity {

    private EditText apiaryName, apiaryEnvironment, apiaryDescription, apiaryLongitude, apiaryLatitude;
    private DAOApiaries daoApiaries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apiary);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        apiaryName = findViewById(R.id.apiaryName);
        apiaryEnvironment = findViewById(R.id.apiaryEnvironmentDetails);
        apiaryDescription = findViewById(R.id.apiaryDescriptionDetails);
        apiaryLongitude = findViewById(R.id.longitude);
        apiaryLatitude = findViewById(R.id.latitude);

        daoApiaries = new DAOApiaries();

        if (getIntent().getStringExtra("idApiary") != null) {
            setTitle("Modifier le rucher");
            String idApiary = getIntent().getStringExtra("idApiary");
            daoApiaries.find(idApiary).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ApiaryModel apiary = snapshot.getChildren().iterator().next().getValue(ApiaryModel.class);
                    assert apiary != null;
                    apiaryName.setText(apiary.getName());
                    apiaryEnvironment.setText(apiary.getEnvironment());
                    apiaryDescription.setText(apiary.getDescription());
                    apiaryLongitude.setText(String.valueOf(apiary.getLocation().getLongitude()));
                    apiaryLatitude.setText(String.valueOf(apiary.getLocation().getLatitude()));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

        Button pinOnMapButton = findViewById(R.id.situerCarteButton);
        pinOnMapButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> saveApiary());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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
            daoApiaries = new DAOApiaries();

            if (getIntent().getStringExtra("idApiary") == null) {
                String idApiary = daoApiaries.getKey();
                assert idApiary != null;
                ApiaryModel apiary = new ApiaryModel(idApiary, apiaryNameText, apiaryEnvironmentText, apiaryDescriptionText, location, userId);
                daoApiaries.add(apiary).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Rucher ajouté", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Erreur lors de l'ajout du rucher", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                String idApiary = getIntent().getStringExtra("idApiary");
                assert idApiary != null;
                HashMap<String, Object> apiaryUpdateMap = new HashMap<>();
                apiaryUpdateMap.put("name", apiaryNameText);
                apiaryUpdateMap.put("environment", apiaryEnvironmentText);
                apiaryUpdateMap.put("description", apiaryDescriptionText);
                apiaryUpdateMap.put("location", location);
                daoApiaries.update(idApiary, apiaryUpdateMap).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Rucher modifié", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Erreur lors de la modification du rucher", Toast.LENGTH_LONG).show();
                    }
                });
            }
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