package com.jameswk2.FantasyStocksAPI;
import java.util.Date;

class FullStockSuggestion implements StockSuggestion {
    protected static final String MODEL_NAME = "stockSuggestion";

    protected int id;
    private FullStock stock;
    private Player requstingPlayer;
    private Floor floor;
    protected Date date;


    public int getId() {
        return id;
    }

    public FullStock getStock() {
        return stock;
    }

    public Player getRequstingPlayer() {
        return requstingPlayer;
    }

    public Floor getFloor() {
        return floor;
    }

    public Date getDate() {
        return date;
    }
}
