package com.jameswk2.FantasyStocksAPI;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;

import static com.jameswk2.FantasyStocksAPI.FullTrade.MODEL_NAME;

/**
 * Created by ddsnowboard on 4/17/17.
 */
public interface Trade {
    static Trade create(Player senderPlayer, Player recipientPlayer, Stock[] senderStocks,
                        Stock[] recipientStocks, Floor floor) {
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("senderPlayer", senderPlayer.getId());
        jsonObj.addProperty("recipientPlayer", recipientPlayer.getId());

        JsonArray senderStockIds = new JsonArray();
        Arrays.stream(senderStocks).mapToInt(s -> s.getId()).forEach(i -> senderStockIds.add(i));
        jsonObj.add("senderStocks", senderStockIds);

        JsonArray recipientStockIds = new JsonArray();
        Arrays.stream(recipientStocks).mapToInt(s -> s.getId()).forEach(i -> recipientStockIds.add(i));
        jsonObj.add("recipientStocks", recipientStockIds);

        jsonObj.addProperty("floor", floor.getId());
        Gson gson = new Gson();
        try {
            URL url = new URL(FantasyStocksAPI.BASE_URL + MODEL_NAME + "/create/");
            JsonObject jsonResponse = gson.fromJson(URLUtils.post(url, gson.toJson(jsonObj)), JsonObject.class);
            System.out.println(jsonObj);
            System.out.println(jsonResponse);
            if(jsonResponse.has("error"))
                throw new RuntimeException(jsonResponse.get("error").getAsString());

            return gson.fromJson(jsonResponse, FullTrade.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException("There is an issue with the URL");
        }
    }

    int getId();

    Player getRecipientPlayer();

    Player getSenderPlayer();

    Stock[] getSenderStocks();

    Stock[] getRecipientStocks();

    Floor getFloor();

    Date getDate();

    static FullTrade get(int id) {
        return (FullTrade) Model.getModel(id, MODEL_NAME, FullUser.class);
    }

    static FullTrade[] getTrades() {
        return (FullTrade[]) Model.getModel(MODEL_NAME, FullTrade.class);
    }
}
