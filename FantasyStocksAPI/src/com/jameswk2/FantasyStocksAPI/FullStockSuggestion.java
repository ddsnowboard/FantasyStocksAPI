package com.jameswk2.FantasyStocksAPI;
import java.util.Date;

class FullStockSuggestion {
    protected static final String MODEL_NAME = "stockSuggestion";

    protected int id;
    private FullStock stock;
    private Player requstingPlayer;
    private Floor floor;
    protected Date date;

    public static FullStockSuggestion get(int id) {
        return (FullStockSuggestion) Model.getModel(id, MODEL_NAME, FullStockSuggestion.class);
    }

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
