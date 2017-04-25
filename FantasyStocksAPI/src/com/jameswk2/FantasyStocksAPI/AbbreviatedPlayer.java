package com.jameswk2.FantasyStocksAPI;

import java.util.Arrays;

/**
 * Created by ddsnowboard on 4/17/17.
 */
public class AbbreviatedPlayer implements Player {
    private int id;
    private int user;
    private int floor;
    private int[] stocks;
    private int points;
    private boolean isFloor;
    private int[] sentTrades;
    private int[] receivedTrades;
    private boolean isFloorOwner;

    @Override
    public int getId() {
        return id;
    }

    public FullUser getUser() {
        return User.get(user);
    }

    public Floor getFloor() {
        return Floor.get(floor);
    }

    public Stock[] getStocks() {
        return Arrays.stream(stocks).mapToObj(i -> Stock.get(i)).toArray(i -> new Stock[i]);
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public boolean isFloor() {
        return isFloor;
    }

    public FullTrade[] getSentTrades() {
        return Arrays.stream(sentTrades).mapToObj(i -> FullTrade.get(i)).toArray(i -> new FullTrade[i]);
    }

    public FullTrade[] getReceivedTrades() {
        return Arrays.stream(receivedTrades).mapToObj(i -> FullTrade.get(i)).toArray(i -> new FullTrade[i]);
    }

    @Override
    public boolean isFloorOwner() {
        return isFloorOwner;
    }


}
