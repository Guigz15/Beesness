package com.uqac.beesness.controller;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.uqac.beesness.R;
import com.uqac.beesness.databinding.FragmentApiaryDetailsMainBinding;
import com.uqac.beesness.model.BeehiveModel;

/**
 * Fragment for the main page of the apiary details activity
 */
public class ApiaryDetailsMainFragment extends Fragment {

    private FragmentApiaryDetailsMainBinding binding;
    private BeehivesAdapter adapter;
    private RecyclerView beehivesRecyclerView;
    private String idApiary;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentApiaryDetailsMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        idApiary = ((ApiaryDetailsActivity) requireActivity()).getIdApiary();

        ImageButton addBeehiveButton = binding.imageButton;
        addBeehiveButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddUpdateBeehiveActivity.class);
            intent.putExtra("idApiary", idApiary);
            startActivity(intent);
        });

        beehivesRecyclerView = root.findViewById(R.id.beehives_list);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        DatabaseReference beehivesRef = FirebaseDatabase.getInstance().getReference("Beehives");
        Query query = beehivesRef.orderByChild("idApiary").equalTo(idApiary);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        beehivesRecyclerView.setItemAnimator(null);
        FirebaseRecyclerOptions<BeehiveModel> options = new FirebaseRecyclerOptions.Builder<BeehiveModel>()
                .setQuery(query, BeehiveModel.class)
                .build();
        adapter = new BeehivesAdapter(options);
        beehivesRecyclerView.setLayoutManager(linearLayoutManager);
        beehivesRecyclerView.setAdapter(adapter);

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