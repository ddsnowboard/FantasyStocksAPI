package com.jameswk2.FantasyStocksAPI.JsonResponses;

import com.jameswk2.FantasyStocksAPI.FullUser;

/**
 * This is just a data holder class for API responses involving authentication.
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
