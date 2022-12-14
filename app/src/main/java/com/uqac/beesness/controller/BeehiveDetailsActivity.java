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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.uqac.beesness.R;
import com.uqac.beesness.database.DAOBeehives;
import com.uqac.beesness.model.BeehiveModel;

/**
 * Activity for details of a beehive
 */
public class BeehiveDetailsActivity extends AppCompatActivity {

    private String idBeehive;
    private TextView beehiveNameTextView;
    private ImageButton infoButton;
    private DAOBeehives daoBeehives;
    private BottomSheetDialog deleteBeehiveDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beehive_details);

        beehiveNameTextView = findViewById(R.id.title_text);
        infoButton = findViewById(R.id.informations);

        idBeehive = getIntent().getStringExtra("idBeehive");
        daoBeehives = new DAOBeehives();
        daoBeehives.find(idBeehive).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BeehiveModel beehive = dataSnapshot.getValue(BeehiveModel.class);
                    assert beehive != null;
                    beehiveNameTextView.setText(beehive.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        infoButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            BeehiveDetailsMainFragment beehiveDetailsMainFragment = (BeehiveDetailsMainFragment) fragmentManager.findFragmentByTag("main");
            if (beehiveDetailsMainFragment != null && beehiveDetailsMainFragment.isVisible()) {
                infoButton.setImageResource(R.drawable.ic_baseline_info_48);
                fragmentTransaction.replace(R.id.fragment_container, new BeehiveDetailsInfoFragment(), "informations");
            } else {
                infoButton.setImageResource(R.drawable.ic_outline_info_48);
                fragmentTransaction.replace(R.id.fragment_container, new BeehiveDetailsMainFragment(), "main");
            }
            fragmentTransaction.commit();
        });

        ImageButton editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddUpdateBeehiveActivity.class);
            intent.putExtra("idBeehive", idBeehive);
            startActivity(intent);
        });

        ImageButton deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> showDialog());

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
    }

    /**
     * Show the dialog to confirm the deletion of a beehive
     */
    private void showDialog() {
        deleteBeehiveDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View deleteBeehiveView = getLayoutInflater().inflate(R.layout.delete_beehive_confirmation, findViewById(R.id.bottom_sheet_container));

        deleteBeehiveView.findViewById(R.id.delete_button).setOnClickListener(v ->
            daoBeehives.delete(idBeehive).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "La ruche a ??t?? supprim??", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                }
            }
        ));

        deleteBeehiveView.findViewById(R.id.cancel_button).setOnClickListener(v -> deleteBeehiveDialog.dismiss());

        deleteBeehiveDialog.setContentView(deleteBeehiveView);
        deleteBeehiveDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (deleteBeehiveDialog != null) {
            deleteBeehiveDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (deleteBeehiveDialog != null) {
            deleteBeehiveDialog.show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (deleteBeehiveDialog != null) {
            deleteBeehiveDialog.dismiss();
        }
    }

    /**
     * Get the id of the beehive
     * @return the id of the beehive
     */
    public String getIdBeehive() {
        return idBeehive;
    }
}