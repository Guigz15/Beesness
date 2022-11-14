package com.uqac.beesness.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uqac.beesness.R;
import com.uqac.beesness.databinding.FragmentHomeBinding;
import com.uqac.beesness.model.UserModel;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

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
                    ((TextView) requireActivity().findViewById(R.id.title_text)).setText("Bienvenue " + userModel.getUsername());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

        DatabaseReference apiariesReference = FirebaseDatabase.getInstance().getReference("Apiaries");
        Query query = apiariesReference.orderByChild("idUser").equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.apiariesNumber.setText(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}