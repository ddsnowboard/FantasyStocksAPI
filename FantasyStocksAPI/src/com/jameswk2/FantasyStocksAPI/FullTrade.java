package com.jameswk2.FantasyStocksAPI;

import java.util.Date;

class FullTrade implements Trade {
    protected static final String MODEL_NAME = "trade";
    private int id;
    private AbbreviatedPlayer recipientPlayer;
    private AbbreviatedPlayer senderPlayer;
    private AbbreviatedStock[] senderStocks;
    private AbbreviatedStock[] recipientStocks;
    private AbbreviatedFloor floor;
    private Date date;

    public int getId() {
        return id;
    }

    public Player getRecipientPlayer() {
        return recipientPlayer;
    }

    public Player getSenderPlayer() {
        return senderPlayer;
    }

    public Stock[] getSenderStocks() {
        return senderStocks;
    }

    public Stock[] getRecipientStocks() {
        return recipientStocks;
    }

    public Floor getFloor() {
        return floor;
    }

    public Date getDate() {
        return date;
    }

    public static FullTrade get(int id) {
        return (FullTrade) Model.getModel(id, MODEL_NAME, FullUser.class);
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
