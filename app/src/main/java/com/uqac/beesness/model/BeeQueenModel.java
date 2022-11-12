package com.uqac.beesness.model;

public class BeeQueenModel {

    private String birthYear;
    private String origin;
    private String bloodline;
    private int idBeehive;

    public BeeQueenModel() {
        this.birthYear = "";
        this.origin = "";
        this.bloodline = "";
    }

    public BeeQueenModel(String birthYear, String origin, String bloodline, int idBeehive) {
        this.birthYear = birthYear;
        this.origin = origin;
        this.bloodline = bloodline;
        this.idBeehive = idBeehive;
    }

    public String getBirthYear() { return birthYear; }

    public void setBirthYear(String birthYear) { this.birthYear = birthYear; }

    public String getOrigin() { return origin; }

    public void setOrigin(String origin) { this.origin = origin; }

    public String getBloodline() { return bloodline; }

    public void setBloodline(String bloodline) { this.bloodline = bloodline; }

    public int getIdBeehive() { return idBeehive; }

    public void setIdBeehive(int idBeehive) { this.idBeehive = idBeehive; }
}
