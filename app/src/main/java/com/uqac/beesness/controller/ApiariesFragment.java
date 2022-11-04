package com.uqac.beesness.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActionBar;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uqac.beesness.R;
import com.uqac.beesness.databinding.FragmentApiariesBinding;
import com.uqac.beesness.model.ApiariesModel;
import com.uqac.beesness.model.ApiariesViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class ApiariesFragment extends Fragment {

    private FragmentApiariesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentApiariesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // To custom the action bar
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setCustomView(R.layout.custom_action_bar_with_plus);

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