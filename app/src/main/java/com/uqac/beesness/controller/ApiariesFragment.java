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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uqac.beesness.R;
import com.uqac.beesness.databinding.FragmentApiariesBinding;
import com.uqac.beesness.model.ApiaryModel;

import java.util.ArrayList;

public class ApiariesFragment extends Fragment {

    private FragmentApiariesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        RecyclerView apiariesRecyclerView = root.findViewById(R.id.apiaries_list);

        ArrayList<ApiaryModel> apiariesList = new ArrayList<>();
        apiariesList.add(new ApiaryModel("Rucher 1", 4));
        apiariesList.add(new ApiaryModel("Rucher 2", 1));
        apiariesList.add(new ApiaryModel("Rucher 3", 2));
        apiariesList.add(new ApiaryModel("Rucher 4", 3));
        apiariesList.add(new ApiaryModel("Rucher 5", 5));
        apiariesList.add(new ApiaryModel("Rucher 6", 6));

        ApiariesAdapter apiariesAdapter = new ApiariesAdapter(this.getContext(), apiariesList, listener -> {
            Intent intent = new Intent(getActivity(), ApiaryDetailsActivity.class);
            intent.putExtra("apiary", listener.getName());
            startActivity(intent);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);

        apiariesRecyclerView.setLayoutManager(linearLayoutManager);
        apiariesRecyclerView.setAdapter(apiariesAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}