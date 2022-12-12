package com.uqac.beesness.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.uqac.beesness.model.HoneySuperModel;
import java.util.HashMap;
import java.util.Objects;

/**
 * DAO to handle HoneySuperModel objects
 */
public class DAOHoneySuper {

    private final DatabaseReference dbReference;

    public DAOHoneySuper() {
        this.dbReference = FirebaseDatabase.getInstance().getReference("HoneySuper");
    }

    /**
     * Add a HoneySuperModel object to the database
     * @param obj HoneySuperModel object to add
     * @return Task object
     */
    public Task<Void> add(HoneySuperModel obj) {
        return dbReference.child(obj.getIdHoneySuper()).setValue(obj);
    }

    /**
     * Update a HoneySuperModel object in the database
     * @param key Key of the HoneySuperModel object to update
     * @param obj HoneySuperModel objects to update
     * @return Task object
     */
    public Task<Void> update(String key, HashMap<String, Object> obj) {
        return dbReference.child(key).updateChildren(obj);
    }

    /**
     * Delete a HoneySuperModel object from the database
     * @param key Key of the HoneySuperModel object to delete
     * @return Task object
     */
    public Task<Void> delete(String key) {
        return dbReference.child(key).removeValue();
    }

    /**
     * Generate a new key for a HoneySuperModel object
     * @return New key
     */
    public String getKey() {
        return dbReference.push().getKey();
    }

    /**
     * Find a HoneySuperModel object in the database
     * @param id Id of the HoneySuperModel object to find
     * @return Query object
     */
    public Query find(String id) {
        return dbReference.orderByChild("idHoneySuper").equalTo(id);
    }

    /**
     * Find all HoneySuperModel objects in the database for a specific beehive
     * @param idBeehive Id of the beehive
     * @return Query object
     */
    public Query findAllForBeehive(String idBeehive) {
        return dbReference.orderByChild("idBeehive").equalTo(idBeehive);
    }

    /**
     * Find all HoneySuperModel objects in the database for the current user
     * @return Query object
     */
    public Query findAllForUser() {
        return dbReference.orderByChild("idUser").equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
    }
}
