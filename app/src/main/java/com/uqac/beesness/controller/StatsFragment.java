package com.uqac.beesness.controller;

import androidx.lifecycle.ViewModelProvider;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.uqac.beesness.R;
import com.uqac.beesness.databinding.FragmentStatsBinding;
import com.uqac.beesness.model.StatsViewModel;

/**
 * Fragment to handle the statistics tab
 */
public class StatsFragment extends Fragment {

    private FragmentStatsBinding binding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StatsViewModel statsViewModel = new ViewModelProvider(this).get(StatsViewModel.class);

        binding = FragmentStatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        requireActivity().findViewById(R.id.toolbar_add).setVisibility(View.GONE);
        requireActivity().findViewById(R.id.space).setVisibility(View.GONE);
        ((TextView) requireActivity().findViewById(R.id.title_text)).setText("Statistiques");

        final TextView textView = binding.textStats;
        statsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}