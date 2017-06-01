package com.jameswk2.FantasyStocksAPI;

import com.google.gson.*;
import com.jameswk2.FantasyStocksAPI.JsonResponses.AuthResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the first class that the client interacts with, where all
 * the authentication takes place.
 * Created by ddsnowboard on 4/17/17.
 */
public class FantasyStocksAPI {
    static final String BASE_URL = "http://fantasystocks.herokuapp.com/api/v1/";

    // The URL for testing. I should probably find a better way to do this, but that can 
    // be part of #1
    // static final String BASE_URL = "http://localhost:8000/api/v1/";

    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
    private static FantasyStocksAPI instance = null;

    private String sessionId;

    private FullUser user = null;

    /**
     * @return the singleton instance of the {@link FantasyStocksAPI}
     */
    public static FantasyStocksAPI getInstance() {
        if (instance == null)
            instance = new FantasyStocksAPI();
        return instance;
    }

    /**
     * The private nullary constructor. Is only called from the getInstance() method
     */
    private FantasyStocksAPI() { }

    /**
     * This returns the {@link User} that the client logged in with.
     * @return the {@link User} object that the client logged in as. 
     * @throws RuntimeException if the client has not logged in yet
     */
    public FullUser getUser() {
        if (user == null) {
            throw new RuntimeException("You need to log in or create a user first!");
        }
        return user;
    }

    /**
     * Tries to log in with the given username and password.
     * @param username the username with which the client wants to log in
     * @param password the password with which the client wants to log in
     * @throws IllegalArgumentException if the username or password are wrong. The API
     *                                  will specify which was caused the error in the message.
     */
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

    /**
     * Registers the client with the given username and password and logs him in.
     * @param username the username to register with
     * @param password the password to register with
     * @throws IllegalArgumentException if there is an error with the registration. There 
     *                                  will be information in the message, but chances
     *                                  are that the username is already taken.
     * @throws RuntimeException if there is some other error. May God help you if this happens.
     */
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

    /**
     * @return the sessionId of this API session
     */
    protected String getSessionId() {
        return sessionId;
    }

    /**
     * Registers a Firebase id to the logged in User. This allows
     * Firebase to send them push notifications.
     * @param id the Firebase id to send
     * @throws RuntimeException if there is an error. See the message, because I don't know what 
     *                          it could be.
     */
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
        JsonElement response = gson.fromJson(URLUtils.post(url, gson.toJson(obj)), JsonElement.class);
        JsonObject responseObject = response.getAsJsonObject();
        if(responseObject.has("success"))
            return;
        else
            throw new RuntimeException(responseObject.getAsString());
    }
}
