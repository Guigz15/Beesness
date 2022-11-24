package com.uqac.beesness.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.uqac.beesness.R;
import com.uqac.beesness.database.DAOBeehives;
import com.uqac.beesness.model.BeeQueenModel;
import com.uqac.beesness.model.BeehiveModel;

import java.util.HashMap;
import java.util.Objects;

public class AddUpdateBeehiveActivity extends AppCompatActivity {

    private EditText beehiveName, beehiveDetails, beehiveType, queenOrigin, queenLine, queenBirthYear;
    private String idApiary;
    private DAOBeehives daoBeehives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beehive);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        idApiary = getIntent().getStringExtra("idApiary");

        beehiveName = findViewById(R.id.beehiveName);
        beehiveDetails = findViewById(R.id.beehiveDetails);
        beehiveType = findViewById(R.id.beehiveType);

        queenOrigin = findViewById(R.id.queenOrigin);
        queenLine = findViewById(R.id.queenLine);
        queenBirthYear = findViewById(R.id.queenBirthYear);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> saveBeehive());

        daoBeehives = new DAOBeehives();

        if (getIntent().getStringExtra("idBeehive") != null) {
            setTitle("Modifier la ruche");
            String idBeehive = getIntent().getStringExtra("idBeehive");
            daoBeehives.find(idBeehive).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        BeehiveModel beehive = dataSnapshot.getValue(BeehiveModel.class);
                        assert beehive != null;
                        beehiveName.setText(beehive.getName());
                        beehiveDetails.setText(beehive.getDetails());
                        beehiveType.setText(beehive.getType());
                        queenOrigin.setText(beehive.getBeeQueen().getOrigin());
                        queenLine.setText(beehive.getBeeQueen().getBloodline());
                        queenBirthYear.setText(String.valueOf(beehive.getBeeQueen().getBirthYear()));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void saveBeehive() {
        String beehiveNameText = beehiveName.getText().toString();
        String beehiveDetailsText = beehiveDetails.getText().toString();
        String beehiveTypeText = beehiveType.getText().toString();

        String queenOriginText = queenOrigin.getText().toString();
        String queenLineText = queenLine.getText().toString();
        String queenBirthYearText = queenBirthYear.getText().toString();

        if (beehiveNameText.isEmpty()) {
            beehiveName.setError("Veuillez entrer un nom de ruche");
            beehiveName.requestFocus();
        }

        if (!beehiveNameText.isEmpty()) {
            if (getIntent().getStringExtra("idBeehive") == null) {
                String idBeehive = daoBeehives.getKey();
                assert idBeehive != null;
                BeeQueenModel queen = new BeeQueenModel(queenOriginText, queenLineText, queenBirthYearText);
                BeehiveModel beehive = new BeehiveModel(idBeehive, beehiveNameText, beehiveTypeText, beehiveDetailsText, idApiary, queen);
                daoBeehives.add(beehive).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Ruche ajoutée", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Erreur lors de l'ajout de la ruche", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                String idBeehive = getIntent().getStringExtra("idBeehive");
                assert idBeehive != null;
                HashMap<String, Object> beehiveUpdateMap = new HashMap<>();
                beehiveUpdateMap.put("name", beehiveNameText);
                beehiveUpdateMap.put("details", beehiveDetailsText);
                beehiveUpdateMap.put("type", beehiveTypeText);
                beehiveUpdateMap.put("beeQueen/origin", queenOriginText);
                beehiveUpdateMap.put("beeQueen/bloodline", queenLineText);
                beehiveUpdateMap.put("beeQueen/birthYear", queenBirthYearText);
                daoBeehives.update(idBeehive, beehiveUpdateMap).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Ruche modifiée", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Erreur lors de la modification de la ruche", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}