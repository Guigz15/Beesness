package com.uqac.beesness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;

import com.uqac.beesness.controller.LoginActivity;
import com.uqac.beesness.controller.ProfileActivity;

import com.uqac.beesness.databinding.ActivityMainBinding;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // To define qrCode button behavior
        ImageButton qrCodeButton = findViewById(R.id.toolbar_qr_code_scanner);
        qrCodeButton.setOnClickListener(v -> Toast.makeText(this, "En développement", Toast.LENGTH_SHORT).show());

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
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void showDialog() {
        BottomSheetDialog logoutDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View logoutView = getLayoutInflater().inflate(R.layout.user_deconnection, findViewById(R.id.bottom_sheet_container));

        logoutView.findViewById(R.id.disconnect_button).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        logoutView.findViewById(R.id.cancel_button).setOnClickListener(v -> logoutDialog.dismiss());

        logoutDialog.setContentView(logoutView);
        logoutDialog.show();
    }
}