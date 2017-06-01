package com.jameswk2.FantasyStocksAPI;

import java.util.Date;

import static com.jameswk2.FantasyStocksAPI.FullStock.MODEL_NAME;

public interface Stock {

    /**
     * @return the Stock object with the given id
     * @param id the id of the desired Stock object
     */
    static Stock get(int id) {
        return (Stock) Model.getModel(id, MODEL_NAME, FullStock.class);
    }

    /**
     * @return all the Stock objects in the databse
     */
    static Stock[] getStocks() {
        return (Stock[]) Model.getModel(MODEL_NAME, FullStock.class);
    }

    /**
     * @return the id of this object
     */
    int getId();

    /**
     * @return the company name associated with this object
     */
    String getCompanyName();

    /**
     * This returns the symbol of this stock. In most, but not all, cases, this is unique. 
     * @return the stock symbol associated with this object
     */
    String getSymbol();

    /**
     * @return the Date object describing when this object's price was last updated
     */
    Date getLastUpdated();

    /**
     * @return the price of this stock
     */
    double getPrice();

    /**
     * @return the last change in price of this stock
     */
    double getChange();

    /**
     * @return all the {@link StockSuggestion}s involving this stock
     */
    StockSuggestion[] getStockSuggestions();
}
