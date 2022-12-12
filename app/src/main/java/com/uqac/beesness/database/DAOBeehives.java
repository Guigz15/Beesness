package com.uqac.beesness.database;

import android.graphics.Bitmap;
import android.net.Uri;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.uqac.beesness.model.BeehiveModel;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Objects;

/**
 * DAO to handle BeehiveModel objects in the database
 */
public class DAOBeehives {

    private final DatabaseReference dbReference;

    public DAOBeehives() {
        this.dbReference = FirebaseDatabase.getInstance().getReference("Beehives");
    }

    /**
     * Add a BeehiveModel object to the database
     * @param obj BeehiveModel object to add
     * @return Task object
     */
    public Task<Void> add(BeehiveModel obj) {
        return dbReference.child(obj.getIdBeehive()).setValue(obj);
    }

    /**
     * Update a BeehiveModel object in the database
     * @param key Key of the BeehiveModel object to update
     * @param obj BeehiveModel objects to update
     * @return Task object
     */
    public Task<Void> update(String key, HashMap<String, Object> obj) {
        return dbReference.child(key).updateChildren(obj);
    }

    /**
     * Delete a BeehiveModel object from the database and all its children
     * @param key Key of the BeehiveModel object to delete
     * @return Task object
     */
    public Task<Void> delete(String key) {
        DAOHoneySuper daoHoneySuper = new DAOHoneySuper();
        daoHoneySuper.findAllForBeehive(key).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    daoHoneySuper.delete(snapshot.getKey());
                }
            }
        });

        DAOVisits daoVisits = new DAOVisits();
        daoVisits.findAllForBeehive(key).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    daoVisits.delete(snapshot.getKey());
                }
            }
        });

        dbReference.child(key).child("picturesUrl").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    FirebaseStorage.getInstance().getReferenceFromUrl(Objects.requireNonNull(snapshot.getValue()).toString()).delete();
                }
            }
        });

        return dbReference.child(key).removeValue();
    }

    /**
     * Generate a new key for a BeehiveModel object
     * @return New key
     */
    public String getKey() {
        return dbReference.push().getKey();
    }

    /**
     * Find a BeehiveModel object in the database
     * @param id id of the BeehiveModel object to find
     * @return Task object
     */
    public Query find(String id) {
        return dbReference.orderByChild("idBeehive").equalTo(id);
    }

    /**
     * Find all BeehiveModel objects in the database for a specific apiary
     * @param idApiary id of the apiary
     * @return Query object
     */
    public Query findAllForApiary(String idApiary) {
        return dbReference.orderByChild("idApiary").equalTo(idApiary);
    }

    /**
     * Find all BeehiveModel objects in the database for the current user
     * @return Query object
     */
    public Query findAllForUser() {
        return dbReference.orderByChild("idUser").equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
    }

    /**
     * Upload a picture to the Firebase Storage and the database
     * @param idBeehive id of the beehive
     * @param bitmap Bitmap of the picture
     */
    public void addPicture(String idBeehive, Bitmap bitmap) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference childRef = storageRef.child(bitmap.toString());

        // Uploading the image
        UploadTask uploadTask = childRef.putBytes(bitmapToByteArray(bitmap));

        uploadTask.addOnSuccessListener(taskSnapshot -> uploadTask.continueWithTask(task ->
            childRef.getDownloadUrl()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    dbReference.child(idBeehive).child("picturesUrl").push().setValue(downloadUri.toString());
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
