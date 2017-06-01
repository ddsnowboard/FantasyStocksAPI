package com.jameswk2.FantasyStocksAPI;

import java.util.Date;

class AbbreviatedStock implements Stock {
    private int id;
    private String companyName;
    private String symbol;
    private Date lastUpdated;
    private double price;
    private double change;
    private StockSuggestion[] stockSuggestions;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public Date getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getChange() {
        return change;
    }

    @Override
    public StockSuggestion[] getStockSuggestions() {
        return stockSuggestions;
    }

    public String toString() {
        return String.format("AbbreviatedStock %s: %f", getSymbol(), getPrice());
    }

    public boolean equals(Object o) {
        if(o instanceof Stock) {
            return ((Stock) o).getId() == getId();
        }
        return false;
    }
}
