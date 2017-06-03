package com.jameswk2.FantasyStocksAPI;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This abstracts away a lot of network-related 
 * code.
 * Created by ddsnowboard on 4/16/17.
 */
class URLUtils {
    /**
     * Sends a GET request.
     * @param url the URL to send the request to
     * @param queryString a map of keys and values that will be converted into 
     *                    an HTTP query string
     * @return the response, as a string
     */
    public static String get(URL url, HashMap<String, String> queryString) {
        String urlBase = url.toString();
        String sessionId = FantasyStocksAPI.getInstance().getSessionId();
        if (sessionId != null)
            queryString.put("sessionId", sessionId);

        if (!queryString.isEmpty()) {
            urlBase += "?";
            urlBase += queryStringify(queryString);
        }
        try {
            return fromInputStream(new URL(urlBase).openStream());
        } catch (IOException e) {
            throw new RuntimeException(String.format("Something bad happened with the URL encoding. Here's the URL: %s", urlBase));
        }
    }

    /**
     * Sends a GET request with an empty query string.
     * @param url the URL to send the request to
     * @return the response, as a string
     */
    public static String get(URL url) {
        return get(url, new HashMap<>());
    }


    /**
     * Sends a POST request.
     * @param url the URL to send the request to
     * @param queryString a map of keys and values that will be converted into 
     *                    an HTTP query string
     * @param jsonPostData the JSON data that will make up the body, 
     *                     as a string
     * @return the response, as a string
     */
    public static String post(URL url, HashMap<String, String> queryString, String jsonPostData) {
        String urlBase = url.toString();
        String sessionId = FantasyStocksAPI.getInstance().getSessionId();

        if (sessionId != null)
            queryString.put("sessionId", sessionId);

        if (!queryString.isEmpty()) {
            urlBase += "?";
            urlBase += queryStringify(queryString);
        }
        try {
            URL newUrl = new URL(urlBase);
            HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(jsonPostData.getBytes("UTF-8"));
            os.close();

            return fromInputStream(conn.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(String.format("Something bad happened with the URL reading. Here's the URL: %s", urlBase));
        }
    }

    /**
     * Sends a POST request with an empty query string.
     * @param url the URL to send the request to
     * @param jsonPostData the JSON data that will make up the body, 
     *                     as a string
     * @return the response, as a string
     */
    public static String post(URL url, String jsonPostData) {
        return post(url, new HashMap<>(), jsonPostData);
    }

    /**
     * Converts a Map into an HTTP query string
     * @param qString the map to convert
     * @return the converted query string
     */
    private static String queryStringify(HashMap<String, String> qString) {
        String retval = "";
        for (Map.Entry<String, String> pair : qString.entrySet()) {
            try {
                retval += URLEncoder.encode(pair.getKey(), "UTF-8") + "=" + URLEncoder.encode(pair.getValue(), "UTF-8");
                retval += "&";
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Apparently Unicode isn't a good encoding");
            }
        }
        // Remove last extra ampersand
        retval = retval.substring(0, retval.length() - 1);
        return retval;
    }

    /**
     * Converts an InputStream to a String. Beware, for large InputStreams, this might
     * negatively impact performance.
     * @param is the InputStream to convert
     * @return the contents of the InputStream as a String
     */
    private static String fromInputStream(InputStream is) {
        Scanner sc = new Scanner(is).useDelimiter("\\A");
        return sc.next();
    }
}
