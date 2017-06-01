package com.jameswk2.FantasyStocksAPI;

import java.util.Date;

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
