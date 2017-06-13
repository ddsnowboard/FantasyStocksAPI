package com.jameswk2.FantasyStocksAPI;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
class URLBackend implements NetworkBackend {

    private final URL baseUrl;

    public URLBackend(URL baseUrl) {
        if (!baseUrl.toString().endsWith("/")) {
            try {
                this.baseUrl = new URL(baseUrl.toString() + "/");
            } catch (MalformedURLException e) {
                throw new RuntimeException("May God have mercy on your soul");
            }
        } else {
            this.baseUrl = baseUrl;
        }
    }

    public String get(String address, Map<String, String> queryString) {
        String urlBase = baseUrl.toString() + address;
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

    public String get(String address) {
        return get(address, new HashMap<>());
    }

    public String post(String address, Map<String, String> queryString, String jsonPostData) {
        String urlBase = baseUrl.toString() + address;
        if(!urlBase.endsWith("/"))
            urlBase += "/";
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

    public String post(String address, String jsonPostData) {
        return post(address, new HashMap<>(), jsonPostData);
    }

    /**
     * Converts a Map into an HTTP query string
     *
     * @param qString the map to convert
     * @return the converted query string
     */
    private static String queryStringify(Map<String, String> qString) {
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
     *
     * @param is the InputStream to convert
     * @return the contents of the InputStream as a String
     */
    private static String fromInputStream(InputStream is) {
        Scanner sc = new Scanner(is).useDelimiter("\\A");
        return sc.next();
    }
}
