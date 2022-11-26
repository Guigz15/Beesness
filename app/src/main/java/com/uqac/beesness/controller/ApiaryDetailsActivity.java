package com.uqac.beesness.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uqac.beesness.R;
import com.uqac.beesness.database.DAOApiaries;
import com.uqac.beesness.database.DAOBeehives;
import com.uqac.beesness.database.DAOHoneySuper;
import com.uqac.beesness.model.ApiaryModel;

public class ApiaryDetailsActivity extends AppCompatActivity {

    private String idApiary;
    private TextView apiaryNameTextView;
    private ImageButton infoButton, editButton, backButton;
    private DAOApiaries daoApiaries;
    private BottomSheetDialog deleteApiaryDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiary_details);

        apiaryNameTextView = findViewById(R.id.title_text);

        idApiary = getIntent().getStringExtra("idApiary");
        daoApiaries = new DAOApiaries();
        daoApiaries.find(idApiary).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ApiaryModel apiary = dataSnapshot.getValue(ApiaryModel.class);
                    assert apiary != null;
                    apiaryNameTextView.setText(apiary.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        infoButton = findViewById(R.id.informations);
        infoButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            ApiaryDetailsMainFragment apiaryDetailsMainFragment = (ApiaryDetailsMainFragment) fragmentManager.findFragmentByTag("main");
            if (apiaryDetailsMainFragment != null && apiaryDetailsMainFragment.isVisible()) {
                infoButton.setImageResource(R.drawable.ic_baseline_info_48);
                fragmentTransaction.replace(R.id.fragmentContainerView, new ApiaryDetailsInfoFragment(), "info");
            } else {
                infoButton.setImageResource(R.drawable.ic_outline_info_48);
                fragmentTransaction.replace(R.id.fragmentContainerView, new ApiaryDetailsMainFragment(), "main");
            }
            fragmentTransaction.commit();
        });

        ImageButton deleteApiaryButton = findViewById(R.id.delete_button);
        deleteApiaryButton.setOnClickListener(v -> showDialog());

        editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddUpdateApiaryActivity.class);
            intent.putExtra("idApiary", idApiary);
            startActivity(intent);
        });

        backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (deleteApiaryDialog != null) {
            deleteApiaryDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (deleteApiaryDialog != null) {
            deleteApiaryDialog.show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (deleteApiaryDialog != null) {
            deleteApiaryDialog.dismiss();
        }
    }

    private void showDialog() {
        deleteApiaryDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View deleteApiaryView = getLayoutInflater().inflate(R.layout.delete_apiary_confirmation, findViewById(R.id.bottom_sheet_container));

        deleteApiaryView.findViewById(R.id.delete_button).setOnClickListener(v -> {
            daoApiaries.delete(idApiary).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ApiaryDetailsActivity.this, "Le rucher a été supprimé", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ApiaryDetailsActivity.this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                }
            });
        });

        deleteApiaryView.findViewById(R.id.cancel_button).setOnClickListener(v -> {
            deleteApiaryDialog.dismiss();
        });

        deleteApiaryDialog.setContentView(deleteApiaryView);
        deleteApiaryDialog.show();
    }

    public String getIdApiary() {
        return idApiary;
    }

    public String getApiaryName() {
        return apiaryNameTextView.getText().toString();
    }
}