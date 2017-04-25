package com.jameswk2.FantasyStocksAPI;

import java.util.Date;

/**
 * Created by ddsnowboard on 4/17/17.
 */
public class FullStock implements Stock {
    protected static final String MODEL_NAME = "stock";

    private int id;
    private String companyName;
    private String symbol;
    private Date lastUpdated;
    private double price;
    private double change;
    private StockSuggestion[] stockSuggestions;

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getSymbol() {
        return symbol;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public double getPrice() {
        return price;
    }

    public double getChange() {
        return change;
    }

    public StockSuggestion[] getStockSuggestions() {
        return stockSuggestions;
    }

    public String toString() {
        return String.format("FullStock %s: %f", getSymbol(), getPrice());
    }

    public boolean equals(Object o) {
        if(o instanceof Stock) {
            return ((Stock) o).getId() == getId();
        }
        return false;
    }
}
