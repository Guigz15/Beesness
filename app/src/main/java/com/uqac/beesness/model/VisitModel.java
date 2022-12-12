package com.uqac.beesness.model;

/**
 * This class represents a visit.
 */
public class VisitModel {

    /**
     * Enum for the status of a visit.
     */
    public enum VisitType {
        SANITARY_CONTROL,
        FEEDING,
        HARVESTING,
        OTHER
    }

    private String idVisit;
    private String date;
    private String description;
    private VisitType visitType;
    private String idBeehive;
    private String idUser_visitType;

    public VisitModel() {}

    public VisitModel(String idVisit, String date, String description, VisitType visitType, String idBeehive, String idUser_visitType) {
        this.idVisit = idVisit;
        this.date = date;
        this.description = description;
        this.visitType = visitType;
        this.idBeehive = idBeehive;
        this.idUser_visitType = idUser_visitType;
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

    public VisitType getVisitType() {
        return visitType;
    }

    public void setVisitType(VisitType visitType) {
        this.visitType = visitType;
    }

    public String getIdBeehive() { return idBeehive; }

    public void setIdBeehive(String idBeehive) { this.idBeehive = idBeehive; }

    public String getIdUser_visitType() { return idUser_visitType; }

    public void setIdUser_visitType(String idUser_visitType) { this.idUser_visitType = idUser_visitType; }
}
