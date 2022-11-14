package com.uqac.beesness.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uqac.beesness.R;

import java.util.Objects;

public class ApiaryDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiary_details);

        String idApiary = getIntent().getStringExtra("idApiary");

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
    }
}