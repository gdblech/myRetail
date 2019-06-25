package rest;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private String currencyCode;
    private BigDecimal value;

 //   public Product(){}

    //default tester product
    public Product(){
        id = 123;
        name = "Geoffrey's Great Movie";
        value = new BigDecimal("100.00");
        currencyCode = CurrencyHandling.USD;

    }

    public Product(int id, String name, String currencyCode, BigDecimal value) {
        this.id = id;
        this.name = name;
        this.currencyCode = currencyCode;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    //todo
    @Override
    public String toString(){
        return null;
    }
}
