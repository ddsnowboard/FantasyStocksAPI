package com.jameswk2.FantasyStocksAPI;

import static com.jameswk2.FantasyStocksAPI.FullUser.MODEL_NAME;


/**
 * Created by ddsnowboard on 4/17/17.
 */

/*
So here's my current strategy. I'm going to have a cache in each model interface that will only hold full models.
Then, whenever you ask for a model, unless you're in lazyEvaluation mode, it get it and loads all its fields into
their respective caches.

Although another option would be to dispense with Abbreviated* and Full* altogether and just have everything be a full
model. The abbreviated versions would still exist but they would just be to read the Json and they would immediately be replaced
with real versions. The user would never see partial versions. That would be a whole rewrite, and I'm not even sure
if either of these plans would actually work properly. This is an annoying problem. Maybe I can solve it all in Android.
There has to be a way to have dynamic arrays in Tabviews or whatever. I mean that's ridiculous. Maybe I pass in the
Floor and then the tabView gets the info itself. Hmmm....
 */

public interface User {
    int getId();

    String getUsername();

    Player[] getPlayers();

    static FullUser get(int id) {
        return (FullUser) Model.getModel(id, MODEL_NAME, FullUser.class);
    }

    static User[] getUsers() {
        return (User[]) Model.getModel(MODEL_NAME, FullUser.class);
    }
}
