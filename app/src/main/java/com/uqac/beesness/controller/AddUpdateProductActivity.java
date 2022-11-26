package com.uqac.beesness.controller;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.uqac.beesness.R;

import com.uqac.beesness.database.DAOProducts;
import com.uqac.beesness.model.ProductModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

public class AddUpdateProductActivity extends AppCompatActivity {
    private EditText productName, productQuantity;
    private ImageButton addPictureButton;
    private ImageView productImage;
    private DAOProducts daoProducts;
    private String idProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        productName = findViewById(R.id.productName);
        productQuantity = findViewById(R.id.productQuantity);
        productImage = findViewById(R.id.productImage);

        daoProducts = new DAOProducts();

        addPictureButton = findViewById(R.id.add_pictures);
        addPictureButton.setOnClickListener(v -> selectImage(this));

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> saveProduct());
    }

    private void saveProduct() {
        String nameText = productName.getText().toString();
        String quantityText = productQuantity.getText().toString();
        String image = "";

        if (nameText.isEmpty()) {
            productName.setError("Veuillez entrer un nom de produit");
            productName.requestFocus();
        }


           // Uri uri = productImage.getDrawable().getConstantState().getResources().getUri();
            //daoProducts.addPictureFromGallery(idProduct, uri);

/*
        if (quantityText.isEmpty()) {
            productQuantity.setError("Veuillez entrer une quantité");
            productQuantity.requestFocus();
        }*/
        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        if (!nameText.isEmpty()) {
            daoProducts = new DAOProducts();

            if (getIntent().getStringExtra("idProduct") == null) {
                idProduct = daoProducts.getKey();
                assert idProduct != null;
                ProductModel product = new ProductModel(idProduct, nameText, Integer.parseInt(quantityText), userId);
                daoProducts.add(product).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if((productImage.getDrawable()) instanceof BitmapDrawable) {
                            Bitmap bitmap = ((BitmapDrawable) productImage.getDrawable()).getBitmap();
                            daoProducts.addPictureFromCamera(idProduct, bitmap);

                        }
                        Toast.makeText(this, "Produit ajouté", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Erreur lors de l'ajout du produit", Toast.LENGTH_LONG).show();
                    }
                });
            } else {

            }
        }
    }

    private void selectImage(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 100);

        final CharSequence[] options = { "Prendre une photo", "Choisir depuis la galerie" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ajouter une photo");

        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Prendre une photo")) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                launcher.launch(takePicture);
            } else if (options[item].equals("Choisir depuis la galerie")) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                launcher.launch(intent);
            }
        });

        builder.show();

    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        if (data.getData() == null) {
                            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                            productImage.setImageBitmap(bitmap);

                        } else {
                            Uri selectedImageUri = data.getData();
                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(selectedImageUri);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            //pictureUrl = selectedImageUri.toString();
                            productImage.setImageBitmap(selectedImage);
                            //Glide.with(productImage.getContext()).load(pictureUrl).into(productImage);
                        }
                    }
                }
            }
    );
}