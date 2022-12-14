package com.uqac.beesness.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents a beehive.
 */
public class BeehiveModel {

    private String idBeehive;
    private String name;
    private String type;
    private String details;
    private HashMap<String, String> picturesUrl;
    private String idApiary;
    private List<HoneySuperModel> honeySupers;
    private List<VisitModel> visits;
    private BeeQueenModel beeQueen;
    private String idUser;

    public BeehiveModel() {
        picturesUrl = new HashMap<>();
        honeySupers = new ArrayList<>();
        visits = new ArrayList<>();
    }

    public BeehiveModel(String idBeehive, String name, String type, String details, String idApiary, BeeQueenModel beeQueen, String idUser) {
        this.idBeehive = idBeehive;
        this.name = name;
        this.type = type;
        this.details = details;
        this.idApiary = idApiary;
        this.honeySupers = new ArrayList<>();
        this.visits = new ArrayList<>();
        this.picturesUrl = new HashMap<>();
        this.beeQueen = beeQueen;
        this.idUser = idUser;
    }

    public String getIdBeehive() {
        return idBeehive;
    }

    public void setIdBeehive(String idBeehive) {
        this.idBeehive = idBeehive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getDetails() { return details; }

    public void setDetails(String details) { this.details = details; }

    public HashMap<String, String> getPicturesUrl() { return picturesUrl; }

    public void setPicturesUrl(HashMap<String, String> picturesUrl) { this.picturesUrl = picturesUrl; }

    public String getIdApiary() { return idApiary; }

    public void setIdApiary(String idApiary) { this.idApiary = idApiary; }

    public List<HoneySuperModel> getHoneySupers() { return honeySupers; }

    public void setHoneySupers(ArrayList<HoneySuperModel> honeySupers) { this.honeySupers = honeySupers; }

    public List<VisitModel> getVisits() { return visits; }

    public void setVisits(ArrayList<VisitModel> visits) { this.visits = visits; }

    public BeeQueenModel getBeeQueen() {
        return beeQueen;
    }

    public void setBeeQueen(BeeQueenModel beeQueen) {
        this.beeQueen = beeQueen;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
