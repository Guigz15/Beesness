package com.uqac.beesness.model;

/**
 * This class represents a honey super.
 */
public class HoneySuperModel {

    private String idHoneySuper;
    private String name;
    private int nbFrames;
    private String idBeehive;
    private String idUser;

    public HoneySuperModel() {
    }

    public HoneySuperModel(String idHoneySuper, String name, int nbFrames, String idBeehive, String idUser) {
        this.idHoneySuper = idHoneySuper;
        this.name = name;
        this.nbFrames = nbFrames;
        this.idBeehive = idBeehive;
        this.idUser = idUser;
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

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
