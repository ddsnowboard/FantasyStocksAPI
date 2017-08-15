package com.jameswk2.FantasyStocksAPI;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.google.gson.JsonPrimitive;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class NetworkBackendTests {
    private static final String TESTING_URL = "http://fantasystocks.herokuapp.com/api/v1/";
    // private static final String TESTING_URL = "http://127.0.0.1:8000/api/v1/";
    private static final String TESTING_ENDPOINT = "test/";
    private static URLBackend backend;
    private static final Gson gson = FantasyStocksAPI.gson;

    @BeforeClass
    public static void setUp() {
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
        JsonObject expected = getExpected(new HashMap<>(), new HashMap<>());
        assertEquals(expected, outObj);
    }

    @Test
    public void harderGet() {
        Map<String, String> queryString = new HashMap<>();
        queryString.put("Ham", "eggs");
        queryString.put("pork", "beans");

        JsonObject expected = getExpected(queryString, new HashMap<>());

        String out = backend.get(TESTING_ENDPOINT, queryString);
        JsonObject outObj = gson.fromJson(out, JsonObject.class);
        assertEquals(2, outObj.size());
        assertEquals(expected, outObj);
    }

    @Test
    public void easyPost() {
        Map<String, String> map = new HashMap<>();
        map.put("apples", "bananas");
        map.put("bears", "orcs");
        JsonObject jsonObj = mapToJsonObject(map);
        String out = backend.post(TESTING_ENDPOINT, gson.toJson(jsonObj));
        JsonObject retObj = gson.fromJson(out, JsonObject.class);
        assertEquals(retObj, getExpected(new HashMap<>(), map));
    }

    @Test
    public void harderPost() {
        // I need to add a test that does both a POST body and querystring, but I need to change the server code for that.

        Map<String, String> map = new HashMap<>();
        map.put("apples", "bananas");
        map.put("bears", "orcs");
        JsonObject jsonObj = mapToJsonObject(map);
        Map<String, String> queryString = new HashMap<>();
        queryString.put("sing", "songs");
        queryString.put("Sting", "Police");
        String out = backend.post(TESTING_ENDPOINT, queryString, gson.toJson(jsonObj));
        JsonObject retObj = gson.fromJson(out, JsonObject.class);
        assertEquals(retObj, getExpected(queryString, map));
    }

    private JsonObject getExpected(Map<String, String> get, Map<String, String> post) {
        JsonObject ret = new JsonObject();

        JsonObject getObject = mapToJsonObject(get);

        JsonObject postObject = mapToJsonObject(post);
        ret.add("get", getObject);
        ret.add("post", postObject);
        return ret;
    }

    private JsonObject mapToJsonObject(Map<String, String> m) {
        JsonObject ret = new JsonObject();
        for(Map.Entry<String, String> e : m.entrySet()) {
            ret.add(e.getKey(), new JsonPrimitive(e.getValue()));
        }
        return ret;
    }
}