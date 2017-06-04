package com.jameswk2.FantasyStocksAPI;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a mock network backend
 * Created by ddsnowboard on 6/4/17.
 */
public class FakeNetworkBackend implements NetworkBackend {
    private Map<String, JsonObject> getOutputs = new HashMap<>();
    private Map<String, JsonObject> postOutputs = new HashMap<>();

    @Override
    public String get(String address, HashMap<String, String> queryString) {
        return null;
    }

    @Override
    public String get(String address) {
        return null;
    }

    @Override
    public String post(String address, HashMap<String, String> queryString, String jsonPostData) {
        return null;
    }

    @Override
    public String post(String address, String jsonPostData) {
        return null;
    }
}
