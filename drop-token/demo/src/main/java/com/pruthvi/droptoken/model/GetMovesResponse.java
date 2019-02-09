package com.pruthvi.droptoken.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetMovesResponse {
    List<GetMoveResponse> moves;

    @Override
    public String toString() {
        return "GetMovesResponse{" +
                "moves=" + moves +
                '}';
    }

    public GetMovesResponse() {
    }

    public GetMovesResponse(List<GetMoveResponse> moves) {
        this.moves = moves;
    }

    public List<GetMoveResponse> getMoves() {
        return moves;
    }

    public void setMoves(List<GetMoveResponse> moves) {
        this.moves = moves;
    }
}
