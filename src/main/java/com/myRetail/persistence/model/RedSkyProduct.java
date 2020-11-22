package com.myRetail.persistence.model;

public class RedSkyProduct {
    Product product;

    public RedSkyProduct(Product product) {
        this.product = product;
    }

    public RedSkyProduct(){};

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
