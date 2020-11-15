package com.myRetail.rest;

import java.math.BigDecimal;
//class to represent the current price of a product
public final class CurrentPrice {

    //Currency codes, for demonstration purposes.
    public static final String USD = "USD";
    public static final String EUR = "EUR";
    public static final String AUD = "AUD";

    private BigDecimal value;
    private String currencyCode;

    public CurrentPrice(BigDecimal value, String currencyCode) {
        this.value = value;
        this.currencyCode = currencyCode;
    }

    public CurrentPrice(){}

    public CurrentPrice(String value, String currencyCode) {
        this.value = new BigDecimal(value);
        this.currencyCode = currencyCode;
    }
    public CurrentPrice(double value, String currencyCode) {
        this.value = new BigDecimal(value);
        this.currencyCode = currencyCode;
    }

    public CurrentPrice(float value, String currencyCode) {
        this.value = new BigDecimal(value);
        this.currencyCode = currencyCode;
    }
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
