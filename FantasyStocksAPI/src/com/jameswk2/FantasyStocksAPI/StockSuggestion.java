package com.jameswk2.FantasyStocksAPI;

import java.util.Date;

/**
 * This is what is created when a user wants to add a stock
 * to a permissive {@link Floor}. The owner of the {@link Floor}
 * is notified of the user's intent and can accept of deny this
 * suggestion.
 */
public interface StockSuggestion {
    /**
     * @return the integer id of this object
     */
    int getId();

    /**
     * @return the {@link Stock} that this object is suggesting
     */
    Stock getStock();

    /**
     * @return the {@link Player} who made this suggestion
     */
    Player getRequstingPlayer();

    /**
     * @return the {@link Floor} this suggestion is on
     */
    Floor getFloor();

    /**
     * @return the date and time this object was made
     */
    Date getDate();
}
