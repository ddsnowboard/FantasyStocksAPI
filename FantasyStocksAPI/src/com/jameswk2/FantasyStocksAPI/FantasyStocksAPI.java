package com.jameswk2.FantasyStocksAPI;

import com.google.gson.*;
import com.jameswk2.FantasyStocksAPI.JsonResponses.AuthResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ddsnowboard on 4/17/17.
 */
public class FantasyStocksAPI {
    static final String BASE_URL = "http://fantasystocks.herokuapp.com/api/v1/";
    // static final String BASE_URL = "http://localhost:8000/api/v1/";
    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
    private static FantasyStocksAPI instance = null;

    private String sessionId;

    private FullUser user = null;

    private FantasyStocksAPI() {

    }

    public static FantasyStocksAPI getInstance() {
        if (instance == null)
            instance = new FantasyStocksAPI();
        return instance;
    }

    public FullUser getUser() {
        if (user == null) {
            throw new RuntimeException("You need to log in or create a user first!");
        }
        return user;
    }

    public void login(String username, String password) {
        try {
            URL url = new URL(BASE_URL + "auth/getKey/");
            Map<String, String> jsonRequest = new HashMap<>();
            jsonRequest.put("username", username);
            jsonRequest.put("password", password);
            String retString = URLUtils.post(url, new HashMap<>(), gson.toJson(jsonRequest));
            AuthResponse response = gson.fromJson(retString, AuthResponse.class);
            if (response.getSessionId() != null) {
                user = response.getUser();
                sessionId = response.getSessionId();
            } else
                throw new IllegalArgumentException(gson.fromJson(retString, JsonElement.class).getAsJsonObject().get("error").getAsString());
        } catch (MalformedURLException e) {
            throw new RuntimeException("I wrote the URL wrong");
        }
    }

    public User register(String username, String password) {
        try {
            URL url = new URL(BASE_URL + "user/create/");
            Map<String, String> jsonRequest = new HashMap<>();
            jsonRequest.put("username", username);
            jsonRequest.put("password", password);
            String retString = URLUtils.post(url, new HashMap<>(), gson.toJson(jsonRequest));
            JsonElement response = gson.fromJson(retString, JsonElement.class);
            if(response.isJsonObject()) {
                JsonObject jsonObj = response.getAsJsonObject();
                if(jsonObj.has("error")) {
                    throw new IllegalArgumentException(jsonObj.get("error").getAsString());
                }
                else {
                    login(username, password);
                    return gson.fromJson(jsonObj, FullUser.class);
                }
            }
            else {
                throw new RuntimeException(String.format("Something bad happened; I dunno what. Here's the response: %s",
                        retString));
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("I wrote the URL wrong");
        }
    }

    protected String getSessionId() {
        return sessionId;
    }

    public void registerFirebaseId(String id) {
        final URL url;
        try {
            url = new URL(FantasyStocksAPI.BASE_URL + "android/register/");
        } catch (MalformedURLException e) {
            throw new RuntimeException("I messed up the url");
        }
        JsonObject obj = new JsonObject();
        Gson gson = new Gson();
        obj.add("registrationToken", new JsonPrimitive(id));
        URLUtils.post(url, gson.toJson(obj));
    }
}
