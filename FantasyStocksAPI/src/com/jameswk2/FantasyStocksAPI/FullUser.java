package com.jameswk2.FantasyStocksAPI;

import java.util.Arrays;

class FullUser implements User {
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

    void setPlayers(AbbreviatedPlayer[] players) {
        this.players = players;
    }

    void setUsername(String username) {
        this.username = username;
    }

    void setId(int id) {
        this.id = id;
    }

    public boolean equals(Object o) {
        return o instanceof User && getId() == ((User) o).getId();
    }

    public String toString() {
        return String.format("FullUser id: %d, username: %s", getId(), getUsername());
    }
}
