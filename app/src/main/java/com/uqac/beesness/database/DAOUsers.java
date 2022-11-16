package com.uqac.beesness.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uqac.beesness.model.UserModel;

import java.util.HashMap;

public class DAOUsers {
    private final DatabaseReference dbReference;

    public DAOUsers() {
        this.dbReference = FirebaseDatabase.getInstance().getReference("Users");
    }

    public Task<Void> add(UserModel obj) {
        return dbReference.child(obj.getIdUser()).setValue(obj);
    }

    public Task<Void> update(String key, HashMap<String, Object> obj) {
        return dbReference.child(key).updateChildren(obj);
    }

    public Task<Void> delete(String key) {
        return dbReference.child(key).removeValue();
    }

    public UserModel find(String id) {
        return dbReference.child(id).get().getResult().getValue(UserModel.class);
    }
}
