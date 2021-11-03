package sample;

import java.util.Arrays;

public class Board {
    private char[][] board = new char[6][7];

    public Board(){
        for (int i = 0; i<6; i++){
            for(int j = 0; j<7; j++){
                board[i][j]='e';
            }
        }
    }

    public boolean addDisk(int col, char color){
        for (int i = 5; i>=0; i--){
            if(board[i][col]==Constants.EMPTY){
                board[i][col]=color;
                System.out.println(Arrays.deepToString(board));
                return true;
            }
        }
        return false;
    }

    public int getLastRow(int col){
        for (int i = 5; i>=0; i--){
            if(board[i][col]==Constants.EMPTY){
                return i;
            }
        }
        return Constants.ERROR;
    }

    public boolean check4(){
        //boolean win = false;
        for (int row=0; row<6; row++) {
            for (int col=0; col<7-3; col++) {
                char curr = board[row][col];
                if (curr!=Constants.EMPTY
                        && curr == board[row][col+1]
                        && curr == board[row][col+2]
                        && curr == board[row][col+3]) {
                    return true;
                }
            }
        }
        // vertical columns
        for (int col=0; col<7; col++) {
            for (int row=0; row<6-3; row++) {
                char curr = board[row][col];
                if (curr!=Constants.EMPTY
                        && curr == board[row+1][col]
                        && curr == board[row+2][col]
                        && curr == board[row+3][col])
                    return true;
            }
        }
        // diagonal lower left to upper right
        for (int row=0; row<6-3; row++) {
            for (int col=0; col<7-3; col++) {
                char curr = board[row][col];
                if (curr!=Constants.EMPTY
                        && curr == board[row+1][col+1]
                        && curr == board[row+2][col+2]
                        && curr == board[row+3][col+3])
                    return true;
            }
        }
        // diagonal upper left to lower right
        for (int row=6-1; row>=3; row--) {
            for (int col=0; col<7-3; col++) {
                char curr = board[row][col];
                if (curr!=Constants.EMPTY
                        && curr == board[row-1][col+1]
                        && curr == board[row-2][col+2]
                        && curr == board[row-3][col+3])
                    return true;
            }
        }
        return false;
    }


}
