package com.jameswk2.FantasyStocksAPI;

import java.util.Arrays;

class AbbreviatedPlayer implements Player {
    private int id;
    private int user;
    private int floor;
    private int[] stocks;
    private int points;
    private boolean isFloor;
    private int[] sentTrades;
    private int[] receivedTrades;
    private boolean isFloorOwner;

    @Override
    public int getId() {
        return id;
    }

    public User getUser() {
        return User.get(user);
    }

    public Floor getFloor() {
        return Floor.get(floor);
    }

    public Stock[] getStocks() {
        return Arrays.stream(stocks).mapToObj(Stock::get).toArray(i -> new Stock[i]);
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public boolean isFloor() {
        return isFloor;
    }

    public Trade[] getSentTrades() {
        return Arrays.stream(sentTrades).mapToObj(Trade::get).toArray(i -> new Trade[i]);
    }

    public Trade[] getReceivedTrades() {
        return Arrays.stream(receivedTrades).mapToObj(Trade::get).toArray(i -> new Trade[i]);
    }

    @Override
    public boolean isFloorOwner() {
        return isFloorOwner;
    }

    public boolean equals(Object o) {
        if(o instanceof Player) {
            Player p = ((Player) o);
            return p.getId() == this.getId() && p.getFloor().getId() == this.getFloor().getId();
        }
        else
            return false;
    }

}
