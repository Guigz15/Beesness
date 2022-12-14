package com.uqac.beesness.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.uqac.beesness.R;
import com.uqac.beesness.database.DAOUsers;
import com.uqac.beesness.model.UserModel;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Activity for the user to subscribe to the app
 */
public class SubscriptionActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText lastname, firstname, email, password, passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        lastname = findViewById(R.id.lastname);
        firstname = findViewById(R.id.firstname);
        email = findViewById(R.id.emailField);
        password = findViewById(R.id.password);
        passwordConfirm = findViewById(R.id.password_checking);

        Button subscribeButton = findViewById(R.id.subscription_button);
        subscribeButton.setOnClickListener(v -> subscribeUser());

        TextView loginButton = findViewById(R.id.connect);
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Method to subscribe a user
     */
    private void subscribeUser() {
        String lastnameText = lastname.getText().toString();
        String firstnameText = firstname.getText().toString();
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String passwordConfirmText = passwordConfirm.getText().toString();

        if (lastnameText.isEmpty()) {
            lastname.setError("Veuillez entrer votre nom");
            lastname.requestFocus();
        } else if (firstnameText.isEmpty()) {
            firstname.setError("Veuillez entrer votre pr??nom");
            firstname.requestFocus();
        } else if (emailText.isEmpty()) {
            email.setError("Veuillez entrer une adresse email");
            email.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("Veuillez entrer une adresse email valide");
            email.requestFocus();
        } else if (passwordText.isEmpty()) {
            password.setError("Veuillez entrer un mot de passe");
            password.requestFocus();
        } else if (!passwordValid(passwordText)) {
            password.setError("Le mot de passe doit contenir au moins 8 caract??res, une majuscule, une minuscule et un chiffre");
            password.requestFocus();
            if (!passwordConfirmText.isEmpty())
                passwordConfirm.setText("");
        } else if (passwordConfirmText.isEmpty()) {
            passwordConfirm.setError("Veuillez confirmer votre mot de passe");
            passwordConfirm.requestFocus();
        } else if (!passwordText.equals(passwordConfirmText)) {
            passwordConfirm.setError("Les mots de passe ne correspondent pas");
            passwordConfirm.requestFocus();
            passwordConfirm.setText("");
        } else {
            mAuth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    UserModel user = new UserModel(Objects.requireNonNull(mAuth.getCurrentUser()).getUid(), lastnameText, firstnameText, emailText);
                    DAOUsers daoUsers = new DAOUsers();

                    daoUsers.add(user).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Objects.requireNonNull(mAuth.getCurrentUser()).sendEmailVerification();
                            Toast.makeText(this, "Veuillez v??rifier votre adresse courriel.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            password.getText().clear();
                            passwordConfirm.getText().clear();
                            Toast.makeText(this, "Inscription ??chou??e", Toast.LENGTH_LONG).show();
                            try {
                                throw Objects.requireNonNull(task.getException());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    password.getText().clear();
                    passwordConfirm.getText().clear();
                    Toast.makeText(this, "Inscription ??chou??e", Toast.LENGTH_LONG).show();
                    try {
                        throw Objects.requireNonNull(task.getException());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * Method to check if the password is valid
     * @param password to check
     * @return true if the password is valid, false otherwise
     */
    private boolean passwordValid(String password) {

        Pattern UpperCasePatten = Pattern.compile("[A-Z]");
        Pattern lowerCasePatten = Pattern.compile("[a-z]");
        Pattern digitCasePatten = Pattern.compile("[0-9]");

        boolean flag = password.length() >= 8;

        if (!UpperCasePatten.matcher(password).find()) {
            flag = false;
        }
        if (!lowerCasePatten.matcher(password).find()) {
            flag = false;
        }
        if (!digitCasePatten.matcher(password).find()) {
            flag = false;
        }

        return flag;
    }
}

