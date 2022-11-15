package com.uqac.beesness.model;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private String idUser;
    private String username;
    private String email;
    private List<ApiaryModel> apiaries;
    private List<ProductModel> products;

    public UserModel() {
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

    public UserModel(UserModel user) {
        this.idUser = user.idUser;
        this.username = user.username;
        this.email = user.email;
        this.apiaries = user.apiaries;
        this.products = user.products;
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

    public List<ApiaryModel> getApiaries() { return apiaries; }

    public void setApiaries(ArrayList<ApiaryModel> apiaries) { this.apiaries = apiaries; }

    public List<ProductModel> getProducts() { return products; }

    public void setProducts(ArrayList<ProductModel> products) { this.products = products; }

    public void addApiary(ApiaryModel apiary) { this.apiaries.add(apiary); }

    public void addProduct(ProductModel product) { this.products.add(product); }

    public List<BeehiveModel> getBeehives() {
        List<BeehiveModel> beehives = new ArrayList<>();
        for (ApiaryModel apiary : apiaries) {
            beehives.addAll(apiary.getBeehives());
        }
        return beehives;
    }

    public ApiaryModel getApiary(String idApiary) {
        for (ApiaryModel apiary : apiaries) {
            if (apiary.getIdApiary().equals(idApiary)) {
                return apiary;
            }
        }
        return null;
    }
}
