package sample;

import java.util.ArrayList;
import java.util.Arrays;

public class Board implements Cloneable{
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

    public boolean checkLegalMove(int col){
        if(board[0][col]==Constants.EMPTY) return true;
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

    public Board clone(){
        Board cloneBoard = new Board();
        for (int i = 0; i<6; i++){
            for(int j = 0; j<7; j++){
                cloneBoard.board[i][j]=this.board[i][j];
            }
        }
        return cloneBoard;
    }

    public boolean isFull(){
        for(char c : board[0]){
            if(c==Constants.EMPTY){
                return false;
            }
        }
        return true;
    }

    public int checkForStreak(int col, int numStreak, char color){
        int count = 0;
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
                if (board[i][j]== color){
                    count += this.verticalStreak(i,j,numStreak);
                    count += this.horizontalStreak(i,j,numStreak);
                    count += this.diagonalStreak(i,j,numStreak);
                }
            }
        }

        return count;
    }

    private int verticalStreak(int row, int col, int numStreak){
        int consecutiveCount = 0;
        for (int i=row; i>=0; i--){
            if (board[i][col] == board[row][col]) {
                consecutiveCount += 1;
            }else {
                break;
            }
        }
        if (consecutiveCount >= numStreak){
            return 1;
        }else {
            return 0;
        }
    }

    private int horizontalStreak(int row, int col, int numStreak){
        int consecutiveCount = 0;
        for (int j=col; j<7; j++){
            if (board[row][j] == board[row][col]) {
                consecutiveCount += 1;
            }else {
                break;
            }
        }
        if (consecutiveCount >= numStreak){
            return 1;
        }else {
            return 0;
        }
    }

    private int diagonalStreak(int row, int col, int numStreak){
        int total =0;
        int count =0;
        int j = col;
        for (int i=row; i>=0; i--){
            if(j>6) break;
            else if(board[i][j] == board[row][col]) count=count+1;
            else break;
            j= j+1;
        }
        if(count>=numStreak) total=total+1;

        count=0;
        j=col;
        for (int i=row; i<6; i++){
            if(j>6) break;
            else if(board[i][j] == board[row][col]) count=count+1;
            else break;
            j= j+1;
        }
        if(count>=numStreak) total=total+1;

        return total;
    }

    public char[] getHorizontalWindow(int col, int row){
        char[] res = new char[4];
        for(int i=0; i<4; i++){
            res[i] = board[row][col];
            col++;
        }
        return res;
    }

    public char[] getVerticalWindow(int col, int row){
        char[] res = new char[4];
        for(int i=0; i<4; i++){
            res[i] = board[row][col];
            row--;
        }
        return res;
    }

    public char[] getPositiveDiagonalWindow(int col, int row){
        char[] res = new char[4];
        for(int i=0; i<4; i++){
            res[i] = board[row][col];
            row--;
            col++;
        }
        return res;
    }

    public char[] getNegativeDiagonalWindow(int col, int row){
        char[] res = new char[4];
        for(int i=0; i<4; i++){
            res[i] = board[row][col];
            row++;
            col++;
        }
        return res;
    }

    public char[] getColumn(int colToGet){
        char[] colArray=new char[6];
        int i=0;
        for(int row=5;row>=0;row--){
            colArray[i]=board[row][colToGet];
            i++;
        }
        return colArray;
    }

    /*
    public int countHorizontal(int col, int row, char color){
        ArrayList<Integer> indexes = new ArrayList();
        int maxSequence = 0;

    }

    public ArrayList<Integer> createValidArray(int col){
        ArrayList<Integer> indexes = new ArrayList();
        for(int i = col-3; i<=col+3; i++){
            if(i>=0 && i<7) {
                indexes.add(i);
            }
        }
        return indexes;
    }*/
}
