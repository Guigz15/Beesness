package com.uqac.beesness.controller;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.uqac.beesness.R;

import com.uqac.beesness.database.DAOProducts;

import com.uqac.beesness.model.ProductModel;

public class ProductDetailsActivity extends AppCompatActivity {

    private String idProduct;
    private TextView productNameTextView;
    private ImageButton infoButton, editButton, backButton;
    private DAOProducts daoProducts;
    private BottomSheetDialog deleteProductDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productNameTextView = findViewById(R.id.title_text);

        idProduct = getIntent().getStringExtra("idProduct");
        daoProducts = new DAOProducts();
        daoProducts.find(idProduct).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductModel product = dataSnapshot.getValue(ProductModel.class);
                    assert product != null;
                    productNameTextView.setText(product.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        /*infoButton = findViewById(R.id.informations);
        infoButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            ApiaryDetailsMainFragment apiaryDetailsMainFragment = (ApiaryDetailsMainFragment) fragmentManager.findFragmentByTag("main");
            if (apiaryDetailsMainFragment != null && apiaryDetailsMainFragment.isVisible()) {
                infoButton.setImageResource(R.drawable.ic_baseline_info_48);
                fragmentTransaction.replace(R.id.fragmentContainerView, new ApiaryDetailsInfoFragment(), "info");
            } else {
                infoButton.setImageResource(R.drawable.ic_outline_info_48);
                fragmentTransaction.replace(R.id.fragmentContainerView, new ApiaryDetailsMainFragment(), "main");
            }
            fragmentTransaction.commit();
        });*/

        ImageButton deleteProductButton = findViewById(R.id.delete_button);
        deleteProductButton.setOnClickListener(v -> showDialog());

        editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddUpdateProductActivity.class);
            intent.putExtra("idProduct", idProduct);
            startActivity(intent);
        });

        backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (deleteProductDialog != null) {
            deleteProductDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (deleteProductDialog != null) {
            deleteProductDialog.show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (deleteProductDialog != null) {
            deleteProductDialog.dismiss();
        }
    }

    private void showDialog() {
        System.out.println("====================================");
        deleteProductDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View deleteProductView = getLayoutInflater().inflate(R.layout.delete_product_confirmation, findViewById(R.id.bottom_sheet_container));
        System.out.println("idProduct : " + idProduct);
        deleteProductView.findViewById(R.id.delete_button).setOnClickListener(v -> {
            daoProducts.delete(idProduct).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ProductDetailsActivity.this, "Le produit a été supprimé", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ProductDetailsActivity.this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                }
            });
        });

        deleteProductView.findViewById(R.id.cancel_button).setOnClickListener(v -> {
            deleteProductDialog.dismiss();
        });

        deleteProductDialog.setContentView(deleteProductView);
        deleteProductDialog.show();
    }

    public String getIdProduct() {
        return idProduct;
    }

    public String getProductName() {
        return productNameTextView.getText().toString();
    }
}
