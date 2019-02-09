package com.pruthvi.droptoken.model;


import org.springframework.stereotype.Component;
import java.lang.String;

@Component
public class CreateGameResponse {

    private String gameId;

    public CreateGameResponse() {
    }

    public CreateGameResponse(String gameId) {
        this.gameId = gameId;
    }
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }



}
