package com.uqac.beesness.model;

public class VisitModel {

    private String idVisit;
    private String date;
    private String description;
    private String idBeehive;

    public VisitModel() {
    }

    public VisitModel(String idVisit, String date, String description, String idBeehive) {
        this.idVisit = idVisit;
        this.date = date;
        this.description = description;
        this.idBeehive = idBeehive;
    }

    public VisitModel(String idVisit, String date, String idBeehive) {
        this.idVisit = idVisit;
        this.date = date;
        this.idBeehive = idBeehive;
    }

    public String getIdVisit() {
        return idVisit;
    }

    public void setIdVisit(String idVisit) {
        this.idVisit = idVisit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getIdBeehive() { return idBeehive; }

    public void setIdBeehive(String idBeehive) { this.idBeehive = idBeehive; }
}
