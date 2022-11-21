package com.uqac.beesness.model;

public class BeeQueenModel {

    private String birthYear;
    private String origin;
    private String bloodline;

    public BeeQueenModel() {
    }

    public BeeQueenModel(String origin, String bloodline, String birthYear) {
        this.origin = origin;
        this.bloodline = bloodline;
        this.birthYear = birthYear;
    }

    public String getBirthYear() { return birthYear; }

    public void setBirthYear(String birthYear) { this.birthYear = birthYear; }

    public String getOrigin() { return origin; }

    public void setOrigin(String origin) { this.origin = origin; }

    public String getBloodline() { return bloodline; }

    public void setBloodline(String bloodline) { this.bloodline = bloodline; }

    public boolean isEmpty() {
        return birthYear.isEmpty() && origin.isEmpty() && bloodline.isEmpty();
    }
}
