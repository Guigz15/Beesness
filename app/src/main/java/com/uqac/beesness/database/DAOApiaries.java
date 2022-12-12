package com.uqac.beesness.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.uqac.beesness.model.ApiaryModel;
import java.util.HashMap;
import java.util.Objects;

/**
 * DAO to handle ApiaryModel objects in the database
 */
public class DAOApiaries {

    private final DatabaseReference dbReference;

    public DAOApiaries() {
        this.dbReference = FirebaseDatabase.getInstance().getReference("Apiaries");
    }

    /**
     * Add an ApiaryModel object to the database
     * @param obj ApiaryModel object to add
     * @return Task object
     */
    public Task<Void> add(ApiaryModel obj) {
        return dbReference.child(obj.getIdApiary()).setValue(obj);
    }

    /**
     * Update an ApiaryModel object in the database
     * @param key Key of the ApiaryModel object to update
     * @param obj ApiaryModel objects to update
     * @return Task object
     */
    public Task<Void> update(String key, HashMap<String, Object> obj) {
        return dbReference.child(key).updateChildren(obj);
    }

    /**
     * Delete an ApiaryModel object from the database and all its children
     * @param key Key of the ApiaryModel object to delete
     * @return Task object
     */
    public Task<Void> delete(String key) {
        DAOBeehives daoBeehives = new DAOBeehives();
        daoBeehives.findAllForApiary(key).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    daoBeehives.delete(snapshot.getKey());
                }
            }
        });

        return dbReference.child(key).removeValue();
    }

    /**
     * Generate a new key for an ApiaryModel object
     * @return New key
     */
    public String getKey() {
        return dbReference.push().getKey();
    }

    /**
     * Find an ApiaryModel object in the database
     * @param id Key of the ApiaryModel object to find
     * @return Query object
     */
    public Query find(String id) {
        return dbReference.orderByChild("idApiary").equalTo(id);
    }

    /**
     * Find all ApiaryModel objects in the database for the current user
     * @return Query object
     */
    public Query findAllForUser() {
        return dbReference.orderByChild("idUser").equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
    }
}
