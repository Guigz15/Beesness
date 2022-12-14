package com.uqac.beesness.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an apiary.
 */
public class ApiaryModel {

    private String idApiary;
    private String name;
    private String environment;
    private String description;
    private LocationModel location;
    private List<BeehiveModel> beehives;
    private String idUser;

    public ApiaryModel() {
        beehives = new ArrayList<>();
    }

    public ApiaryModel(String idApiary, String name, String environment, String description, LocationModel location, String idUser) {
        this.idApiary = idApiary;
        this.name = name;
        this.environment = environment;
        this.description = description;
        this.beehives = new ArrayList<>();
        this.location = location;
        this.idUser = idUser;
    }

    public String getIdApiary() { return idApiary; }

    public void setIdApiary(String idApiary) { this.idApiary = idApiary; }

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

    public List<BeehiveModel> getBeehives() {
        return beehives;
    }

    public void setBeehives(ArrayList<BeehiveModel> beehives) {
        this.beehives = beehives;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public String getIdUser() { return idUser; }

    public void setIdUser(String idUser) { this.idUser = idUser; }
}
