package com.uqac.beesness.model;

import java.util.ArrayList;
import java.util.List;

public class ProductModel {

    private String pictureUrl;
    private String idProduct;
    private int quantityProduct;
    private double price;
    private String name;
    private List<HoneyPotModel> honeyPots;
    private String idUser;

    public ProductModel() {
        this.honeyPots = new ArrayList<>();
    }

    public ProductModel(String idProduct, String name, int quantityProduct, String idUser) {
        this.idProduct = idProduct;
        this.name = name;
        this.quantityProduct = quantityProduct;
        this.idUser = idUser;
        this.honeyPots = new ArrayList<>();
    }

    public ProductModel(String idProduct, int quantityProduct, double price, String name) {
        this.idProduct = idProduct;
        this.quantityProduct = quantityProduct;
        this.price = price;
        this.name = name;
        this.honeyPots = new ArrayList<>();
    }

    public String getIdProduct() { return idProduct; }

    public void setIdProduct(String idProduct) { this.idProduct = idProduct; }

    public String getPictureUrl() { return pictureUrl; }

    public void setPictureUrl(String pictureUrl) { this.pictureUrl = pictureUrl; }

    public int getQuantityProduct() { return quantityProduct; }

    public void setQuantityProduct(int quantityProduct) { this.quantityProduct = quantityProduct; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getIdUser() { return idUser; }

    public void setIdUser(String idUser) { this.idUser = idUser; }

    public List<HoneyPotModel> getHoneyPots() { return honeyPots; }

    public void setHoneyPots(ArrayList<HoneyPotModel> honeyPots) { this.honeyPots = honeyPots; }

    public void addHoneyPot(HoneyPotModel honeyPot) { this.honeyPots.add(honeyPot); }
}
