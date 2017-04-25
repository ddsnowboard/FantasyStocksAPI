package com.jameswk2.FantasyStocksAPI;

import java.util.Date;

/**
 * Created by ddsnowboard on 4/17/17.
 */
public interface StockSuggestion {
    int getId();

    Stock getStock();

    Player getRequstingPlayer();

    Floor getFloor();

    Date getDate();
}
