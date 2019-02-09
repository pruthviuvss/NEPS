package com.pruthvi.droptoken.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetGamesResponse {

    private List<String> gameIds;

    public GetGamesResponse() {
    }

    public GetGamesResponse(List<String> gameIds) {
        this.gameIds = gameIds;
    }

    public List<String> getGameIds() {
        return gameIds;
    }

    public void setGameIds(List<String> gameIds) {
        this.gameIds = gameIds;
    }
}
