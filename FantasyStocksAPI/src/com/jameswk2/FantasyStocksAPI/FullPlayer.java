package com.jameswk2.FantasyStocksAPI;

class FullPlayer implements Player {
    protected static final String MODEL_NAME = "player";

    private int id;
    private AbbreviatedUser user;
    private AbbreviatedFloor floor;
    private AbbreviatedStock[] stocks;
    private int points;
    private boolean isFloor;
    private AbbreviatedTrade[] sentTrades;
    private AbbreviatedTrade[] receivedTrades;
    private boolean isFloorOwner;

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Floor getFloor() {
        return floor;
    }

    public Stock[] getStocks() {
        return stocks;
    }

    public int getPoints() {
        return points;
    }

    public boolean isFloor() {
        return isFloor;
    }

    public Trade[] getSentTrades() {
        return sentTrades;
    }

    public Trade[] getReceivedTrades() {
        return receivedTrades;
    }

    public boolean isFloorOwner() {
        return isFloorOwner;
    }

    public static Player get(int id) {
        if(!cache.containsKey(id))
            cache.put(id, (FullPlayer) Model.getModel(id, MODEL_NAME, FullPlayer.class));
        return cache.get(id);
    }

    public static FullPlayer[] getPlayers() {
        return (FullPlayer[]) Model.getModel(MODEL_NAME, FullPlayer.class);
    }

    @Override
    public String toString() {
        return "FullPlayer{" +
                "id=" + id +
                ", floor=" + floor +
                '}';
    }
}
