package com.uqac.beesness.database;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.uqac.beesness.MainActivity;
import com.uqac.beesness.model.ApiaryModel;
import com.uqac.beesness.model.BeehiveModel;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Objects;

public class DAOBeehives {

    private final DatabaseReference dbReference;

    public DAOBeehives() {
        this.dbReference = FirebaseDatabase.getInstance().getReference("Beehives");
    }

    public Task<Void> add(BeehiveModel obj) {
        return dbReference.child(obj.getIdBeehive()).setValue(obj);
    }

    public Task<Void> update(String key, HashMap<String, Object> obj) {
        return dbReference.child(key).updateChildren(obj);
    }

    public Task<Void> delete(BeehiveModel obj) {
        return dbReference.child(obj.getIdBeehive()).removeValue();
    }

    public String getKey() {
        return dbReference.push().getKey();
    }

    public Query find(String id) {
        return dbReference.orderByChild("idBeehive").equalTo(id);
    }

    public void addPictureFromGallery(String idBeehive, Uri selectedImageUri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference childRef = storageRef.child(selectedImageUri.getPath());

        // uploading the image
        UploadTask uploadTask = childRef.putFile(selectedImageUri);

        uploadTask.addOnSuccessListener(taskSnapshot ->
                uploadTask.continueWithTask(task ->
                        childRef.getDownloadUrl()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                dbReference.child(idBeehive).child("picturesUrl").push().setValue(downloadUri.toString());
            }
        }));
    }

    public void addPictureFromCamera(String idBeehive, Bitmap bitmap) {
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
                dbReference.child(idBeehive).child("picturesUrl").push().setValue(downloadUri.toString());
            }
        }));
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
}
