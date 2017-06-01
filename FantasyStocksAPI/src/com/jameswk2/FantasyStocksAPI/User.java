package com.jameswk2.FantasyStocksAPI;

import static com.jameswk2.FantasyStocksAPI.FullUser.MODEL_NAME;


public interface User {
    /**
     * @return the integer id of this user
     */
    int getId();

    /**
     * @return the username of this user
     */
    String getUsername();

    /**
     * @return all the {@link Player}s that have this object as their User 
     *         field
     */
    Player[] getPlayers();

    /**
     * @param id the id of the user to get
     * @return the User object with the given integer id
     */
    static FullUser get(int id) {
        return (FullUser) Model.getModel(id, MODEL_NAME, FullUser.class);
    }

    /**
     * @return all the Users in the databse
     */
    static User[] getUsers() {
        return (User[]) Model.getModel(MODEL_NAME, FullUser.class);
    }
}
