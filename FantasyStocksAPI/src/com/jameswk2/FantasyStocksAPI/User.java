package com.jameswk2.FantasyStocksAPI;

import static com.jameswk2.FantasyStocksAPI.FullUser.MODEL_NAME;

/**
 * This is tied to an individual human playing the game.
 * It has many {@link Player}s tied to it, along with authentication information.
 */
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
    static User get(int id) {
        return (FullUser) FantasyStocksAPI.getInstance().getModel(id, MODEL_NAME, FullUser.class);
    }

    /**
     * @return all the Users in the databse
     */
    static User[] getUsers() {
        return (User[]) FantasyStocksAPI.getInstance().getModel(MODEL_NAME, FullUser.class);
    }
}
