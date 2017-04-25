package com.jameswk2.FantasyStocksAPI;

import static com.jameswk2.FantasyStocksAPI.FullFloor.MODEL_NAME;

/**
 * Created by ddsnowboard on 4/17/17.
 */
public interface Floor {
    static Floor get(int id) {
        return (FullFloor) Model.getModel(id, MODEL_NAME, FullFloor.class);
    }

    int getId();

    String getName();

    Stock[] getStocks();

    FullFloor.Permissiveness getPermissiveness();

    User getOwner();

    Player getFloorPlayer();

    boolean isPublic();

    int getNumStocks();

    static Floor[] getFloors() {
        return (Floor[]) Model.getModel(MODEL_NAME, FullFloor.class);
    }
}
