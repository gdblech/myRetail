package rest;

import java.math.BigDecimal;

//basic class for a product
public class Product {

    private int pid;
    private String name;
    private CurrentPrice currentPrice;

    public CurrentPrice getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(CurrentPrice currentPrice) {
        this.currentPrice = currentPrice;
    }


    public Product(){}


    public Product(int pid, String name, String currencyCode, BigDecimal value) {
        this.pid = pid;
        this.name = name;
    }

    public Product(int pid, String name, String currencyCode, String value) {
        this.pid = pid;
        this.name = name;
    }

    public Product(String pid, String name, String currencyCode, String value) {
        this.pid = Integer.parseInt(pid);
        this.name = name;
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

    @Override
    public String toString() {
        return "{}";
    }
}
