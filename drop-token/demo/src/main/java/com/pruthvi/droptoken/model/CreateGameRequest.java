package com.pruthvi.droptoken.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

@Component
public class CreateGameRequest {
    private List<String> players;
    private Integer columns;
    private Integer rows;

    public CreateGameRequest(List<String> players, Integer columns, Integer rows) {
        this.players = players;
        this.columns = columns;
        this.rows = rows;
    }

    public CreateGameRequest() {
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public Integer getColumns() {
        return columns;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
