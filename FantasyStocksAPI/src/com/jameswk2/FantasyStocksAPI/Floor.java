package com.jameswk2.FantasyStocksAPI;

import static com.jameswk2.FantasyStocksAPI.FullFloor.MODEL_NAME;

/**
 * This represents a group of {@link Player} objects and {@link Stock}s.
 * Each {@link Player} belongs to a single one of these, but a {@link Stock}
 * can belong to multiple.
 */
public interface Floor {

    /**
     * @param id the integral id associated with the desired {@link Floor} object
     * @return the floor assocaited with the given id
     */
    static Floor get(int id) {
        return (FullFloor) Model.getModel(id, MODEL_NAME, FullFloor.class);
    }

    /**
     * @return the integer id associated with this object
     */
    int getId();

    /**
     * @return the name of this {@link Floor} object
     */
    String getName();

    /**
     * @return an array of the {@link Stock} objects on this {@link Floor}
     */
    Stock[] getStocks();

    /**
     * @return a {@link FullFloor.Permissiveness} corresponding to the permissiveness of this object
     */
    FullFloor.Permissiveness getPermissiveness();

    /**
     * @return a {@link User} object representing the owner of this object
     */
    User getOwner();

    /**
     * Returns the floor player assigned to this floor. The floor player is the virtual player that
     * holds all the stocks that no other player on the Floor is holding. He does have a score in the 
     * database, but it is meaningless, and whether or not it is returned through this API is undefined.
     * @return a {@link Player} object representing the floor player on this {@link Floor}
     */
    Player getFloorPlayer();

    /**
     * @return whether the floor is public or not
     */
    boolean isPublic();

    /**
     * @return the number of stocks available on this floor
     */
    int getNumStocks();

    /**
     * Returns all the floors in the database, subject to certain constraints,
     * specifically that the floor is either public or the user is a member.
     * @return all the {@link Floor}s in the database that are available to the user
     */
     static Floor[] getFloors() {
     return (Floor[]) Model.getModel(MODEL_NAME, FullFloor.class);
     }
}
