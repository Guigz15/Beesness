package com.uqac.beesness.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uqac.beesness.MainActivity;
import com.uqac.beesness.R;

/**
 * Activity for the login page
 */
public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailForLogin);
        password = findViewById(R.id.password);

        Button loginButton = findViewById(R.id.connection_button);
        loginButton.setOnClickListener(v -> userLogin());

        TextView subscriptionButton = findViewById(R.id.subscribe);
        subscriptionButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SubscriptionActivity.class);
            startActivity(intent);
            finish();
        });

        TextView forgotPasswordButton = findViewById(R.id.forgot_password);
        forgotPasswordButton.setOnClickListener(v -> showDialog());
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().isEmailVerified()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Method to show the dialog to reset the password
     */
    private void showDialog() {
        BottomSheetDialog forgotPasswordDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View forgotPasswordView = getLayoutInflater().inflate(R.layout.forgot_password_dialog, findViewById(R.id.bottom_sheet_container));

        forgotPasswordView.findViewById(R.id.send_link_button).setOnClickListener(v -> {
            resetPassord(forgotPasswordView);
            forgotPasswordDialog.dismiss();
        });

        forgotPasswordDialog.setContentView(forgotPasswordView);
        forgotPasswordDialog.show();
    }

    /**
     * Method to reset the password
     * @param view forgot password view
     */
    private void resetPassord(View view) {
        EditText email = view.findViewById(R.id.email_for_reset);
        String emailText = email.getText().toString();

        if (emailText.isEmpty()) {
            email.setError("Veuillez entrer une adresse email");
            email.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("Veuillez entrer une adresse email valide");
            email.requestFocus();
        }

        if(!emailText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            mAuth.sendPasswordResetEmail(emailText).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Un lien de réinitialisation de mot de passe a été envoyé à votre adresse courriel.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Une erreur est survenue lors de l'envoi du lien de réinitialisation de mot de passe.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * Method to login the user
     */
    private void userLogin() {
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        if (emailText.isEmpty()) {
            email.setError("Veuillez entrer votre adresse courriel.");
            email.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("Veuillez entrer une adresse email valide");
            email.requestFocus();
        }

        if (passwordText.isEmpty()) {
            password.setError("Veuillez entrer votre mot de passe.");
            password.requestFocus();
        }

        if(!emailText.isEmpty() && !passwordText.isEmpty()) {
            mAuth.signInWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    assert user != null;
                    if (user.isEmailVerified()) {
                        Toast.makeText(this, "Connexion réussie", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        password.getText().clear();
                        Toast.makeText(this, "Veuillez vérifier votre adresse courriel.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    password.getText().clear();
                    Toast.makeText(this, "Courriel ou mot de passe erroné", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
