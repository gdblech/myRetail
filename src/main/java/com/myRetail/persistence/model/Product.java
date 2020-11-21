package com.myRetail.persistence.model;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Objects;


//basic class for a product
public class Product {

    @Id
    private Long id;
    private String name;
    private BigDecimal currentPrice;

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }


    public Product(){}


    public Product(Long id, String name, BigDecimal value) {
        this.id = id;
        this.name = name;
        this.currentPrice = value;
    }

    public Product(Long id, String name, String value) {
        this.id = id;
        this.name = name;
        this.currentPrice = new BigDecimal(value);
    }

    public Product(Long id, String name, double value) {
        this.id = id;
        this.name = name;
        this.currentPrice = new BigDecimal(value);
    }

    public Product(Long id, String name, float value) {
        this.id = id;
        this.name = name;
        this.currentPrice = new BigDecimal(value);
    }

    public Product(Product product){
        this.id = product.id;
        this.name = product.name;
        this.currentPrice = product.currentPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id.equals(product.id) &&
                name.equals(product.name) &&
                currentPrice.equals(product.currentPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, currentPrice);
    }

    //todo
    @Override
    public String toString() {
        return id + ": " + name;
    }
}
