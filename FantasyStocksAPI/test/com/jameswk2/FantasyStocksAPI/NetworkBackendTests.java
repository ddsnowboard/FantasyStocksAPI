package com.jameswk2.FantasyStocksAPI;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.google.gson.JsonPrimitive;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class NetworkBackendTests {
    // private static final String TESTING_URL = "http://fantasystocks.herokuapp.com/api/v1/";
    private static final String TESTING_URL = "http://127.0.0.1:8000/api/v1/";
    private static final String TESTING_ENDPOINT = "test";
    private static NetworkBackend backend;
    private static Gson gson;

    @BeforeClass
    public static void setUp() {
        gson = new Gson();
        try {
            backend = new URLBackend(new URL(TESTING_URL));
        } catch (MalformedURLException e) {
            throw new RuntimeException("Something bad happened");
        }
    }

    @Test
    public void easyGet() {
        String out = backend.get(TESTING_ENDPOINT);
        JsonObject outObj = gson.fromJson(out, JsonObject.class);
        assertEquals(0, outObj.size());
    }

    @Test
    public void harderGet() {
        Map<String, String> queryString = new HashMap<>();
        queryString.put("Ham", "eggs");
        queryString.put("pork", "beans");

        String out = backend.get(TESTING_ENDPOINT, queryString);
        JsonObject outObj = gson.fromJson(out, JsonObject.class);
        assertEquals(2, outObj.size());
        for (Map.Entry<String, String> e : queryString.entrySet())
            assertEquals(outObj.get(e.getKey()).getAsString(), e.getValue());
    }

    @Test
    public void easyPost() {
        JsonObject jsonObj = new JsonObject();
        jsonObj.add("apples", new JsonPrimitive("bananas"));
        jsonObj.add("bears", new JsonPrimitive("orcs"));
        String out = backend.post(TESTING_ENDPOINT, gson.toJson(jsonObj));
        JsonObject retObj = gson.fromJson(out, JsonObject.class);
        assertEquals(retObj, jsonObj);
    }

    // I need to add a test that does both a POST body and querystring, but I need to change the server code for that.
}