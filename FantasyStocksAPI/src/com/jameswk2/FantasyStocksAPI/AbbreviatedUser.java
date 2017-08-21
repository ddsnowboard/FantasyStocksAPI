package com.jameswk2.FantasyStocksAPI;

import java.util.Arrays;

class AbbreviatedUser implements User {

    private String username;
    private int id;
    private transient int[] players;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public FullPlayer[] getPlayers() {
        return Arrays.stream(players).mapToObj(i -> FullPlayer.get(i)).toArray(i -> new FullPlayer[i]);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof User)
            return getId() == ((User) o).getId();
        return false;
    }

    @Override
    public String toString() {
        return String.format("AbbreviatedUser id: %d, username: %s", getId(), getUsername());
    }
}
