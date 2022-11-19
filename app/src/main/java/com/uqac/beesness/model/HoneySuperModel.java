package com.uqac.beesness.model;

public class HoneySuperModel {

    private String idHoneySuper;
    private String name;
    private int nbFrames;
    private String idBeehive;

    public HoneySuperModel() {
    }

    public HoneySuperModel(String idHoneySuper, String name, int nbFrames, String idBeehive) {
        this.idHoneySuper = idHoneySuper;
        this.name = name;
        this.nbFrames = nbFrames;
        this.idBeehive = idBeehive;
    }

    public String getIdHoneySuper() {
        return idHoneySuper;
    }

    public void setIdHoneySuper(String idHoneySuper) {
        this.idHoneySuper = idHoneySuper;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbFrames() { return nbFrames; }

    public void setNbFrames(int nbFrames) { this.nbFrames = nbFrames; }

    public String getIdBeehive() {
        return idBeehive;
    }

    public void setIdBeehive(String idBeehive) {
        this.idBeehive = idBeehive;
    }
}
