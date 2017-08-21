package com.jameswk2.FantasyStocksAPI;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * This is a mock network backend
 * Created by ddsnowboard on 6/4/17.
 */
public class MockNetworkBackend implements NetworkBackend {
    private List<GetRequest> getOutputs = new ArrayList<>();
    private List<PostRequest> postOutputs = new ArrayList<>();
    private final Gson gson = FantasyStocksAPI.gson;

    @Override
    public String get(String address, Map<String, String> queryString) {
        System.out.printf("Received get to %s\n", address);
        GetRequest request = getOutputs.stream()
                .filter(r -> r.address.equals(address) && r.queryString.equals(queryString))
                .findFirst()
                .get();
        if(request.getRepeat() == 1)
            getOutputs.remove(request);
        else
            request.setRepeat(request.getRepeat() - 1);
        return gson.toJson(request.response);
    }

    @Override
    public String get(String address) {
        return get(address, new HashMap<>());
    }

    @Override
    public String post(String address, Map<String, String> queryString, String jsonPostData) {
        System.out.printf("Received post to %s\n", address);
        PostRequest request = postOutputs.stream()
                .filter(o -> o.address.equals(address) && gson.fromJson(jsonPostData, JsonObject.class).equals(o.postData) && o.queryString.equals(queryString))
                .findFirst()
                .get();
        if(request.getRepeat() == 1)
            postOutputs.remove(request);
        else
            request.setRepeat(request.getRepeat() - 1);
        return gson.toJson(request.response);
    }

    @Override
    public String post(String address, String jsonPostData) {
        return post(address, new HashMap<>(), jsonPostData);
    }

    public PostRequest expectPost(String address, Map<String, String> queryString, JsonObject jsonObj, JsonElement response) {
        PostRequest request = new PostRequest(address, jsonObj, queryString, response);
        postOutputs.add(request);
        return request;
    }

    public GetRequest expectGet(String address, Map<String, String> queryString, JsonElement response) {
        GetRequest request = new GetRequest(address, queryString, response);
        getOutputs.add(request);
        return request;
    }

    public void validateExpectations() {
        if (getOutputs.stream().anyMatch(o -> o.getRepeat() > 0))
            throw new RuntimeException("All the specified GETs weren't called");
        else if (postOutputs.stream().anyMatch(o -> o.getRepeat() > 0))
            throw new RuntimeException("All the specified POSTs weren't called");
    }


    static abstract class ExpectedRequest {
        String address;
        Map<String, String> queryString;
        JsonElement response;

        private int repeat = 1;

        public ExpectedRequest(String address, Map<String, String> queryString, JsonElement response) {
            this.address = address;
            this.queryString = queryString;
            this.response = response;
        }

        public void setRepeat(boolean repeat) {
            if(repeat)
                this.repeat = -1;
            else
                this.repeat = 1;
        }

        public void setRepeat(int repeat) {
            this.repeat = repeat;
        }

        public int getRepeat() {
            return this.repeat;
        }
    }

    static class PostRequest extends ExpectedRequest {
        JsonElement postData;

        public PostRequest(String address, JsonObject postData, Map<String, String> queryString, JsonElement response) {
            super(address, queryString, response);
            this.postData = postData;
        }
    }

    static class GetRequest extends ExpectedRequest {
        public GetRequest(String address, Map<String, String> queryString, JsonElement response) {
            super(address, queryString, response);
        }

    }
}

