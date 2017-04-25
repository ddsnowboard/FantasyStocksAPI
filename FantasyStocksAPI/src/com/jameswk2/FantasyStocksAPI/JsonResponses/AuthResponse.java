package com.jameswk2.FantasyStocksAPI.JsonResponses;

import com.jameswk2.FantasyStocksAPI.FullUser;

/**
 * Created by ddsnowboard on 4/17/17.
 */
public class AuthResponse {
    private String sessionId;
    private FullUser user;

    public String getSessionId() {
        return sessionId;
    }

    public FullUser getUser() {
        return user;
    }
}
