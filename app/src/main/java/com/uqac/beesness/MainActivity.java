package com.uqac.beesness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;

import com.uqac.beesness.controller.LoginActivity;
import com.uqac.beesness.controller.ProfileActivity;
import com.uqac.beesness.controller.QrCodeScannerActivity;

import com.uqac.beesness.databinding.ActivityMainBinding;



public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // To define qrCode button behavior
        ImageButton qrCodeButton = findViewById(R.id.toolbar_qr_code_scanner);
        qrCodeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QrCodeScannerActivity.class);
            startActivity(intent);
        });

        // To define menu items behavior
        ImageButton menuButton = findViewById(R.id.toolbar_more_vert);
        menuButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, menuButton);
            popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());
            popupMenu.setForceShowIcon(true);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getTitle().equals("Mon compte")) {
                    Intent intent = new Intent(this, ProfileActivity.class);
                    startActivity(intent);
                } else if (menuItem.getTitle().equals("Déconnexion")) {
                    showDialog();
                }
                return true;
            });
            popupMenu.show();
        });

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void showDialog() {
        BottomSheetDialog deleteAccountDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View deleteAccountView = getLayoutInflater().inflate(R.layout.user_deconnection, findViewById(R.id.bottom_sheet_container));

        deleteAccountView.findViewById(R.id.disconnect_button).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });




        deleteAccountView.findViewById(R.id.cancel_button).setOnClickListener(v -> {
            deleteAccountDialog.dismiss();
        });

        deleteAccountDialog.setContentView(deleteAccountView);
        deleteAccountDialog.show();
    }
}