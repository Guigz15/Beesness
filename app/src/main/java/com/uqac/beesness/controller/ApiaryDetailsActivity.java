package com.uqac.beesness.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uqac.beesness.MainActivity;
import com.uqac.beesness.R;

import java.util.Objects;

public class ApiaryDetailsActivity extends AppCompatActivity {

    String idApiary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiary_details);

        idApiary = getIntent().getStringExtra("idApiary");


        DatabaseReference apiariesRef = FirebaseDatabase.getInstance().getReference("Apiaries");
        Query query = apiariesRef.orderByChild("idApiary").equalTo(idApiary);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                    String longitude = Objects.requireNonNull(dataSnapshot.child("location").child("longitude").getValue()).toString();
                    String latitude = Objects.requireNonNull(dataSnapshot.child("location").child("latitude").getValue()).toString();

                    TextView nameTextView = findViewById(R.id.title_text);
                    TextView longitudeTextView = findViewById(R.id.longitude_value);
                    TextView latitudeTextView = findViewById(R.id.latitude_value);

                    nameTextView.setText(name);
                    longitudeTextView.setText(longitude);
                    latitudeTextView.setText(latitude);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ImageButton deleteApiarieButton = findViewById(R.id.delete_button);
        deleteApiarieButton.setOnClickListener(v -> {
            showDialog();
        });
    }

    private void showDialog() {
        BottomSheetDialog deleteAccountDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View deleteAccountView = getLayoutInflater().inflate(R.layout.delete_apiary_confirmation, findViewById(R.id.bottom_sheet_container));

        deleteAccountView.findViewById(R.id.delete_button).setOnClickListener(v -> {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Apiaries").child(idApiary);
            //Suppression de l'utilisateur
            ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ApiaryDetailsActivity.this, "Le rucher a été supprimé", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ApiaryDetailsActivity.this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        deleteAccountView.findViewById(R.id.cancel_button).setOnClickListener(v -> {
            deleteAccountDialog.dismiss();
        });

        deleteAccountDialog.setContentView(deleteAccountView);
        deleteAccountDialog.show();
    }
}