package com.uqac.beesness.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.uqac.beesness.model.ApiaryModel;
import com.uqac.beesness.model.BeehiveModel;

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

    public Query findAll(String idApiary) {
        return dbReference.orderByChild("idApiary").equalTo(idApiary);
    }
}
