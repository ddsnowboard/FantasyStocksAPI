package com.jameswk2.FantasyStocksAPI;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static com.jameswk2.FantasyStocksAPI.FullPlayer.MODEL_NAME;

/**
 * Created by ddsnowboard on 4/17/17.
 */
public interface Player {
    HashMap<Integer, Player> cache = new HashMap<>();

    static Player create(User user, Floor floor) {
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("user", user.getId());
        jsonObj.addProperty("floor", floor.getId());
        Gson gson = new Gson();
        try {
            URL url = new URL(FantasyStocksAPI.DEFAULT_URL + FullPlayer.MODEL_NAME + "/create/");
            JsonObject jsonResponse = gson.fromJson(URLUtils.post(url, gson.toJson(jsonObj)), JsonObject.class);
            if(jsonResponse.has("error"))
                throw new RuntimeException(jsonResponse.get("error").getAsString());

            return gson.fromJson(jsonResponse, FullPlayer.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException("There is an issue with the URL");
        }
    }

    int getId();

    User getUser();

    Floor getFloor();

    Stock[] getStocks();

    int getPoints();

    boolean isFloor();

    Trade[] getSentTrades();

    Trade[] getReceivedTrades();

    boolean isFloorOwner();

    static Player get(int id) {
        if(!cache.containsKey(id))
            cache.put(id, (Player) Model.getModel(id, MODEL_NAME, FullPlayer.class));
        return cache.get(id);
    }

    static Player[] getPlayers() {
        return (Player[]) Model.getModel(MODEL_NAME, FullPlayer.class);
    }
}
