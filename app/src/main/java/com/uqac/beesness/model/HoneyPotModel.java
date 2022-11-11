package com.uqac.beesness.model;

public class HoneyPotModel {

    private double volume;
    private int quantity;
    private double price;

    public HoneyPotModel() {}

    public HoneyPotModel(double volume, int quantity, double price) {
        this.volume = volume;
        this.quantity = quantity;
        this.price = price;
    }

    public double getVolume() { return volume; }

    public void setVolume(double volume) { this.volume = volume; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }
}
