package com.uqac.beesness.model;

public class VisitModel {

    private int idVisit;
    private String type;
    private String description;
    private int idBeehive;

    public VisitModel() {
        this.type = "";
        this.description = "";
    }

    public VisitModel(int idVisit, String type, String description, int idBeehive) {
        this.idVisit = idVisit;
        this.type = type;
        this.description = description;
        this.idBeehive = idBeehive;
    }

    public int getIdVisit() { return idVisit; }

    public void setIdVisit(int idVisit) { this.idVisit = idVisit; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getIdBeehive() { return idBeehive; }

    public void setIdBeehive(int idBeehive) { this.idBeehive = idBeehive; }
}
