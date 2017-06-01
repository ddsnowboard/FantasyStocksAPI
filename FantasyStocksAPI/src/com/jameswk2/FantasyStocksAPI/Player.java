package com.jameswk2.FantasyStocksAPI;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;
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
     * @param user the {@link User} object that owns the created Player
     * @param floor the {@link Floor} object on which the created Player exists
     * @return the created Player
     */
    static Player create(User user, Floor floor) {
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("user", user.getId());
        jsonObj.addProperty("floor", floor.getId());
        Gson gson = new Gson();
        try {
            URL url = new URL(FantasyStocksAPI.BASE_URL + FullPlayer.MODEL_NAME + "/create/");
            JsonObject jsonResponse = gson.fromJson(URLUtils.post(url, gson.toJson(jsonObj)), JsonObject.class);
            if(jsonResponse.has("error"))
                throw new RuntimeException(jsonResponse.get("error").getAsString());

            return gson.fromJson(jsonResponse, FullPlayer.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException("There is an issue with the URL");
        }
    }

    /**
     * @param id the id of the desired Player
     */
    static Player get(int id) {
        if(!cache.containsKey(id))
            cache.put(id, (Player) Model.getModel(id, MODEL_NAME, FullPlayer.class));
        return cache.get(id);
    }

    /**
     * @return an array of all the Players in the database
     */
    static Player[] getPlayers() {
        return (Player[]) Model.getModel(MODEL_NAME, FullPlayer.class);
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
