package com.jameswk2.FantasyStocksAPI;

import java.util.Arrays;

/**
 * Created by ddsnowboard on 4/17/17.
 */
public class FullUser implements User {
    protected static final String MODEL_NAME = "user";

    private int id;
    private String username;
    private AbbreviatedPlayer[] players;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public Player[] getPlayers() {
        return Arrays.stream(Player.getPlayers()).filter(p -> p.getUser().equals(this)).toArray(i -> new Player[i]);
    }

    public boolean equals(Object o) {
        if(!(o instanceof User))
            return false;
        return getId() == ((User)o).getId();
    }

    public String toString() {
        return String.format("FullUser id: %d, username: %s", getId(), getUsername());
    }
}
