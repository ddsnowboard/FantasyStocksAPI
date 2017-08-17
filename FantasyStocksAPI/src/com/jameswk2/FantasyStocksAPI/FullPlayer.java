package com.jameswk2.FantasyStocksAPI;

class FullPlayer implements Player {
    protected static final String MODEL_NAME = "player";

    private int id;
    private User user;
    private Floor floor;
    private Stock[] stocks;
    private int points;
    private boolean isFloor;
    private Trade[] sentTrades;
    private Trade[] receivedTrades;
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

    public void setFloor(Floor floor) {
        this.floor = floor;
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

    void setUser(User u) {
        this.user = u;
    }

    public static Player get(int id) {
        if(!cache.containsKey(id))
            cache.put(id, (FullPlayer) FantasyStocksAPI.getInstance().getModel(id, MODEL_NAME, FullPlayer.class));
        return cache.get(id);
    }

    public static FullPlayer[] getPlayers() {
        return (FullPlayer[]) FantasyStocksAPI.getInstance().getModel(MODEL_NAME, FullPlayer.class);
    }

    @Override
    public String toString() {
        return "FullPlayer{" +
                "id=" + id +
                ", floor=" + floor +
                '}';
    }
}
