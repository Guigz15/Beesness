package com.uqac.beesness.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uqac.beesness.R;
import com.uqac.beesness.database.DAOApiaries;
import com.uqac.beesness.database.DAOBeehives;
import com.uqac.beesness.database.DAOHoneySuper;
import com.uqac.beesness.database.DAOVisits;
import com.uqac.beesness.databinding.FragmentHomeBinding;
import com.uqac.beesness.model.UserModel;
import com.uqac.beesness.model.VisitModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private PlanningAdapter healthCheckAdapter;
    private PlanningAdapter feedingAdapter;
    private PlanningAdapter harvestAdapter;
    private PlanningAdapter otherAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        requireActivity().findViewById(R.id.toolbar_add).setVisibility(View.GONE);
        requireActivity().findViewById(R.id.space).setVisibility(View.GONE);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users");
        if (user != null) {
            userReference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    assert userModel != null;
                    ((TextView) requireActivity().findViewById(R.id.title_text)).setText("Bienvenue " + userModel.getFirstname());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

        // Update apiaries number
        DAOApiaries daoApiaries = new DAOApiaries();
        daoApiaries.findAllForUser().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.apiariesNumber.setText(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Erreur lors de la récupération des ruchers", Toast.LENGTH_SHORT).show();
            }
        });

        // Update beehives number
        DAOBeehives daoBeehives = new DAOBeehives();
        daoBeehives.findAllForUser().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.beehivesNumber.setText(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Erreur lors de la récupération des ruches", Toast.LENGTH_SHORT).show();
            }
        });

        // Update honeysuper number
        DAOHoneySuper daoHoneySuper = new DAOHoneySuper();
        daoHoneySuper.findAllForUser().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.honeySupersNumber.setText(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Erreur lors de la récupération des hausses", Toast.LENGTH_SHORT).show();
            }
        });

        DAOVisits daoVisits = new DAOVisits();
        Query healthCheckQuery = daoVisits.findAllForCurrentUserAndType("SANITARY_CONTROL");
        RecyclerView healthCheckRecyclerView = binding.healthCheckList;
        healthCheckRecyclerView.setItemAnimator(null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        FirebaseRecyclerOptions<VisitModel> healthCheckOptions = new FirebaseRecyclerOptions.Builder<VisitModel>()
                .setQuery(healthCheckQuery, VisitModel.class)
                .build();
        healthCheckAdapter = new PlanningAdapter(healthCheckOptions);
        healthCheckRecyclerView.setLayoutManager(linearLayoutManager);
        healthCheckRecyclerView.setAdapter(healthCheckAdapter);

        Query feedingQuery = daoVisits.findAllForCurrentUserAndType("FEEDING");
        RecyclerView feedingRecyclerView = binding.feedingList;
        feedingRecyclerView.setItemAnimator(null);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        FirebaseRecyclerOptions<VisitModel> feedingOptions = new FirebaseRecyclerOptions.Builder<VisitModel>()
                .setQuery(feedingQuery, VisitModel.class)
                .build();
        feedingAdapter = new PlanningAdapter(feedingOptions);
        feedingRecyclerView.setLayoutManager(linearLayoutManager2);
        feedingRecyclerView.setAdapter(feedingAdapter);

        Query harvestingQuery = daoVisits.findAllForCurrentUserAndType("HARVESTING");
        RecyclerView harvestingRecyclerView = binding.harvestingList;
        harvestingRecyclerView.setItemAnimator(null);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        FirebaseRecyclerOptions<VisitModel> harvestOptions = new FirebaseRecyclerOptions.Builder<VisitModel>()
                .setQuery(harvestingQuery, VisitModel.class)
                .build();
        harvestAdapter = new PlanningAdapter(harvestOptions);
        harvestingRecyclerView.setLayoutManager(linearLayoutManager3);
        harvestingRecyclerView.setAdapter(harvestAdapter);

        Query otherQuery = daoVisits.findAllForCurrentUserAndType("OTHER");
        RecyclerView otherRecyclerView = binding.otherList;
        otherRecyclerView.setItemAnimator(null);
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        FirebaseRecyclerOptions<VisitModel> otherOptions = new FirebaseRecyclerOptions.Builder<VisitModel>()
                .setQuery(otherQuery, VisitModel.class)
                .build();
        otherAdapter = new PlanningAdapter(otherOptions);
        otherRecyclerView.setLayoutManager(linearLayoutManager4);
        otherRecyclerView.setAdapter(otherAdapter);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        healthCheckAdapter.startListening();
        feedingAdapter.startListening();
        harvestAdapter.startListening();
        otherAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        healthCheckAdapter.stopListening();
        feedingAdapter.stopListening();
        harvestAdapter.stopListening();
        otherAdapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}