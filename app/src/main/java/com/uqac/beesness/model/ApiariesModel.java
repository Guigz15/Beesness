package com.uqac.beesness.model;

public class ApiariesModel {

    private String name;
    private String environment;
    private String description;
    private int beehivesNumber;

    public ApiariesModel() {}

    public ApiariesModel(String name, int beehivesNumber) {
        this.name = name;
        this.beehivesNumber = beehivesNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBeehivesNumber() {
        return beehivesNumber;
    }

    public void setBeehivesNumber(int beehivesNumber) {
        this.beehivesNumber = beehivesNumber;
    }
}
