package com.uqac.beesness.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uqac.beesness.AddApiarieActivity;
import com.uqac.beesness.R;
import com.uqac.beesness.databinding.FragmentApiariesBinding;
import com.uqac.beesness.model.ApiariesModel;

import java.util.ArrayList;

public class ApiariesFragment extends Fragment {

    private FragmentApiariesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentApiariesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageButton addApiarieButton = requireActivity().findViewById(R.id.toolbar_add);
        addApiarieButton.setVisibility(View.VISIBLE);
        addApiarieButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddApiarieActivity.class);
            startActivity(intent);
        });

        RecyclerView apiariesRecyclerView = root.findViewById(R.id.apiaries_list);

        ArrayList<ApiariesModel> apiariesList = new ArrayList<>();
        apiariesList.add(new ApiariesModel("Rucher 1", 4));
        apiariesList.add(new ApiariesModel("Rucher 2", 1));
        apiariesList.add(new ApiariesModel("Rucher 3", 2));
        apiariesList.add(new ApiariesModel("Rucher 4", 3));
        apiariesList.add(new ApiariesModel("Rucher 5", 5));
        apiariesList.add(new ApiariesModel("Rucher 6", 6));

        // we are initializing our adapter class and passing our arraylist to it.
        ApiariesAdapter apiarieAdapter = new ApiariesAdapter(this.getContext(), apiariesList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        apiariesRecyclerView.setLayoutManager(linearLayoutManager);
        apiariesRecyclerView.setAdapter(apiarieAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}