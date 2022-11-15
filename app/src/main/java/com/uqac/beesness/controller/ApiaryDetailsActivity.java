package com.uqac.beesness.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uqac.beesness.R;
import com.uqac.beesness.model.ApiaryModel;

import java.util.Objects;

public class ApiaryDetailsActivity extends AppCompatActivity {

    private String idApiary;
    private TextView apiaryName;
    private ImageButton infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiary_details);

        apiaryName = findViewById(R.id.title_text);

        idApiary = getIntent().getStringExtra("idApiary");
        DatabaseReference apiariesRef = FirebaseDatabase.getInstance().getReference("Apiaries");
        Query query = apiariesRef.orderByChild("idApiary").equalTo(idApiary);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ApiaryModel apiary = dataSnapshot.getValue(ApiaryModel.class);
                    assert apiary != null;
                    apiaryName.setText(apiary.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        infoButton = findViewById(R.id.informations);
        infoButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            ApiaryDetailsMainFragment apiaryDetailsMainFragment = (ApiaryDetailsMainFragment) fragmentManager.findFragmentByTag("main");
            if (apiaryDetailsMainFragment != null && apiaryDetailsMainFragment.isVisible()) {
                infoButton.setImageResource(R.drawable.ic_baseline_info_48);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.replace(R.id.fragmentContainerView, new ApiaryDetailsInfoFragment(), "info");
                fragmentTransaction.commit();
            }

            ApiaryDetailsInfoFragment apiaryDetailsInfoFragment = (ApiaryDetailsInfoFragment) fragmentManager.findFragmentByTag("info");
            if (apiaryDetailsInfoFragment != null && apiaryDetailsInfoFragment.isVisible()) {
                infoButton.setImageResource(R.drawable.ic_outline_info_48);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.replace(R.id.fragmentContainerView, new ApiaryDetailsMainFragment(), "main");
                fragmentTransaction.commit();
            }
        });
    }

    public String getIdApiary() {
        return idApiary;
    }
}