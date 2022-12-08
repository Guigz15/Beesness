package com.uqac.beesness.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.uqac.beesness.MainActivity;
import com.uqac.beesness.R;
import com.uqac.beesness.databinding.FragmentPlusBinding;

public class PlusFragment extends Fragment {

    private FragmentPlusBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlusBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ((TextView) requireActivity().findViewById(R.id.title_text)).setText("Plus");

        binding.accountCardView.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), ProfileActivity.class);
            intent.putExtra("transition", "plus");
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        binding.logoutCardView.setOnClickListener(v -> showDialog());

        binding.supportCardView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            String[] to = {"contact@beesness.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, to);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Demande d'assistance Beesness");
            startActivity(intent);
        });

        return root;
    }

    private void showDialog() {
        BottomSheetDialog logoutDialog = new BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme);
        View logoutView = getLayoutInflater().inflate(R.layout.user_deconnection, requireActivity().findViewById(R.id.bottom_sheet_container));

        logoutView.findViewById(R.id.disconnect_button).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
        });

        logoutView.findViewById(R.id.cancel_button).setOnClickListener(v -> logoutDialog.dismiss());

        logoutDialog.setContentView(logoutView);
        logoutDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}