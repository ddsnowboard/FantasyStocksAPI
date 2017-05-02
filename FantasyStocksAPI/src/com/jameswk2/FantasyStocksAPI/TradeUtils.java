package com.jameswk2.FantasyStocksAPI;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ddsnowboard on 5/2/17.
 */
public class TradeUtils {

    protected static void acceptTrade(Trade t) {
        URL url;
        try {
            url = new URL(FantasyStocksAPI.BASE_URL + FullTrade.MODEL_NAME + "/accept/" + t.getId());
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("I wrote the URL wrong");
        }
        String response = URLUtils.post(url, "");
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(response, JsonObject.class);
        if(jsonObj.has("success"))
            return;
        else
            throw new RuntimeException(jsonObj.get("error").getAsString());
    }

    protected static void declineTrade(Trade t) {
        URL url;
        try {
            url = new URL(FantasyStocksAPI.BASE_URL + FullTrade.MODEL_NAME + "/decline/" + t.getId());
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("I wrote the URL wrong");
        }
        String response = URLUtils.post(url, "");
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(response, JsonObject.class);
        if(jsonObj.has("success"))
            return;
        else
            throw new RuntimeException(jsonObj.get("error").getAsString());
    }
}
