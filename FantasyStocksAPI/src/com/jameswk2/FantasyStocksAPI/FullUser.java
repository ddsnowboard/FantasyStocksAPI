package com.jameswk2.FantasyStocksAPI;

class FullUser implements User {
    protected static final String MODEL_NAME = "user";

    private int id;
    private String username;
    private transient Player[] players;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public Player[] getPlayers() {
        return players;
    }

    void setPlayers(Player[] players) {
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
