package com.uqac.beesness.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.uqac.beesness.model.ProductModel;
import java.util.HashMap;
import java.util.Objects;

public class DAOProducts {
    private final DatabaseReference dbReference;

    public DAOProducts() {
        this.dbReference = FirebaseDatabase.getInstance().getReference("Products");
    }

    public Task<Void> add(ProductModel obj) {
        return dbReference.child(obj.getIdProduct()).setValue(obj);
    }

    public Task<Void> update(String key, HashMap<String, Object> obj) {
        return dbReference.child(key).updateChildren(obj);
    }

    public Task<Void> delete(ProductModel obj) {
        return dbReference.child(obj.getIdProduct()).removeValue();
    }

    public String getKey() {
        return dbReference.push().getKey();
    }

    public Query find(String id) {
        return dbReference.orderByChild("idProduct").equalTo(id);
    }

    public Query findAllForUser() {
        return dbReference.orderByChild("idUser").equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
    }
}
