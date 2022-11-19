package com.uqac.beesness.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.uqac.beesness.model.HoneySuperModel;

import java.util.HashMap;

public class DAOHoneySuper {

    private final DatabaseReference dbReference;

    public DAOHoneySuper() {
        this.dbReference = FirebaseDatabase.getInstance().getReference("HoneySuper");
    }

    public Task<Void> add(HoneySuperModel obj) {
        return dbReference.child(obj.getIdHoneySuper()).setValue(obj);
    }

    public Task<Void> update(String key, HashMap<String, String> obj) {
        return dbReference.child(key).setValue(obj);
    }

    public Task<Void> delete(HoneySuperModel obj) {
        return dbReference.child(obj.getIdHoneySuper()).removeValue();
    }

    public String getKey() {
        return dbReference.push().getKey();
    }

    public Query find(String id) {
        return dbReference.orderByChild("idHoneySuper").equalTo(id);
    }

    public Query findAllForBeehive(String idBeehive) {
        return dbReference.orderByChild("idBeehive").equalTo(idBeehive);
    }
}
