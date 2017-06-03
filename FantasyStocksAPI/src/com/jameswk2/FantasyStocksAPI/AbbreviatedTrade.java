package com.jameswk2.FantasyStocksAPI;

import java.util.Arrays;
import java.util.Date;

class AbbreviatedTrade implements Trade {
    private int id;
    private int recipientPlayer;
    private int senderPlayer;
    private int[] senderStocks;
    private int[] recipientStocks;
    private int floor;
    private Date date;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Player getRecipientPlayer() {
        return Player.get(recipientPlayer);
    }

    @Override
    public Player getSenderPlayer() {
        return Player.get(senderPlayer);
    }

    @Override
    public Stock[] getSenderStocks() {
        return Arrays.stream(senderStocks).mapToObj(i -> Stock.get(i)).toArray(i -> new Stock[i]);
    }

    @Override
    public Stock[] getRecipientStocks() {
        return Arrays.stream(recipientStocks).mapToObj(i -> Stock.get(i)).toArray(i -> new Stock[i]);
    }

    @Override
    public Floor getFloor() {
        return Floor.get(floor);
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void accept() {
        TradeUtils.acceptTrade(this);
    }

    @Override
    public void decline() {
        TradeUtils.declineTrade(this);
    }
}
