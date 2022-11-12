package com.uqac.beesness.model;

import java.util.ArrayList;

public class UserModel {

    private String username;
    private String email;
    private String password;
    private String address;
    private ArrayList<ApiarieModel> apiaries;
    private ArrayList<ProductModel> products;

    public UserModel() {
        this.username = "";
        this.email = "";
        this.password = "";
        this.address = "";
        this.apiaries = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public UserModel(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public UserModel(String username, String email, String password, String address) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.apiaries = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public ArrayList<ApiarieModel> getApiaries() { return apiaries; }

    public void setApiaries(ArrayList<ApiarieModel> apiaries) { this.apiaries = apiaries; }

    public ArrayList<ProductModel> getProducts() { return products; }

    public void setProducts(ArrayList<ProductModel> products) { this.products = products; }

    public void addApiary(ApiarieModel apiary) { this.apiaries.add(apiary); }

    public void addProduct(ProductModel product) { this.products.add(product); }
}
