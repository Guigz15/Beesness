package com.uqac.beesness.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uqac.beesness.MainActivity;
import com.uqac.beesness.R;
import com.uqac.beesness.model.BeeQueenModel;
import com.uqac.beesness.model.BeehiveModel;
import com.uqac.beesness.model.UserModel;

public class AddBeehiveActivity extends AppCompatActivity {

    private EditText beehiveName, beehiveDetails, beehiveType, queenOrigin, queenLine, queenBirthYear;
    private String idApiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beehive);

        idApiary = getIntent().getStringExtra("idApiary");

        beehiveName = findViewById(R.id.beehiveName);
        beehiveDetails = findViewById(R.id.beehiveDetails);
        beehiveType = findViewById(R.id.beehiveType);

        queenOrigin = findViewById(R.id.queenOrigin);
        queenLine = findViewById(R.id.queenLine);
        queenBirthYear = findViewById(R.id.queenBirthYear);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> saveBeehive());
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
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            String idBeehive = reference.child("Beehives").push().getKey();

            BeeQueenModel queen = new BeeQueenModel(queenOriginText, queenLineText, queenBirthYearText);
            BeehiveModel beehive = new BeehiveModel(idBeehive, beehiveNameText, beehiveTypeText, beehiveDetailsText, idApiary, queen);

            assert idBeehive != null;
            reference.child("Beehives").child(idBeehive)
                    .setValue(beehive)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Ruche ajoutée", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Erreur lors de l'ajout de la ruche", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}