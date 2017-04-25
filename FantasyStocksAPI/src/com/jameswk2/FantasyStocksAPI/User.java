package com.jameswk2.FantasyStocksAPI;

import static com.jameswk2.FantasyStocksAPI.FullUser.MODEL_NAME;

/**
 * Created by ddsnowboard on 4/17/17.
 */
public interface User {
    int getId();

    String getUsername();

    Player[] getPlayers();

    static FullUser get(int id) {
        return (FullUser) Model.getModel(id, MODEL_NAME, FullUser.class);
    }

    static User[] getUsers() {
        return (User[]) Model.getModel(MODEL_NAME, FullUser.class);
    }
}
