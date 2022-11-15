package com.uqac.beesness.model;

public class HoneySuperModel {

    private int nbFrames;
    private String idBeehive;

    public HoneySuperModel() {
    }

    public HoneySuperModel(int nbFrames, String idBeehive) {
        this.nbFrames = nbFrames;
        this.idBeehive = idBeehive;
    }

    public int getNbFrames() { return nbFrames; }

    public void setNbFrames(int nbFrames) { this.nbFrames = nbFrames; }

    public String getIdBeehive() { return idBeehive; }

    public void setIdBeehive(String idBeehive) { this.idBeehive = idBeehive; }
}
