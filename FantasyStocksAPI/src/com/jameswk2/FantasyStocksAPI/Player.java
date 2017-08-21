package com.jameswk2.FantasyStocksAPI;

import com.google.gson.JsonObject;

import java.util.HashMap;

import static com.jameswk2.FantasyStocksAPI.FullPlayer.MODEL_NAME;

/**
 * This is the manifestation of a {@link User} on a {@link Floor}.
 * Each of these is tied to exactly one {@link User} and one {@link Floor},
 * and owns some number of {@link Stock}s
 */
public interface Player {
    HashMap<Integer, Player> cache = new HashMap<>();

    /**
     * Creates a new Player based on the given parameters
     *
     * @param user  the {@link User} object that owns the created Player
     * @param floor the {@link Floor} object on which the created Player exists
     * @return the created Player
     */
    static Player create(User user, Floor floor) {
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("user", user.getId());
        jsonObj.addProperty("floor", floor.getId());
        *****************
        // This doesn't work. Something is wrong with casting a JosnObject to a Player, apparently
        return  FantasyStocksAPI.gson.fromJson(FantasyStocksAPI.getInstance().createModel(FullPlayer.MODEL_NAME, jsonObj), Player.class);
    }

    /**
     * @param id the id of the desired Player
     */
    static Player get(int id) {
        if (!cache.containsKey(id))
            cache.put(id, (Player) FantasyStocksAPI.getInstance().getModel(id, MODEL_NAME, FullPlayer.class));
        return cache.get(id);
    }

    /**
     * @return an array of all the Players in the database
     */
    static Player[] getPlayers() {
        return (Player[]) FantasyStocksAPI.getInstance().getModel(MODEL_NAME, FullPlayer.class);
    }

    /**
     * @return the id of ths object
     */
    int getId();

    /**
     * @return the {@link User} objec that owns this object
     */
    User getUser();

    /**
     * @return the {@link Floor} object on which this object exists
     */
    Floor getFloor();

    /**
     * @return an array of {@link Stock} objects that belong to this object
     */
    Stock[] getStocks();

    /**
     * @return the amount of points this object has
     */
    int getPoints();

    /**
     * @return whether this object is a Floor player or not
     */
    boolean isFloor();

    /**
     * @return all the {@link Trade}s that this object has sent
     */
    Trade[] getSentTrades();

    /**
     * @return all the {@link Trade}s that this object has received
     */
    Trade[] getReceivedTrades();

    /**
     * @return whether this object is the owner of its floor
     */
    boolean isFloorOwner();
}
