package com.uqac.beesness.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.uqac.beesness.model.ApiaryModel;
import java.util.HashMap;
import java.util.Objects;

public class DAOApiaries {
    private final DatabaseReference dbReference;

    public DAOApiaries() {
        this.dbReference = FirebaseDatabase.getInstance().getReference("Apiaries");
    }

    public Task<Void> add(ApiaryModel obj) {
        return dbReference.child(obj.getIdApiary()).setValue(obj);
    }

    public Task<Void> update(String key, HashMap<String, Object> obj) {
        return dbReference.child(key).updateChildren(obj);
    }

    public Task<Void> delete(ApiaryModel obj) {
        return dbReference.child(obj.getIdApiary()).removeValue();
    }

    public String getKey() {
        return dbReference.push().getKey();
    }

    public Query find(String id) {
        return dbReference.orderByChild("idApiary").equalTo(id);
    }

    public Query findAllForUser() {
        return dbReference.orderByChild("idUser").equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
    }
}
