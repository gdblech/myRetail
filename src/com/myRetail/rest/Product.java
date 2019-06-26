package rest;

import java.math.BigDecimal;

public class Product {

    private int pid;
    private String name;
    private String currencyCode;
    private BigDecimal value;

 //   public Product(){}

    //default tester product
    public Product(){
        pid = 124;
        name = "Geoffrey's Great Movie";
        value = new BigDecimal("100.00");
        currencyCode = CurrencyHandling.USD;

    }

    public Product(int pid, String name, String currencyCode, BigDecimal value) {
        this.pid = pid;
        this.name = name;
        this.currencyCode = currencyCode;
        this.value = value;
    }

    public Product(int pid, String name, String currencyCode, String value) {
        this.pid = pid;
        this.name = name;
        this.currencyCode = currencyCode;
        this.value = new BigDecimal(value);
    }

    public Product(String pid, String name, String currencyCode, String value) {
        this.pid = Integer.parseInt(pid);
        this.name = name;
        this.currencyCode = currencyCode;
        this.value = new BigDecimal(value);
    }
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setPid(String pid) {
        this.pid = Integer.parseInt(pid);
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

    public void setValue(String value) {
        this.value = new BigDecimal(value);
    }

    //todo
    @Override
    public String toString(){
        return "{ \"id\":" + pid +", \"name\": \"" + name +"\", \"current_price\": { \"value\": \"" + value.toString() + "\", \"currency_code\": \"" + currencyCode + "\" }}";
    }
}
