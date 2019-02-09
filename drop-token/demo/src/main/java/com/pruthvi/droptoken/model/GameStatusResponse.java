package com.pruthvi.droptoken.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameStatusResponse {
    private List<String> players;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String winner;
    private String state;

    public GameStatusResponse(List<String> players, String winner, String state) {
        this.players = players;
        this.winner = winner;
        this.state = state;
    }

    public GameStatusResponse() {
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
