package com.uqac.beesness.model;

public class HoneySuperModel {

    private int idHoneySuper;
    private int nbFrames;
    private int idBeehive;

    public HoneySuperModel() {
        this.nbFrames = 0;
    }

    public HoneySuperModel(int idHoneySuper, int nbFrames, int idBeehive) {
        this.idHoneySuper = idHoneySuper;
        this.nbFrames = nbFrames;
        this.idBeehive = idBeehive;
    }

    public int getIdHoneySuper() { return idHoneySuper; }

    public void setIdHoneySuper(int idHoneySuper) { this.idHoneySuper = idHoneySuper; }

    public int getNbFrames() { return nbFrames; }

    public void setNbFrames(int nbFrames) { this.nbFrames = nbFrames; }

    public int getIdBeehive() { return idBeehive; }

    public void setIdBeehive(int idBeehive) { this.idBeehive = idBeehive; }
}
