package com.jameswk2.FantasyStocksAPI;

import java.util.Date;

import static com.jameswk2.FantasyStocksAPI.FullStock.MODEL_NAME;

/**
 * Created by ddsnowboard on 4/17/17.
 */
public interface Stock {

    static Stock get(int id) {
        return (Stock) Model.getModel(id, MODEL_NAME, FullStock.class);
    }
    static Stock[] getStocks() {
        return (Stock[]) Model.getModel(MODEL_NAME, FullStock.class);
    }

    int getId();

    String getCompanyName();

    String getSymbol();

    Date getLastUpdated();

    double getPrice();;

    double getChange();

    StockSuggestion[] getStockSuggestions();

}
