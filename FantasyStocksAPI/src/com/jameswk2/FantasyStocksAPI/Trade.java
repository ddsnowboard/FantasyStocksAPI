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
 * This represents a desire by one {@link Player} to trade some {@link Stock}s with another on
 * the same {@link Floor}.
 */
public interface Trade {
    /**
     * Creates a Trade, uploads it to the server, and returns it.
     * @param senderPlayer the {@link Player} that is sending the Trade
     * @param recipientPlayer the {@link Player} that is sending the Trade
     * @param senderStocks all the {@link Stock}s that the sender will give away in the Trade
     * @param recipientStocks all the {@link Stock}s that the recipient will give away in the Trade
     * @param floor the {@link Floor} that the Trade will take place on
     * @return the Trade that was created
     * @throws RuntimeException if there was a server-side error. See the message.
     */
    static Trade create(Player senderPlayer, Player recipientPlayer, Stock[] senderStocks,
                        Stock[] recipientStocks, Floor floor) {
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("senderPlayer", senderPlayer.getId());
        jsonObj.addProperty("recipientPlayer", recipientPlayer.getId());

        JsonArray senderStockIds = new JsonArray();
        Arrays.stream(senderStocks).mapToInt(Stock::getId).forEach(senderStockIds::add);
        jsonObj.add("senderStocks", senderStockIds);

        JsonArray recipientStockIds = new JsonArray();
        Arrays.stream(recipientStocks).mapToInt(Stock::getId).forEach(recipientStockIds::add);
        jsonObj.add("recipientStocks", recipientStockIds);

        jsonObj.addProperty("floor", floor.getId());
        return FantasyStocksAPI.getInstance().createModel(FullTrade.class, jsonObj);
    }

    /**
     * @param id the id of the desired Trade
     * @return the Trade specified by the given id
     */
    static Trade get(int id) {
        return (Trade) FantasyStocksAPI.getInstance().getModel(id, MODEL_NAME, FullTrade.class);
    }

    /**
     * This returns all the Trades the user has access to, specifically 
     * those in which he involved or that are taking place on a Floor he owns.
     * @return all the Trades the user has access to
     */
    static Trade[] getTrades() {
        return (Trade[]) FantasyStocksAPI.getInstance().getModel(MODEL_NAME, FullTrade.class);
    }

    /**
     * @return the id of this object
     */
    int getId();

    /**
     * @return the {@link Player} that will receive this Trade
     */
    Player getRecipientPlayer();

    /**
     * @return the {@link Player} that sent this Trade
     */
    Player getSenderPlayer();

    /**
     * @return an array of {@link Stock}s that the sender will give away in this Trade
     */
    Stock[] getSenderStocks();

    /**
     * @return an array of {@link Stock}s that the recipient will give away in this Trade
     */
    Stock[] getRecipientStocks();

    /**
     * @return the {@link Floor} that this Trade exists on
     */
    Floor getFloor();

    /**
     * @return the Date this trade was created
     */
    Date getDate();

    /**
     * Accepts this Trade
     */
    void accept();

    /**
     * Declines this Trade
     */
    void decline();
}
