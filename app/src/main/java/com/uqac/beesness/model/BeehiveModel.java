package com.uqac.beesness.model;

import java.util.ArrayList;
import java.util.List;

public class BeehiveModel {

    private String type;
    private String details;
    //private String qrCode;
    private String idApiary;
    private List<HoneySuperModel> honeySupers;
    private List<VisitModel> visits;

    public BeehiveModel() {
        this.type = "";
        this.details = "";
        this.idApiary = "";
        this.honeySupers = new ArrayList<>();
        this.visits = new ArrayList<>();
    }

    public BeehiveModel(String type, String details, String idApiary) {
        this.type = type;
        this.details = details;
        //this.qrCode = qrCode;
        this.idApiary = idApiary;
        this.honeySupers = new ArrayList<>();
        this.visits = new ArrayList<>();
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getDetails() { return details; }

    public void setDetails(String details) { this.details = details; }

    // public String getQrCode() { return qrCode; }
    //
    // public void setQrCode(String qrCode) { this.qrCode = qrCode; }

    public String getIdApiary() { return idApiary; }

    public void setIdApiary(String idApiary) { this.idApiary = idApiary; }

    public List<HoneySuperModel> getHoneySupers() { return honeySupers; }

    public void setHoneySupers(ArrayList<HoneySuperModel> honeySupers) { this.honeySupers = honeySupers; }

    public List<VisitModel> getVisits() { return visits; }

    public void setVisits(ArrayList<VisitModel> visits) { this.visits = visits; }

    public void addHoneySuper(HoneySuperModel honeySuper) { this.honeySupers.add(honeySuper); }

    public void addVisit(VisitModel visit) { this.visits.add(visit); }
}
