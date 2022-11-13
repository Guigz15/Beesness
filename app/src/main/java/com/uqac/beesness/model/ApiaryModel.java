package com.uqac.beesness.model;

import android.location.Location;
import java.util.ArrayList;

public class ApiaryModel {
    private String name;
    private String environment;
    private String description;
    private int beehivesNumber;
    private Location location;
    private ArrayList<BeehiveModel> beehives;
    private int idUser;

    public ApiaryModel() {
        this.name = "";
        this.environment = "";
        this.description = "";
        this.beehivesNumber = 0;
        this.beehives = new ArrayList<>();
        this.location = new Location("");
    }

    public ApiaryModel(String name, String environment, String description, Location location) {
        this.name = name;
        this.environment = environment;
        this.description = description;
        this.beehivesNumber = 0;
        this.beehives = new ArrayList<>();
        this.location = location;
    }

    // For test
    public ApiaryModel(String name, int beehivesNumber) {
        this.name = name;
        this.beehivesNumber = beehivesNumber;
        this.location = null;
        beehives = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBeehivesNumber() {
        return beehivesNumber;
    }

    public void setBeehivesNumber(int beehivesNumber) {
        this.beehivesNumber = beehivesNumber;
    }

    public ArrayList<BeehiveModel> getBeehives() {
        return beehives;
    }

    public void setBeehives(ArrayList<BeehiveModel> beehives) {
        this.beehives = beehives;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getIdUser() { return idUser; }

    public void setIdUser(int idUser) { this.idUser = idUser; }

    public void addBeehive(BeehiveModel beehive) { this.beehives.add(beehive); }
}
