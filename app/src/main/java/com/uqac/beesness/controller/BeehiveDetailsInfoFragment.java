package com.uqac.beesness.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uqac.beesness.databinding.FragmentBeehiveDetailsInfoBinding;

import java.util.Objects;

public class BeehiveDetailsInfoFragment extends Fragment {

    private FragmentBeehiveDetailsInfoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBeehiveDetailsInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String idBeehive = ((BeehiveDetailsActivity) requireActivity()).getIdBeehive();

        DatabaseReference beehivesRef = FirebaseDatabase.getInstance().getReference("Beehives");
        beehivesRef.child(idBeehive).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                binding.beehiveDetailsText.setText(Objects.requireNonNull(task.getResult().child("details").getValue()).toString());
                binding.beehiveTypeText.setText(Objects.requireNonNull(task.getResult().child("type").getValue()).toString());
                binding.queenOriginText.setText(Objects.requireNonNull(task.getResult().child("beeQueen").child("origin").getValue()).toString());
                binding.ligneText.setText(Objects.requireNonNull(task.getResult().child("beeQueen").child("bloodline").getValue()).toString());
                binding.queenYearText.setText(Objects.requireNonNull(task.getResult().child("beeQueen").child("birthYear").getValue()).toString());
            }
        });

        return root;
    }
}
