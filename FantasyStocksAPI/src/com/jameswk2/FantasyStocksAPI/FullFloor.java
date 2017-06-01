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
    private AbbreviatedStock[] stocks;
    private Permissiveness permissiveness;
    private AbbreviatedUser owner;
    private AbbreviatedPlayer floorPlayer;
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

    public boolean equals(Object o) {
        if(o instanceof Floor)
            return ((Floor) o).getId() == getId();
        else
            return false;
    }
}
