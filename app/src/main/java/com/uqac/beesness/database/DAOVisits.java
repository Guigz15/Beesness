package com.uqac.beesness.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.uqac.beesness.model.VisitModel;

import java.util.HashMap;
import java.util.Objects;

public class DAOVisits {

    private DatabaseReference dbReference;

    public DAOVisits() {
        this.dbReference = FirebaseDatabase.getInstance().getReference("Visits");
    }

    public Task<Void> add(VisitModel obj) {
        return dbReference.child(obj.getIdVisit()).setValue(obj);
    }

    public Task<Void> update(String key, HashMap<String, Object> obj) {
        return dbReference.child(key).updateChildren(obj);
    }

    public Task<Void> delete(String key) {
        return dbReference.child(key).removeValue();
    }

    public String getKey() {
        return dbReference.push().getKey();
    }

    public Query find(String id) {
        return dbReference.orderByChild("idVisit").equalTo(id);
    }

    public Query findAllForBeehive(String idBeehive) {
        return dbReference.orderByChild("idBeehive").equalTo(idBeehive);
    }

    public Query findAllForCurrentUserAndType(String type) {
        return dbReference.orderByChild("idUser_visitType").equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid() + "_" + type);
    }
}
