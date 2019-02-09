package com.pruthvi.droptoken.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.lang.String;


@Component
public class Game {

    @Id
    private String gameID;
    private String player1;
    private String player2;
    private Integer columns;
    private Integer rows;
    private String currentPlayerTurn;
    private List<Integer> moves;
    private int[][] board;
    private String status;
    private String winner;

    public Game(String gameID, String player1, String player2, Integer columns, Integer rows, String currentPlayerTurn, List<Integer> moves, int[][] board, String status, String winner) {
        this.gameID = gameID;
        this.player1 = player1;
        this.player2 = player2;
        this.columns = columns;
        this.rows = rows;
        this.currentPlayerTurn = currentPlayerTurn;
        this.moves = moves;
        this.board = board;
        this.status = status;
        this.winner = winner;
    }

    public Game() {
    }



    @Override
    public String toString() {
        return "Game{" +
                "gameID='" + gameID + '\'' +
                ", player1='" + player1 + '\'' +
                ", player2='" + player2 + '\'' +
                ", columns=" + columns +
                ", rows=" + rows +
                ", currentPlayerTurn='" + currentPlayerTurn + '\'' +
                ", moves=" + moves +
                ", board=" + Arrays.toString(board) +
                ", status='" + status + '\'' +
                ", winner='" + winner + '\'' +
                '}';
    }


    public String getCurrentPlayerTurn() {
            return currentPlayerTurn;
        }
        public void setCurrentPlayerTurn(String currentPlayerTurn) {
            this.currentPlayerTurn = currentPlayerTurn;
        }
    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
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

    public List<Integer> getMoves() {
        return moves;
    }

    public void setMoves(List<Integer> moves) {
        this.moves = moves;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

}
