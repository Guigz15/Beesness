package com.uqac.beesness.model;

/**
 * This class represents a product.
 */
public class ProductModel {

    private String pictureUrl;
    private String idProduct;
    private int quantityProduct;
    private String name;
    private String idUser;

    public ProductModel() {
    }

    public ProductModel(String idProduct, String name, int quantityProduct, String idUser) {
        this.idProduct = idProduct;
        this.name = name;
        this.quantityProduct = quantityProduct;
        this.idUser = idUser;
    }

    public String getIdProduct() { return idProduct; }

    public void setIdProduct(String idProduct) { this.idProduct = idProduct; }

    public String getPictureUrl() { return pictureUrl; }

    public void setPictureUrl(String pictureUrl) { this.pictureUrl = pictureUrl; }

    public int getQuantityProduct() { return quantityProduct; }

    public void setQuantityProduct(int quantityProduct) { this.quantityProduct = quantityProduct; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getIdUser() { return idUser; }

    public void setIdUser(String idUser) { this.idUser = idUser; }
}
