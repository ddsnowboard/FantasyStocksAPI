package com.jameswk2.FantasyStocksAPI;

import java.util.Arrays;
import java.util.HashMap;

import static com.jameswk2.FantasyStocksAPI.FullUser.MODEL_NAME;

/**
 * Created by ddsnowboard on 4/17/17.
 */

***********************
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
    static HashMap<Integer, FullUser> cache = new HashMap<>();

    int getId();

    String getUsername();

    Player[] getPlayers();

    FullUser getInFull();

    static FullUser get(int id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        } else {
            FullUser retval = (FullUser) Model.getModel(id, MODEL_NAME, FullUser.class);
            cache.put(id, retval);
            if (!FantasyStocksAPI.getInstance().lazyEvaluation) {
                for(Player p : retval.getPlayers()) {
                    // This will fill in the cache so we don't have to go looking
                    // for stuff on the network later.
                    Player.get(p.getId());
                }
            }

            return retval;
        }
    }

    static User[] getUsers() {
        User[] retval = (User[]) Model.getModel(MODEL_NAME, FullUser.class);
        if (!FantasyStocksAPI.getInstance().lazyEvaluation)
            retval = Arrays.stream(retval).map(u -> u.getInFull()).toArray(i -> new User[i]);

        return retval;
    }
}
