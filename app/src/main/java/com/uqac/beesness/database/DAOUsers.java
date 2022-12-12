package com.uqac.beesness.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uqac.beesness.model.UserModel;
import java.util.HashMap;

/**
 * DAO to handle the users in the database
 */
public class DAOUsers {

    private final DatabaseReference dbReference;

    public DAOUsers() {
        this.dbReference = FirebaseDatabase.getInstance().getReference("Users");
    }

    /**
     * Add a user to the database
     * @param obj the user to add
     * @return Task object
     */
    public Task<Void> add(UserModel obj) {
        return dbReference.child(obj.getIdUser()).setValue(obj);
    }

    /**
     * Update a user in the database
     * @param key the key of the user to update
     * @param obj the user to update
     * @return Task object
     */
    public Task<Void> update(String key, HashMap<String, Object> obj) {
        return dbReference.child(key).updateChildren(obj);
    }

    /**
     * Delete a user from the database and all its children
     * @param key the key of the user to delete
     * @return Task object
     */
    public Task<Void> delete(String key) {
        DAOApiaries daoApiaries = new DAOApiaries();
        daoApiaries.findAllForUser().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    daoApiaries.delete(snapshot.getKey());
                }
            }
        });

        return dbReference.child(key).removeValue();
    }

    /**
     * Get a user from the database
     * @param id the id of the user to get
     * @return Task object
     */
    public UserModel find(String id) {
        return dbReference.child(id).get().getResult().getValue(UserModel.class);
    }
}
