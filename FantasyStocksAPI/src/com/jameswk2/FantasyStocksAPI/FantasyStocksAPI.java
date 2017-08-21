package com.jameswk2.FantasyStocksAPI;

import com.google.gson.*;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * This is the first class that the client interacts with, where all
 * the authentication takes place.
 * Created by ddsnowboard on 4/17/17.
 */
public class FantasyStocksAPI {
    // The URL for testing. I should probably find a better way to do this, but that can
    // be part of #1
    // static final String BASE_URL = "http://localhost:8000/api/v1/";

    static final URL BASE_URL;

    static {
        try {
            BASE_URL = new URL("http://fantasystocks.herokuapp.com/api/v1/");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Something bad happened with the hardcoded URL. " +
                    "The gods are not on your side today");
        }
    }

    private NetworkBackend backend;

    private static GsonBuilder gsonBuilder = new GsonBuilder();

    static {
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssX");

        InstanceCreator<User> userInstanceCreator = type -> new AbbreviatedUser();
        gsonBuilder.registerTypeAdapter(User.class, userInstanceCreator);

        InstanceCreator<Player> playerInstanceCreator = type -> new AbbreviatedPlayer();
        gsonBuilder.registerTypeAdapter(Player.class, playerInstanceCreator);

        InstanceCreator<Floor> floorInstanceCreator = type -> new AbbreviatedFloor();
        gsonBuilder.registerTypeAdapter(Floor.class, floorInstanceCreator);

        InstanceCreator<Stock> stockInstanceCreator = type -> new AbbreviatedStock();
        gsonBuilder.registerTypeAdapter(Stock.class, stockInstanceCreator);
    }

    static final Gson gson = gsonBuilder.create();
    private static FantasyStocksAPI instance = null;

    private String sessionId;

    private User user = null;

    /**
     * @return the singleton instance of the {@link FantasyStocksAPI}
     */
    public static FantasyStocksAPI getInstance() {
        if (instance == null)
            // This constructor sets the `instance` variable
            return new FantasyStocksAPI(new URLBackend(BASE_URL));
        else
            return instance;
    }

    /**
     * The package-private constructor. Is only called from the getInstance() method (or unit tests)
     */
    FantasyStocksAPI(NetworkBackend backend) {
        instance = this;
        this.backend = backend;
    }

    /**
     * This returns the {@link User} that the client logged in with.
     *
     * @return the {@link User} object that the client logged in as
     * @throws RuntimeException if the client has not logged in yet
     */
    public User getUser() {
        if (user == null) {
            throw new RuntimeException("You need to log in or create a user first!");
        }
        return user;
    }

    /**
     * Tries to log in with the given username and password.
     *
     * @param username the username with which the client wants to log in
     * @param password the password with which the client wants to log in
     * @throws IllegalArgumentException if the username or password are wrong. The API
     *                                  will specify which was caused the error in the message.
     */
    public void login(String username, String password) {
        final String LOGIN_ENDPOINT = "auth/getKey/";
        Map<String, String> jsonRequest = new HashMap<>();
        jsonRequest.put("username", username);
        jsonRequest.put("password", password);
        String retString = backend.post(LOGIN_ENDPOINT, gson.toJson(jsonRequest));
        AuthResponse response = gson.fromJson(retString, AuthResponse.class);
        if (response.getSessionId() != null) {
            user = response.getUser();
            sessionId = response.getSessionId();
        } else
            throw new IllegalArgumentException(gson.fromJson(retString, JsonElement.class).getAsJsonObject().get("error").getAsString());
    }

    /**
     * Registers the client with the given username and password and logs him in.
     *
     * @param username the username to register with
     * @param password the password to register with
     * @throws IllegalArgumentException if there is an error with the registration. There
     *                                  will be information in the message, but chances
     *                                  are that the username is already taken.
     * @throws RuntimeException         if there is some other error. May God help you if this happens.
     */
    public User register(String username, String password) {
        final String REGISTER_ENDPOINT = "user/create/";
        Map<String, String> jsonRequest = new HashMap<>();
        jsonRequest.put("username", username);
        jsonRequest.put("password", password);
        String retString = backend.post(REGISTER_ENDPOINT, gson.toJson(jsonRequest));
        JsonElement response = gson.fromJson(retString, JsonElement.class);
        if (response.isJsonObject()) {
            JsonObject jsonObj = response.getAsJsonObject();
            if (jsonObj.has("error")) {
                throw new IllegalArgumentException(jsonObj.get("error").getAsString());
            } else {
                login(username, password);
                return gson.fromJson(jsonObj, FullUser.class);
            }
        } else {
            throw new RuntimeException(String.format("Something bad happened; I dunno what. Here's the response: %s",
                    retString));
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
     *
     * @param id the Firebase id to send
     * @throws RuntimeException if there is an error. See the message, because I don't know what
     *                          it could be.
     */
    public void registerFirebaseId(String id) {
        final String REGISTER_FIREBASE_ID_ENDPOINT = "android/register/";
        JsonObject obj = new JsonObject();
        obj.add("registrationToken", new JsonPrimitive(id));
        JsonElement response = gson.fromJson(backend.post(REGISTER_FIREBASE_ID_ENDPOINT,
                gson.toJson(obj)), JsonElement.class);
        JsonObject responseObject = response.getAsJsonObject();
        if (!responseObject.has("success"))
            throw new RuntimeException(responseObject.getAsString());
    }

    /**
     * @param id         the id in the database of the desired object
     * @param modelName  the name of the model class. Each full model class
     *                   has a MODEL_NAME constant variable that contains the
     *                   peoper model name. You <i>must</i> use this.
     * @param modelClass the class of the model that you want to be returned.
     *                   Usually the full version of the model.
     * @return the instance of the given model class with the given id.
     * It will need to be cast to the proper class after it is returned.
     * @throws RuntimeException if there is an error getting the data from the server.
     *                          See the message for detais.
     */
    Object getModel(int id, String modelName, Class modelClass) {
        final String endpoint = modelName + "/view/" + id;
        String response = backend.get(endpoint);
        JsonElement retval = gson.fromJson(response, JsonElement.class);
        if (!retval.isJsonObject())
            throw new RuntimeException("The json wasn't what I expected.");
        JsonObject jsonObj = retval.getAsJsonObject();
        if (jsonObj.get("error") == null)
            return gson.fromJson(jsonObj, modelClass);
        else
            throw new RuntimeException(jsonObj.get("error").getAsString());
    }

    /**
     * @param modelName  the name of the model class. Each full model class
     *                   has a MODEL_NAME constant variable that contains the
     *                   proper model name. You <i>must</i> use this.
     * @param modelClass the class of the model that you want to be returned.
     *                   Usually the full version of the model.
     * @return all the objects in the database that the logged in user
     * can access of the given model
     * @throws RuntimeException if there is an error getting the data from the server.
     *                          See the message for detais.
     */
    Object[] getModel(String modelName, Class modelClass) {
        final String endpoint = modelName + "/view/";
        String response = backend.get(endpoint);
        JsonElement jsonObj = gson.fromJson(response, JsonElement.class);
        if (!jsonObj.isJsonArray())
            throw new RuntimeException(String.format("The json wasn't what I expected. Here it is: %s", jsonObj.toString()));
        JsonArray jsonArray = jsonObj.getAsJsonArray();

        Object[] retval = (Object[]) Array.newInstance(modelClass, jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            retval[i] = gson.fromJson(jsonArray.get(i), modelClass);
        }
        return retval;
    }

    /**
     * Creates a model given a model name and its attributes
     *
     * @param modelName       the name of the model class. Each full model class
     *                        has a MODEL_NAME constant variable that contains the
     *                        proper model name. You <i>must</i> use this.
     * @param modelAttributes the attributes of the model to be created
     * @return the created model. Must be cast to the type you want
     */
    Object createModel(String modelName, JsonObject modelAttributes) {
        final String endpoint = modelName + "/create/";
        JsonObject jsonResponse = gson.fromJson(backend.post(endpoint, gson.toJson(modelAttributes)), JsonObject.class);
        if (jsonResponse.has("error"))
            throw new RuntimeException(jsonResponse.get("error").getAsString());

        return gson.fromJson(jsonResponse, JsonObject.class);
    }

    /**
     * Accepts the given {@link Trade}
     *
     * @param t the {@link Trade} to accept
     */
    void acceptTrade(Trade t) {
        final String endpoint = FullTrade.MODEL_NAME + "/accept/";
        String response = backend.post(endpoint, "");
        JsonObject jsonObj = gson.fromJson(response, JsonObject.class);
        if (jsonObj.has("success"))
            return;
        else
            throw new RuntimeException(jsonObj.get("error").getAsString());
    }

    /**
     * Accepts the given {@link Trade}
     *
     * @param t the {@link Trade} to accept
     */
    protected void declineTrade(Trade t) {
        final String endpoint = FullTrade.MODEL_NAME + "/accept/";
        String response = backend.post(endpoint, "");
        JsonObject jsonObj = gson.fromJson(response, JsonObject.class);
        if (jsonObj.has("success"))
            return;
        else
            throw new RuntimeException(jsonObj.get("error").getAsString());
    }
}
