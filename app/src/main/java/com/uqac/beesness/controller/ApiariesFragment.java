package com.uqac.beesness.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.uqac.beesness.R;
import com.uqac.beesness.database.DAOApiaries;
import com.uqac.beesness.databinding.FragmentApiariesBinding;
import com.uqac.beesness.model.ApiaryModel;

/**
 * Fragment to handle the apiaries tab
 */
public class ApiariesFragment extends Fragment {

    private FragmentApiariesBinding binding;
    private ApiariesAdapter adapter;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentApiariesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        requireActivity().findViewById(R.id.space).setVisibility(View.VISIBLE);
        ((TextView) requireActivity().findViewById(R.id.title_text)).setText("Ruchers");

        ImageButton addApiarieButton = requireActivity().findViewById(R.id.toolbar_add);
        addApiarieButton.setVisibility(View.VISIBLE);
        addApiarieButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddUpdateApiaryActivity.class);
            startActivity(intent);
        });

        DAOApiaries daoApiaries = new DAOApiaries();
        RecyclerView apiariesRecyclerView = root.findViewById(R.id.apiaries_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        apiariesRecyclerView.setItemAnimator(null);
        FirebaseRecyclerOptions<ApiaryModel> options = new FirebaseRecyclerOptions.Builder<ApiaryModel>()
                .setQuery(daoApiaries.findAllForUser(), ApiaryModel.class)
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