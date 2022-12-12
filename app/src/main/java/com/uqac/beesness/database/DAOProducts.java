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

/**
 * DAO to handle ProductModel objects in the database
 */
public class DAOProducts {

    private final DatabaseReference dbReference;

    public DAOProducts() {
        this.dbReference = FirebaseDatabase.getInstance().getReference("Products");
    }

    /**
     * Add a ProductModel object to the database
     * @param obj ProductModel object to add
     * @return Task object
     */
    public Task<Void> add(ProductModel obj) {
        return dbReference.child(obj.getIdProduct()).setValue(obj);
    }

    /**
     * Update a ProductModel object in the database
     * @param key Key of the ProductModel object to update
     * @param obj ProductModel objects to update
     * @return Task object
     */
    public Task<Void> update(String key, HashMap<String, Object> obj) {
        return dbReference.child(key).updateChildren(obj);
    }

    /**
     * Delete a ProductModel object from the database
     * @param key Key of the ProductModel object to delete
     * @return Task object
     */
    public Task<Void> delete(String key) {
        return dbReference.child(key).removeValue();
    }

    /**
     * Generate a new key for a ProductModel object
     * @return New key
     */
    public String getKey() {
        return dbReference.push().getKey();
    }

    /**
     * Find a ProductModel object in the database
     * @param id Id of the ProductModel object to find
     * @return Query object
     */
    public Query find(String id) {
        return dbReference.orderByChild("idProduct").equalTo(id);
    }

    /**
     * Find all ProductModel objects in the database for the current user
     * @return Query object
     */
    public Query findAllForUser() {
        return dbReference.orderByChild("idUser").equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
    }

    /**
     * Upload a bitmap to the Firebase Storage and the database
     * @param idProduct Id of the ProductModel object
     * @param bitmap Bitmap to upload
     */
    public void addPicture(String idProduct, Bitmap bitmap) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference childRef = storageRef.child(bitmap.toString());

        // Uploading the image
        UploadTask uploadTask = childRef.putBytes(bitmapToByteArray(bitmap));

        uploadTask.addOnSuccessListener(taskSnapshot -> uploadTask.continueWithTask(task ->
            childRef.getDownloadUrl()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    dbReference.child(idProduct).child("pictureUrl").setValue(downloadUri.toString());
                }
            }
        ));
    }

    /**
     * Convert a bitmap to a byte array
     * @param bitmap Bitmap to convert
     * @return Byte array
     */
    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
}
