package com.jameswk2.FantasyStocksAPI;

import java.util.Date;

public interface StockSuggestion {
    int getId();

    Stock getStock();

    Player getRequstingPlayer();

    Floor getFloor();

    Date getDate();
}
