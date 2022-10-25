package com.uqac.beesness.ui.plus;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uqac.beesness.R;
import com.uqac.beesness.databinding.FragmentHomeBinding;
import com.uqac.beesness.databinding.FragmentPlusBinding;
import com.uqac.beesness.ui.home.HomeViewModel;

public class PlusFragment extends Fragment {

    private FragmentPlusBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PlusViewModel plusViewModel = new ViewModelProvider(this).get(PlusViewModel.class);

        binding = FragmentPlusBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPlus;
        plusViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}