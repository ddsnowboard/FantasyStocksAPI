package com.jameswk2.FantasyStocksAPI;

import java.util.Arrays;

class AbbreviatedFloor implements Floor {
    private int id;
    private FullFloor.Permissiveness permissiveness;
    private boolean isPublic;
    private int numStocks;
    private int[] stocks;
    private int owner;
    private int floorPlayer;
    private String name;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public Stock[] getStocks() {
        return Arrays.stream(stocks).mapToObj(i -> Stock.get(i)).toArray(i -> new Stock[i]);
    }

    @Override
    public FullFloor.Permissiveness getPermissiveness() {
        return permissiveness;
    }

    public User getOwner() {
        return User.get(owner);
    }

    public Player getFloorPlayer() {
        return Player.get(floorPlayer);
    }

    @Override
    public boolean isPublic() {
        return isPublic;
    }

    @Override
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
