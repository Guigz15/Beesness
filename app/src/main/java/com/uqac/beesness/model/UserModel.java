package com.uqac.beesness.model;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private String idUser;
    private String lastname;
    private String firstname;
    private String email;
    private String address;
    private String beekeeper_number;
    private List<ApiaryModel> apiaries;
    private List<ProductModel> products;

    public UserModel() {
        this.apiaries = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public UserModel(String idUser, String lastname, String firstname, String email) {
        this.idUser = idUser;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.apiaries = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public UserModel(String idUser, String lastname, String firstname, String email, String address, String beekeeper_number) {
        this.idUser = idUser;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.address = address;
        this.beekeeper_number = beekeeper_number;
        this.apiaries = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public String getIdUser() { return idUser; }

    public void setIdUser(String idUser) { this.idUser = idUser; }

    public String getLastname() { return lastname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getFirstname() { return firstname; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getBeekeeper_number() { return beekeeper_number; }

    public void setBeekeeper_number(String beekeeper_number) { this.beekeeper_number = beekeeper_number; }

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
