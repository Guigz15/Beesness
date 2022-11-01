package com.uqac.beesness.controller;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.uqac.beesness.databinding.FragmentApiariesBinding;
import com.uqac.beesness.model.ApiariesViewModel;

public class ApiariesFragment extends Fragment {

    private FragmentApiariesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ApiariesViewModel apiariesViewModel = new ViewModelProvider(this).get(ApiariesViewModel.class);
        binding = FragmentApiariesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textApiaries;
        apiariesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}