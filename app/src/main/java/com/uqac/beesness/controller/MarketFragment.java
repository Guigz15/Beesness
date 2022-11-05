package com.uqac.beesness.controller;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.uqac.beesness.R;
import com.uqac.beesness.databinding.FragmentMarketBinding;
import com.uqac.beesness.model.MarketViewModel;

public class MarketFragment extends Fragment {

    private FragmentMarketBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MarketViewModel marketViewModel = new ViewModelProvider(this).get(MarketViewModel.class);
        binding = FragmentMarketBinding.inflate(inflater, container, false);

        requireActivity().findViewById(R.id.toolbar_add).setVisibility(View.VISIBLE);

        View root = binding.getRoot();

        final TextView textView = binding.textMarket;
        marketViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}