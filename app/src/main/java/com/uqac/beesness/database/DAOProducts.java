package com.uqac.beesness.database;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.uqac.beesness.model.ProductModel;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Objects;

public class DAOProducts {
    private final DatabaseReference dbReference;

    public DAOProducts() {
        this.dbReference = FirebaseDatabase.getInstance().getReference("Products");
    }

    public Task<Void> add(ProductModel obj) {
        return dbReference.child(obj.getIdProduct()).setValue(obj);
    }

    public Task<Void> update(String key, HashMap<String, Object> obj) {
        return dbReference.child(key).updateChildren(obj);
    }

    public Task<Void> delete(String key) {
        return dbReference.child(key).removeValue();
    }

    public String getKey() {
        return dbReference.push().getKey();
    }

    public Query find(String id) {
        return dbReference.orderByChild("idProduct").equalTo(id);
    }

    public Query findAllForUser() {
        return dbReference.orderByChild("idUser").equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
    }

    public void addPictureFromCamera(String idProduct, Bitmap bitmap) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference childRef = storageRef.child(bitmap.toString());

        // uploading the image
        UploadTask uploadTask = childRef.putBytes(bitmapToByteArray(bitmap));

        uploadTask.addOnSuccessListener(taskSnapshot ->
                uploadTask.continueWithTask(task ->
                        childRef.getDownloadUrl()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        dbReference.child(idProduct).child("pictureUrl").setValue(downloadUri.toString());
                    }
                }));
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
}
