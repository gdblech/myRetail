package com.myRetail.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;


//basie class for a product
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @Id
    private Long id;
    private String name;
    private BigDecimal currentPrice = new BigDecimal(0);
    private String currencyCode = "USD";

    public Product() {
    }

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

    public Product(Product product) {
        this.id = product.id;
        this.name = product.name;
        this.currentPrice = product.currentPrice;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
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

    @SuppressWarnings("unchecked")
    @JsonProperty("item")
    public void unpack(Map<String, Object> item) {
        this.id = Long.parseLong((String) item.get("tcin"));
        Map<String, Object> prodDesc = (Map<String, Object>) item.get("product_description");
        this.name = (String) prodDesc.get("title");
    }


    @Override
    public String toString() {
        return id + ": " + name + ", " + currentPrice;
    }
}
