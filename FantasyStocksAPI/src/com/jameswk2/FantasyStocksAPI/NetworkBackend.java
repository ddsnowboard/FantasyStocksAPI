package com.jameswk2.FantasyStocksAPI;

import java.util.Map;

/**
 * This is the interface used for interacting with the network.
 * It abstracts away all the string interpolation for URL's and things.
 * Using this interface allows me to do dependency injection
 * Created by ddsnowboard on 6/3/17.
 */
public interface NetworkBackend {
    /**
     * Sends a GET request.
     *
     * @param address     the address after the TLD (eg, www.reddit.com<em>/r/programming</em>)
     * @param queryString a map of keys and values that will be converted into
     *                    an HTTP query string
     * @return the response, as a string
     */
    String get(String address, Map<String, String> queryString);

    /**
     * Sends a GET request with an empty query string.
     *
     * @param address the address after the TLD (eg, www.reddit.com<em>/r/programming</em>)
     * @return the response, as a string
     */
    String get(String address);


    /**
     * Sends a POST request.
     *
     * @param address the address after the TLD (eg, www.reddit.com<em>/r/programming</em>)
     * @param queryString  a map of keys and values that will be converted into
     *                     an HTTP query string
     * @param jsonPostData the JSON data that will make up the body,
     *                     as a string
     * @return the response, as a string
     */
    String post(String address, Map<String, String> queryString, String jsonPostData);

    /**
     * Sends a POST request with an empty query string.
     *
     * @param address the address after the TLD (eg, www.reddit.com<em>/r/programming</em>)
     * @param jsonPostData the JSON data that will make up the body,
     *                     as a string
     * @return the response, as a string
     */
    String post(String address, String jsonPostData);
}