package com.uqac.beesness.model;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private String idUser;
    private String username;
    private String email;
    private String password;
    private String address;
    private String idBeekeeper;
    private List<ApiaryModel> apiaries;
    private List<ProductModel> products;

    public UserModel() {
        this.idUser = "";
        this.username = "";
        this.email = "";
        this.apiaries = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public UserModel(String idUser, String username, String email) {
        this.idUser = idUser;
        this.username = username;
        this.email = email;
        this.apiaries = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public String getIdUser() { return idUser; }

    public void setIdUser(String idUser) { this.idUser = idUser; }

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

    public String getIdBeekeeper() { return idBeekeeper; }

    public void setIdBeekeeper(String idBeekeeper) { this.idBeekeeper = idBeekeeper; }

    public List<ApiaryModel> getApiaries() { return apiaries; }

    public void setApiaries(ArrayList<ApiaryModel> apiaries) { this.apiaries = apiaries; }

    public List<ProductModel> getProducts() { return products; }

    public void setProducts(ArrayList<ProductModel> products) { this.products = products; }

    public void addApiary(ApiaryModel apiary) { this.apiaries.add(apiary); }

    public void addProduct(ProductModel product) { this.products.add(product); }
}
