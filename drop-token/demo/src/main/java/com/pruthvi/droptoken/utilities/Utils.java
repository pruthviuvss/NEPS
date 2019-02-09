package com.pruthvi.droptoken.utilities;

public class Utils {



    public String checkGameStatus(int[][] board){
        final int height = board.length-1;
        final int width = board[0].length-1;
        final int EMPTY_SLOT = 0;
        int count=0;
        for(int row = height; row >= 0; row-- ){
            for(int column = 0; column <= width; column++){
                if(board[row][column] == EMPTY_SLOT) continue;
                if(board[row][column] != EMPTY_SLOT){
                    count++;
                }
                if(row == height){

                    if(column == 0){
                        if(board[row][column]==board[row-1][column+1] && board[row][column]==board[row-2][column+2] && board[row-3][column]==board[row][column+3]) return "WON";
                    }
                    if(board[row][column]==board[row-1][column] && board[row][column]==board[row-2][column] && board[row][column]==board[row-3][column]) return "WON";
                }
                if(column == 0){
                    if(row == 0){
                        if(board[row][column]==board[row+1][column+1] && board[row][column]==board[row+2][column+2] && board[row][column]==board[row+3][column+3]) return "WON";
                    }
                    if(board[row][column]==board[row][column+1] && board[row][column]==board[row][column+2] && board[row][column]==board[row][column+3]) return "WON";
                }

            }
        }
        System.out.println(count);
        if(count == 16)  return "DRAW";
        return "IN PROGRESS";
    }


    public boolean rangeCheck(Integer original, Integer expected){
        if(original != expected) return false;
        return true;
    }
}

