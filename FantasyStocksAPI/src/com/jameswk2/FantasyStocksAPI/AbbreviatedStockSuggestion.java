package com.jameswk2.FantasyStocksAPI;


import java.util.Date;

class AbbreviatedStockSuggestion implements StockSuggestion {
    private int id;
    private int stock;
    private int requstingPlayer;
    private int floor;
    private Date date;

    @Override
    public int getId() {
        return id;
    }

    public Stock getStock() {
        return Stock.get(stock);
    }

    public Player getRequstingPlayer() {
        return Player.get(requstingPlayer);
    }

    public Floor getFloor() {
        return Floor.get(floor);
    }

    @Override
    public Date getDate() {
        return date;
    }

}
