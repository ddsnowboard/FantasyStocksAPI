package com.jameswk2.FantasyStocksAPI;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by ddsnowboard on 4/16/17.
 */
class URLUtilsTest {
    @Test
    void get() throws MalformedURLException {
        // final String URL_STRING = "http://localhost:8000/api/v1/test/";
        final String URL_STRING = FantasyStocksAPI.DEFAULT_URL + "test/";
        URL url = new URL(URL_STRING);
        HashMap<String, String> qString = new HashMap<>();
        qString.put("Zilles", "Angrave");
        assertEquals("{\"Zilles\": [\"Angrave\"]}", URLUtils.get(url, qString));
    }

    @Test
    void post() throws MalformedURLException {
        // final String URL_STRING = "http://localhost:8000/api/v1/test/";
        final String URL_STRING = FantasyStocksAPI.DEFAULT_URL + "test/";
        URL url = new URL(URL_STRING);
        HashMap<String, String> qString = new HashMap<>();
        qString.put("Zilles", "Angrave");
        assertEquals("{\"Zilles\": \"Angrave\"}", URLUtils.post(url, new HashMap<>(), "{\"Zilles\": \"Angrave\"}"));
    }
}