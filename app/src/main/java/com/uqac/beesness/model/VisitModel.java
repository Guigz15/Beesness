package com.uqac.beesness.model;

public class VisitModel {

    private String type;
    private String description;
    private String idBeehive;

    public VisitModel() {
        this.type = "";
        this.description = "";
        this.idBeehive = "";
    }

    public VisitModel(String type, String description, String idBeehive) {
        this.type = type;
        this.description = description;
        this.idBeehive = idBeehive;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getIdBeehive() { return idBeehive; }

    public void setIdBeehive(String idBeehive) { this.idBeehive = idBeehive; }
}
