package com.uqac.beesness.model;

/**
 * This class represents a user of the application.
 */
public class UserModel {

    private String idUser;
    private String lastname;
    private String firstname;
    private String email;
    private String address;
    private String beekeeper_number;

    public UserModel() {
    }

    public UserModel(String idUser, String lastname, String firstname, String email) {
        this.idUser = idUser;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
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
}
