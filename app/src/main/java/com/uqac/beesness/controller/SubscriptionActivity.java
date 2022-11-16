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
import com.google.firebase.database.FirebaseDatabase;
import com.uqac.beesness.R;
import com.uqac.beesness.database.DAOUsers;
import com.uqac.beesness.model.UserModel;

import java.util.Objects;
import java.util.regex.Pattern;

public class SubscriptionActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText lastname, firstname, email, password, passwordConfirm;
    private Button subscribeButton;

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

        subscribeButton = findViewById(R.id.subscription_button);
        subscribeButton.setOnClickListener(v -> subscribeUser());

        TextView loginButton = findViewById(R.id.connect);
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void subscribeUser() {
        String lastnameText = lastname.getText().toString();
        String firstnameText = firstname.getText().toString();
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String passwordConfirmText = passwordConfirm.getText().toString();

        if (lastnameText.isEmpty()) {
            lastname.setError("Veuillez entrer votre nom");
            lastname.requestFocus();
        }

        if (firstnameText.isEmpty()) {
            firstname.setError("Veuillez entrer votre prénom");
            firstname.requestFocus();
        }

        if (emailText.isEmpty()) {
            email.setError("Veuillez entrer une adresse email");
            email.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("Veuillez entrer une adresse email valide");
            email.requestFocus();
        }

        if (passwordText.isEmpty()) {
            password.setError("Veuillez entrer un mot de passe");
            password.requestFocus();
        }

        if (!passwordValid(passwordText)) {
            password.setError("Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule et un chiffre");
            password.requestFocus();
        }

        if (passwordConfirmText.isEmpty()) {
            passwordConfirm.setError("Veuillez confirmer votre mot de passe");
            passwordConfirm.requestFocus();
        }

        if (!passwordText.equals(passwordConfirmText)) {
            passwordConfirm.setError("Les mots de passe ne correspondent pas");
            passwordConfirm.requestFocus();
        }

        if (!lastnameText.isEmpty() && !firstnameText.isEmpty() && !emailText.isEmpty() && !passwordText.isEmpty() && !passwordConfirmText.isEmpty() && passwordText.equals(passwordConfirmText)) {
            mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            UserModel user = new UserModel(Objects.requireNonNull(mAuth.getCurrentUser()).getUid(), lastnameText, firstnameText, emailText);
                            DAOUsers daoUsers = new DAOUsers();

                            daoUsers.add(user).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Objects.requireNonNull(mAuth.getCurrentUser()).sendEmailVerification();
                                    Toast.makeText(this, "Veuillez vérifier votre adresse courriel.", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    password.getText().clear();
                                    passwordConfirm.getText().clear();
                                    Toast.makeText(this, "Inscription échouée", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(this, "Inscription échouée", Toast.LENGTH_LONG).show();
                            try {
                                throw Objects.requireNonNull(task.getException());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    private boolean passwordValid(String password) {

        Pattern UpperCasePatten = Pattern.compile("[A-Z]");
        Pattern lowerCasePatten = Pattern.compile("[a-z]");
        Pattern digitCasePatten = Pattern.compile("[0-9]");

        boolean flag = true;

        if (password.length() < 8) {
            flag = false;
        }
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

