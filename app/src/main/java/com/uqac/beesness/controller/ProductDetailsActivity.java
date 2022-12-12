package com.uqac.beesness.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.uqac.beesness.R;
import com.uqac.beesness.database.DAOProducts;
import com.uqac.beesness.model.ProductModel;

/**
 * Activity for the product details
 */
public class ProductDetailsActivity extends AppCompatActivity {

    private String idProduct;
    private TextView productNameTextView;
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
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ImageButton deleteProductButton = findViewById(R.id.delete_button);
        deleteProductButton.setOnClickListener(v -> showDialog());

        ImageButton editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddUpdateProductActivity.class);
            intent.putExtra("idProduct", idProduct);
            startActivity(intent);
        });

        ImageButton backButton = findViewById(R.id.back);
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

    /**
     * Show the dialog to confirm the deletion of the product
     */
    private void showDialog() {
        deleteProductDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View deleteProductView = getLayoutInflater().inflate(R.layout.delete_product_confirmation, findViewById(R.id.bottom_sheet_container));
        deleteProductView.findViewById(R.id.delete_button).setOnClickListener(v ->
            daoProducts.delete(idProduct).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ProductDetailsActivity.this, "Le produit a été supprimé", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ProductDetailsActivity.this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                }
            }
        ));

        deleteProductView.findViewById(R.id.cancel_button).setOnClickListener(v -> deleteProductDialog.dismiss());

        deleteProductDialog.setContentView(deleteProductView);
        deleteProductDialog.show();
    }
}
