package com.uqac.beesness.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.uqac.beesness.R;
import com.uqac.beesness.databinding.FragmentApiariesBinding;
import com.uqac.beesness.model.ApiariesViewModel;
import com.uqac.beesness.model.ApiaryModel;

import java.util.Objects;

public class ApiariesFragment extends Fragment {

    private FragmentApiariesBinding binding;
    private ApiariesAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ApiariesViewModel apiariesViewModel = new ViewModelProvider(this).get(ApiariesViewModel.class);

        binding = FragmentApiariesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        requireActivity().findViewById(R.id.space).setVisibility(View.VISIBLE);
        ((TextView) requireActivity().findViewById(R.id.title_text)).setText("Ruchers");

        ImageButton addApiarieButton = requireActivity().findViewById(R.id.toolbar_add);
        addApiarieButton.setVisibility(View.VISIBLE);
        addApiarieButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddApiaryActivity.class);
            startActivity(intent);
        });

        DatabaseReference apiariesRef = FirebaseDatabase.getInstance().getReference("Apiaries");
        Query query = apiariesRef.orderByChild("idUser").equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        RecyclerView apiariesRecyclerView = root.findViewById(R.id.apiaries_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        apiariesRecyclerView.setItemAnimator(null);
        FirebaseRecyclerOptions<ApiaryModel> options = new FirebaseRecyclerOptions.Builder<ApiaryModel>()
                .setQuery(query, ApiaryModel.class)
                .build();
        adapter = new ApiariesAdapter(options);

        apiariesRecyclerView.setLayoutManager(linearLayoutManager);
        apiariesRecyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}