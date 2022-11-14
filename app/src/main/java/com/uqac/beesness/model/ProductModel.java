package com.uqac.beesness.model;

import java.util.ArrayList;
import java.util.List;

public class ProductModel {

    //private String photo;
    private int idProduct;
    private int quantityProduct;
    private double price;
    private String name;
    private List<HoneyPotModel> honeyPots;
    private String idUser;

    public ProductModel() {
        this.quantityProduct = 0;
        this.price = 0.0;
        this.name = "";
        this.honeyPots = new ArrayList<>();
    }

    public ProductModel(int idProduct, int quantityProduct, double price, String name) {
        this.idProduct = idProduct;
        this.quantityProduct = quantityProduct;
        this.price = price;
        this.name = name;
        this.honeyPots = new ArrayList<>();
    }

    public int getIdProduct() { return idProduct; }

    public void setIdProduct(int idProduct) { this.idProduct = idProduct; }

    public int getQuantityProduct() { return quantityProduct; }

    public void setQuantityProduct(int quantityProduct) { this.quantityProduct = quantityProduct; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<HoneyPotModel> getHoneyPots() { return honeyPots; }

    public void setHoneyPots(ArrayList<HoneyPotModel> honeyPots) { this.honeyPots = honeyPots; }

    public void addHoneyPot(HoneyPotModel honeyPot) { this.honeyPots.add(honeyPot); }
}
