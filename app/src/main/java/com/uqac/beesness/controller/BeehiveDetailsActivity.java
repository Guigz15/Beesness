package com.uqac.beesness.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.uqac.beesness.R;
import com.uqac.beesness.database.DAOBeehives;
import com.uqac.beesness.model.BeehiveModel;

public class BeehiveDetailsActivity extends AppCompatActivity {

    private String idBeehive;
    private TextView beehiveNameTextView;
    private ImageButton infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beehive_details);

        beehiveNameTextView = findViewById(R.id.title_text);

        idBeehive = getIntent().getStringExtra("idBeehive");
        DAOBeehives daoBeehives = new DAOBeehives();
        daoBeehives.find(idBeehive).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BeehiveModel beehive = snapshot.getChildren().iterator().next().getValue(BeehiveModel.class);
                assert beehive != null;
                beehiveNameTextView.setText(beehive.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        infoButton = findViewById(R.id.informations);
        /*infoButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            BeehiveDetailsMainFragment beehiveDetailsMainFragment = (BeehiveDetailsMainFragment) fragmentManager.findFragmentByTag("main");
            if (beehiveDetailsMainFragment != null && beehiveDetailsMainFragment.isVisible()) {
                infoButton.setImageResource(R.drawable.ic_baseline_info_48);
                fragmentTransaction.replace(R.id.fragment_container, new BeehiveDetailsInformationsFragment(), "informations");
            } else {
                infoButton.setImageResource(R.drawable.ic_outline_info_48);
                fragmentTransaction.replace(R.id.fragment_container, new BeehiveDetailsMainFragment(), "main");
            }
            fragmentTransaction.commit();
        });*/
    }

    public String getIdBeehive() {
        return idBeehive;
    }
}