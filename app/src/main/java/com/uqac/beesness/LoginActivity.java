package com.uqac.beesness;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        final Dialog forgotPasswordDialog = new Dialog(this);
        forgotPasswordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        forgotPasswordDialog.setContentView(R.layout.forgot_password_dialog);

        Button sendButton = forgotPasswordDialog.findViewById(R.id.send_link_button);
        sendButton.setOnClickListener(v -> {
            forgotPasswordDialog.dismiss();
            Toast.makeText(this, "Un lien de réinitialisation de mot de passe a été envoyé à votre adresse courriel.", Toast.LENGTH_LONG).show();
        });

        forgotPasswordDialog.show();
        forgotPasswordDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        forgotPasswordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        forgotPasswordDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        forgotPasswordDialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}
