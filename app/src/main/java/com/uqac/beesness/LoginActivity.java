package com.uqac.beesness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.connection_button);
        loginButton.setOnClickListener(v -> {
           Intent intent = new Intent(this, MainActivity.class);
           startActivity(intent);
           finish();
        });

        TextView subscriptionButton = findViewById(R.id.subscribe);
        subscriptionButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SubscriptionActivity.class);
            startActivity(intent);
            finish();
        });

        TextView forgotPasswordButton = findViewById(R.id.forgot_password);
        forgotPasswordButton.setOnClickListener(v -> {
            showDialog();
        });
    }

    private void showDialog() {
        BottomSheetDialog forgotPasswordDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View forgotPasswordView = getLayoutInflater().inflate(R.layout.forgot_password_dialog, findViewById(R.id.bottom_sheet_container));

        forgotPasswordView.findViewById(R.id.send_link_button).setOnClickListener(v -> {
            Toast.makeText(this, "Un lien de réinitialisation de mot de passe a été envoyé à votre adresse courriel.", Toast.LENGTH_LONG).show();
            forgotPasswordDialog.dismiss();
        });

        forgotPasswordDialog.setContentView(forgotPasswordView);
        forgotPasswordDialog.show();
    }
}
