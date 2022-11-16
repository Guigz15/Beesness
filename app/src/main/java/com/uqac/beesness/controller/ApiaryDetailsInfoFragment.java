package com.uqac.beesness.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uqac.beesness.database.DAOApiaries;
import com.uqac.beesness.databinding.FragmentApiaryDetailsInfoBinding;

import java.util.Objects;

public class ApiaryDetailsInfoFragment extends Fragment {

    private FragmentApiaryDetailsInfoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentApiaryDetailsInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String idApiary = ((ApiaryDetailsActivity) requireActivity()).getIdApiary();

        DatabaseReference apiariesRef = FirebaseDatabase.getInstance().getReference("Apiaries");
        apiariesRef.child(idApiary).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                binding.longitudeValue.setText(Objects.requireNonNull(task.getResult().child("location").child("longitude").getValue()).toString());
                binding.latitudeValue.setText(Objects.requireNonNull(task.getResult().child("location").child("latitude").getValue()).toString());

                if (!Objects.requireNonNull(task.getResult().child("environment").getValue()).toString().isEmpty()) {
                    binding.envLinearLayout.setVisibility(View.VISIBLE);
                    binding.apiaryEnvironmentText.setText(Objects.requireNonNull(task.getResult().child("environment").getValue()).toString());
                } else {
                    binding.envLinearLayout.setVisibility(View.GONE);
                }

                if (!Objects.requireNonNull(task.getResult().child("description").getValue()).toString().isEmpty()) {
                    binding.descriptionLinearLayout.setVisibility(View.VISIBLE);
                    binding.apiaryDescriptionText.setText(Objects.requireNonNull(task.getResult().child("description").getValue()).toString());
                } else {
                    binding.descriptionLinearLayout.setVisibility(View.GONE);
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}