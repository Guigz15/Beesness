package com.uqac.beesness.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uqac.beesness.MainActivity;
import com.uqac.beesness.R;
import com.uqac.beesness.model.UserModel;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private EditText lastname, forename, address, beekeeper_number;
    private String emailText;//, apiaryEnvironment, apiaryDescription, apiaryLongitude, apiaryLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Set les informations de l'utilisateur dans les champs
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users");
        if (user != null) {
            userReference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    assert userModel != null;
                    ((TextView) findViewById(R.id.mail_address)).setText(userModel.getEmail());
                    ((TextView) findViewById(R.id.name)).setText(userModel.getLastname());
                    ((TextView) findViewById(R.id.forename)).setText(userModel.getFirstname());
                    ((TextView) findViewById(R.id.address)).setText(userModel.getAddress());
                    ((TextView) findViewById(R.id.beekeeper_number)).setText(userModel.getBeekeeper_number());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

        TextView modifyAccount = findViewById(R.id.button);
        modifyAccount.setOnClickListener(v -> modify());

        TextView deleteAccount = findViewById(R.id.delete_account);
        deleteAccount.setOnClickListener(v -> {
           showDialog();
        });
    }

    private void modify() {
        lastname = findViewById(R.id.name);
        String lastnameText = lastname.getText().toString();
        forename = findViewById(R.id.forename);
        String firstnameText = forename.getText().toString();
        emailText = ((TextView) findViewById(R.id.mail_address)).getText().toString();
        address = findViewById(R.id.address);
        String addressText = address.getText().toString();
        beekeeper_number = findViewById(R.id.beekeeper_number);
        String beekeeper_numberText = beekeeper_number.getText().toString();

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Users").child(userId)
                .setValue(new UserModel(userId, lastnameText, firstnameText, addressText, beekeeper_numberText))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this, "Modification effectuée", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Erreur lors de la modification", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showDialog() {
        BottomSheetDialog deleteAccountDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View deleteAccountView = getLayoutInflater().inflate(R.layout.delete_account_confirmation, findViewById(R.id.bottom_sheet_container));

        deleteAccountView.findViewById(R.id.delete_button).setOnClickListener(v -> {

            //Recuperation de l'id de l'utilisateur
            String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
            //Suppression de l'utilisateur
            ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        FirebaseAuth.getInstance().getCurrentUser().delete();
                        Toast.makeText(ProfileActivity.this, "Votre compte a été supprimé", Toast.LENGTH_SHORT).show();
                        //Deconnexion
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(ProfileActivity.this, SubscriptionActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });




        deleteAccountView.findViewById(R.id.cancel_button).setOnClickListener(v -> {
            deleteAccountDialog.dismiss();
        });

        deleteAccountDialog.setContentView(deleteAccountView);
        deleteAccountDialog.show();
    }
}