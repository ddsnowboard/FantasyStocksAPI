package com.jameswk2.FantasyStocksAPI;

import java.util.Arrays;

/**
 * Created by ddsnowboard on 4/17/17.
 */
public class AbbreviatedUser implements User {

    private String username;
    private int id;
    private int[] players;

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
    public FullUser getInFull() {
        return User.get(id);
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
