package com.uqac.beesness.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.uqac.beesness.model.VisitModel;
import java.util.HashMap;
import java.util.Objects;

/**
 * DAO to handle VisitModel objects in the database
 */
public class DAOVisits {

    private final DatabaseReference dbReference;

    public DAOVisits() {
        this.dbReference = FirebaseDatabase.getInstance().getReference("Visits");
    }

    /**
     * Add a visitModel object to the database
     * @param obj the object to add
     * @return Task object
     */
    public Task<Void> add(VisitModel obj) {
        return dbReference.child(obj.getIdVisit()).setValue(obj);
    }

    /**
     * Update a visitModel object in the database
     * @param key the key of the object to update
     * @param obj the objects to update
     * @return Task object
     */
    public Task<Void> update(String key, HashMap<String, Object> obj) {
        return dbReference.child(key).updateChildren(obj);
    }

    /**
     * Delete a visitModel object from the database
     * @param key the key of the object to delete
     * @return Task object
     */
    public Task<Void> delete(String key) {
        return dbReference.child(key).removeValue();
    }

    /**
     * Generate a new key for a visitModel object
     * @return the new key
     */
    public String getKey() {
        return dbReference.push().getKey();
    }

    /**
     * Find a visitModel object by its key
     * @param id the key of the object to find
     * @return Query object
     */
    public Query find(String id) {
        return dbReference.orderByChild("idVisit").equalTo(id);
    }

    /**
     * Find all visitModel objects for a specific beehive
     * @param idBeehive the id of the beehive
     * @return Query object
     */
    public Query findAllForBeehive(String idBeehive) {
        return dbReference.orderByChild("idBeehive").equalTo(idBeehive);
    }

    /**
     * Find all visitModel objects for the current user and a specific visit type
     * @param type the type of the visit
     * @return Query object
     */
    public Query findAllForCurrentUserAndType(String type) {
        return dbReference.orderByChild("idUser_visitType").equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid() + "_" + type);
    }
}
