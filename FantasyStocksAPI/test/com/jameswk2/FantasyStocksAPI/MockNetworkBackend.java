package com.jameswk2.FantasyStocksAPI;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * This is a mock network backend
 * Created by ddsnowboard on 6/4/17.
 */
public class MockNetworkBackend implements NetworkBackend {
    private List<GetRequest> getOutputs = new ArrayList<>();
    private List<PostRequest> postOutputs = new ArrayList<>();

    @Override
    public String get(String address, Map<String, String> queryString) {
        return null;
    }

    @Override
    public String get(String address) {
        return null;
    }

    @Override
    public String post(String address, Map<String, String> queryString, String jsonPostData) {
        return null;
    }

    @Override
    public String post(String address, String jsonPostData) {
        return null;
    }

    public void expectPost(String address, String queryString, JsonObject jsonObj, JsonObject response) {
        PostRequest request = new PostRequest(address, jsonObj, queryString, response);
        postOutputs.add(request);
    }

    public void expectGet(String address, String queryString, JsonObject response) {
        GetRequest request = new GetRequest(address, queryString, response);
        getOutputs.add(request);
    }





    private static class PostRequest {
        String address;
        JsonObject postData;
        String queryString;
        JsonObject response;

        public PostRequest(String address, JsonObject postData, String queryString, JsonObject response) {
            this.address = address;
            this.postData = postData;
            this.queryString = queryString;
            this.response = response;
        }
    }

    private static class GetRequest {
        String address;
        String queryString;
        JsonObject response;

        public GetRequest(String address, String queryString, JsonObject response) {
            this.address = address;
            this.queryString = queryString;
            this.response = response;
        }
    }
}

