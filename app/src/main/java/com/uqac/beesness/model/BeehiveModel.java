package com.uqac.beesness.model;

import java.util.ArrayList;

public class BeehiveModel {

    private int idBeehive;
    private String type;
    private String details;
    //private String qrCode;
    private int idApiarie;
    private ArrayList<HoneySuperModel> honeySupers;
    private ArrayList<VisitModel> visits;

    public BeehiveModel() {
        this.type = "";
        this.details = "";
        this.honeySupers = new ArrayList<>();
        this.visits = new ArrayList<>();
    }

    public BeehiveModel(int idBeehive, String type, String details, int idApiarie) {
        this.idBeehive = idBeehive;
        this.type = type;
        this.details = details;
        //this.qrCode = qrCode;
        this.idApiarie = idApiarie;
        this.honeySupers = new ArrayList<>();
        this.visits = new ArrayList<>();
    }

    public int getIdBeehive() { return idBeehive; }

    public void setIdBeehive(int idBeehive) { this.idBeehive = idBeehive; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getDetails() { return details; }

    public void setDetails(String details) { this.details = details; }

    // public String getQrCode() { return qrCode; }
    //
    // public void setQrCode(String qrCode) { this.qrCode = qrCode; }

    public int getIdApiarie() { return idApiarie; }

    public void setIdApiarie(int idApiarie) { this.idApiarie = idApiarie; }

    public ArrayList<HoneySuperModel> getHoneySupers() { return honeySupers; }

    public void setHoneySupers(ArrayList<HoneySuperModel> honeySupers) { this.honeySupers = honeySupers; }

    public ArrayList<VisitModel> getVisits() { return visits; }

    public void setVisits(ArrayList<VisitModel> visits) { this.visits = visits; }

    public void addHoneySuper(HoneySuperModel honeySuper) { this.honeySupers.add(honeySuper); }

    public void addVisit(VisitModel visit) { this.visits.add(visit); }
}
