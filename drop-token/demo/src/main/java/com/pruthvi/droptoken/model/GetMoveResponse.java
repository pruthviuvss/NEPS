package com.pruthvi.droptoken.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

@Component
public class GetMoveResponse {
    private String type;
    private String player;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer column;

    @Override
    public String toString() {
        return "GetMoveResponse{" +
                "type='" + type + '\'' +
                ", player='" + player + '\'' +
                ", column=" + column +
                '}';
    }

    public GetMoveResponse() {
    }

    public GetMoveResponse(String type, String player, Integer column) {
        this.type = type;
        this.player = player;
        this.column = column;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
}
