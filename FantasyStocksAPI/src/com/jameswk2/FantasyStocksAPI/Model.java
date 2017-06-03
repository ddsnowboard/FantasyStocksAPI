package com.jameswk2.FantasyStocksAPI;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This is the class that deals with models and abstracts 
 * away most of the logic for interacting with the API. 
 * Created by ddsnowboard on 4/17/17.
 */
class Model {
    /**
     * @return the instance of the given model class with the given id. 
     *         It will need to be cast to the proper class after it is returned.
     * @param id the id in the database of the desired object
     * @param modelName the name of the model class. Each full model class
     *                  has a MODEL_NAME constant variable that contains the 
     *                  peoper model name. You <i>must</i> use this.
     * @param modelClass the class of the model that you want to be returned. 
     *                   Usually the full version of the model.
     * @throws RuntimeException if there is an error getting the data from the server. 
     *                          See the message for detais.
     */
    static Object getModel(int id, String modelName, Class modelClass) {
        final String url = FantasyStocksAPI.BASE_URL + modelName + "/view/" + id;
        try {
            URL urlObject = new URL(url);
            String response = URLUtils.get(urlObject);
            Gson gson = new Gson();
            JsonElement retval = gson.fromJson(response, JsonElement.class);
            if(!retval.isJsonObject())
                throw new RuntimeException("The json wasn't what I expected.");
            JsonObject jsonObj = retval.getAsJsonObject();
            if(jsonObj.get("error") == null)
                return gson.fromJson(jsonObj, modelClass);
            else
                throw new RuntimeException(jsonObj.get("error").getAsString());
        } catch (MalformedURLException e) {
            throw new RuntimeException("I made a mistake in the url");
        }
    }

    /**
     * @return all the objects in the database that the logged in user
     *         can access of the given model
     * @param modelName the name of the model class. Each full model class
     *                  has a MODEL_NAME constant variable that contains the 
     *                  peoper model name. You <i>must</i> use this.
     * @param modelClass the class of the model that you want to be returned. 
     *                   Usually the full version of the model.
     * @throws RuntimeException if there is an error getting the data from the server. 
     *                          See the message for detais.
     */
    static Object[] getModel(String modelName, Class modelClass) {
        final String url = FantasyStocksAPI.BASE_URL + modelName + "/view/";
        try {
            URL urlObject = new URL(url);
            String response = URLUtils.get(urlObject);
            Gson gson = new Gson();
            JsonElement jsonObj = gson.fromJson(response, JsonElement.class);
            if(!jsonObj.isJsonArray())
                throw new RuntimeException(String.format("The json wasn't what I expected. Here it is: %s", jsonObj.toString()));
            JsonArray jsonArray = jsonObj.getAsJsonArray();

            Object[] retval = (Object[]) Array.newInstance(modelClass, jsonArray.size());
            for(int i = 0; i < jsonArray.size(); i++) {
                retval[i] = gson.fromJson(jsonArray.get(i), modelClass);
            }
            return retval;
        } catch (MalformedURLException e) {
            throw new RuntimeException("I made a mistake in the url");
        }
    }
}
