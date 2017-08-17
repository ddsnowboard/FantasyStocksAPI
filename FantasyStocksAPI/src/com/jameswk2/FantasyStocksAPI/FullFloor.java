package com.jameswk2.FantasyStocksAPI;

import com.google.gson.annotations.SerializedName;

class FullFloor implements Floor {
    protected static final String MODEL_NAME = "floor";
    public enum Permissiveness {
        @SerializedName("Permissive")
        PERMISSIVE,
        @SerializedName("Open")
        OPEN,
        @SerializedName("Closed")
        CLOSED
    }
    private int id;
    private String name;
    private Stock[] stocks;
    private Permissiveness permissiveness;
    private User owner;
    private Player floorPlayer;
    private boolean isPublic;
    private int numStocks;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Stock[] getStocks() {
        return stocks;
    }

    public Permissiveness getPermissiveness() {
        return permissiveness;
    }

    public User getOwner() {
        return owner;
    }

    public Player getFloorPlayer() {
        return floorPlayer;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public int getNumStocks() {
        return numStocks;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStocks(Stock[] stocks) {
        this.stocks = stocks;
    }

    public void setPermissiveness(Permissiveness permissiveness) {
        this.permissiveness = permissiveness;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean equals(Object o) {
        return o instanceof Floor && ((Floor) o).getId() == getId();
    }
}
