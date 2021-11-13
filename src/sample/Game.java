package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game {

    public Board board= new Board();
    public int yellowMoves=0;
    public int redMoves=0;

    public char actualPlayer=Constants.RED;
    public boolean inGame= false;
    public int gamemode = Constants.PVP;

    private static Game actualGame = new Game();

    private Game(){
        //!
    }

    private Game(Game toCopy){
        board = toCopy.board.clone();
        yellowMoves = toCopy.yellowMoves;
        redMoves = toCopy.redMoves;
        actualPlayer = toCopy.actualPlayer;
        inGame = toCopy.inGame;
        gamemode = toCopy.gamemode;

        actualGame = null;
    }

    public static Game getActualGame() {
        return actualGame;
    }

    public int parseNextMove(){
        int move = Constants.ERROR;
        if(gamemode==Constants.PVRandom){
            move = randomMove();
        }else if(gamemode == Constants.PVMinimax){
            move = minimaxMove();
        }
        return move;
    }

    private int randomMove(){
        return (int)Math.floor(Math.random() * (6) + 1);
    }

    private int minimaxMove(){
        int [] valueArray = new int[7];

        for (int col = 0; col < 7; col++) {
            valueArray[col]=-100000000;
            if(board.checkLegalMove(col)) {
                //System.out.println("Ho inserito nella colonna"+col);
                Game gameCopy = new Game(this);
                //gameCopy.makeFakeMove(col);
                valueArray[col] = gameCopy.maximize(col,5);
         }
        }
        System.out.println(Arrays.toString(valueArray));

        return findMaxIndex(valueArray);
    }

    private int maximize (int col, int depth){
        makeFakeMove(col);
        //terminal state
        if(board.check4()){
            if(actualPlayer == Constants.RED) return 10000000; //AI (YELLOW) just made the move
            if(actualPlayer == Constants.YELLOW) return -10000000; //player (RED) made the move
        }
        if(board.isFull()) return 0;
        if(depth==0){
            actualPlayer=Constants.RED;
            return evalFunction();
        }

        int value = -100000000; //uno zero in piu di sopra
        for (int c= 0; c<7; c++){
            if(board.checkLegalMove(c)) {
                //System.out.println("Ho inserito nella colonna"+c);
                Game gameCopy = new Game(this);
                value = Math.max(value, gameCopy.minimize(c,depth-1));
                //System.out.println("Risultato del maximize per questa colonna: " +value);
            }
        }
        return value;
    }
    /*
    private int maximize (int col, int depth){

        makeMove(col);
        if(board.check4()) return 99999;
        if(depth==0){
            return evalFunction(col);
        }

        int value = -9999;
        for (int childCol = 0; childCol < 7; childCol++){
            // if(board.checkLegalMove(col)) {
            Game gameCopyChild = new Game(this);
            System.out.println("arrivo nel maximize" + childCol);
            value = Math.max(value, gameCopyChild.minimize(childCol, depth-1));
            System.out.println("finisco maximize" + childCol);
            //}else {
            //   value = -100;
            //}
        }
        return value;
    }*/


    private int minimize (int col, int depth){

        makeFakeMove(col);
        //terminal state
        if(board.check4()){
            if(actualPlayer == Constants.RED) return 10000000; //AI (YELLOW) just made the move
            if(actualPlayer == Constants.YELLOW) return -10000000; //player (RED) made the move
        }
        if(board.isFull()) return 0;

        if(depth==0){
            actualPlayer=Constants.RED;
            return evalFunction();
        }

        int value = 100000000; //uno zero in piu di sopra
        for (int c= 0; c<7; c++){
            if(board.checkLegalMove(c)) {
                Game gameCopy = new Game(this);
                value = Math.min(value, gameCopy.maximize(c,depth-1));
                //System.out.println("Risultato del minimize per questa colonna: " +value);
            }
        }
        return value;
    }

    /*
    SE nodo è un nodo terminale OPPURE profondità = 0
        return il valore euristico del nodo
    SE l'avversario deve giocare
        α := +∞
        PER OGNI figlio di nodo
            α := min(α, minimax(figlio, profondità-1))
    ALTRIMENTI dobbiamo giocare noi
        α := -∞
        PER OGNI figlio di nodo
            α := max(α, minimax(figlio, profondità-1))
    return α
     */

    public char getOtherPlayer(){
        if(actualPlayer == Constants.YELLOW) return Constants.RED;
        else return Constants.YELLOW;
    }

    public int countCharArray(char[] array, char toCount){
        int count=0;
        for (int i=0; i<array.length; i++){
            if(array[i]==toCount) count+=1;
        }
        //System.out.println((count));
        return count;
    }

    private int evalFunction() {

        //char currentPlayer = getOtherPlayer();
        char currentPlayer = Constants.YELLOW;
        int res = 0;

        //center column evaluation
      //  System.out.println("Sezione della colonna centrale");
        char[] centerCol = board.getColumn(3);
       // System.out.print(Arrays.toString(centerCol));
        res+=countCharArray(centerCol,currentPlayer)*3;
      //  System.out.println(countCharArray(centerCol,currentPlayer));
      //  System.out.println("Risultato attuale: "+res);


      //  System.out.println("Sezione della valutazione orizzontale");
        //horizontal eval
        for (int row = 5; row >= 0; row--) {
            for (int col = 0; col < 7 - 3; col++) {
                char[] window = board.getHorizontalWindow(col, row);
               // System.out.print(Arrays.toString(window));
                res+= evaluateWindow(window,currentPlayer);
              //  System.out.println(evaluateWindow(window,currentPlayer));
             //   System.out.println("Risultato attuale: "+res);
            }
        }

       // System.out.println("Sezione della valutazione verticale");
        //vertical eval
        for (int col = 0; col < 7; col++) {
            for (int row = 5; row >= 6 - 3; row--) {
                char[] window = board.getVerticalWindow(col, row);
              //  System.out.print(Arrays.toString(window));
                res+= evaluateWindow(window,currentPlayer);
                //System.out.println(evaluateWindow(window,currentPlayer));
                //System.out.println("Risultato attuale: "+res);
            }
        }

     //   System.out.println("Sezione della diagonale verso destra");
        //positive sloped diagonal
        for (int row = 5; row >= 6 - 3; row--) {
            for (int col = 0; col < 7 - 3; col++) {
                char[] window = board.getPositiveDiagonalWindow(col, row);
              //  System.out.print(Arrays.toString(window));
                res+= evaluateWindow(window,currentPlayer);
                //System.out.println(evaluateWindow(window,currentPlayer));
                //System.out.println("Risultato attuale: "+res);
            }
        }

        //System.out.println("Sezione della diagonale verso sinistra");
        for (int row = 5; row >= 6 - 3; row--) {
            for (int col = 0; col < 7 - 3; col++) {
                char[] window = board.getNegativeDiagonalWindow(col, row - 3);
            //    System.out.print(Arrays.toString(window));
                res+= evaluateWindow(window,currentPlayer);
              //  System.out.println(evaluateWindow(window,currentPlayer));
               // System.out.println("Risultato attuale: "+res);
            }
        }



        return res;
    }
    private int evaluateWindow(char[] window, char currentPlayer){
        int res=0;
        char opponent = Constants.RED;
        //char opponent = actualPlayer;

        if(countCharArray(window, currentPlayer) == 4){
            res += 100;
        }else if(countCharArray(window, currentPlayer) == 3 && countCharArray(window, Constants.EMPTY) == 1){
            res += 10;
        }else if(countCharArray(window, currentPlayer) == 2 && countCharArray(window, Constants.EMPTY) == 2){
            res += 4;
        }

        if((countCharArray(window, opponent) == 3) && countCharArray(window, Constants.EMPTY) == 1){
            res -= 80;
        }
        if((countCharArray(window, opponent) == 2) && countCharArray(window, Constants.EMPTY) == 2){
            res -= 7;
        }


        return res;
    }


        /*
            //int value=0;
            char opponent = Constants.YELLOW;
            if (actualPlayer == Constants.YELLOW) opponent = Constants.RED;

            int playingFours = board.checkForStreak(col, 4, opponent);
            int playingThrees = board.checkForStreak(col, 3, opponent);
            int playingTwos = board.checkForStreak(col, 2, opponent);
            int opponentFours = board.checkForStreak(col, 4, actualPlayer);
            int opponentThrees = board.checkForStreak(col, 3, actualPlayer);
            int opponentTwos = board.checkForStreak(col, 2, actualPlayer);

            int res = (playingFours * 10 + playingThrees * 5 + playingTwos * 2) - (opponentFours * 10 + opponentThrees * +opponentTwos * 2);

            return res;

       /* if(res!=0) return res;
        else {
            try{
                int[][] evaluationTable =  {{3, 4, 5, 7, 5, 4, 3},
                        {4, 6, 8, 10, 8, 6, 4},
                        {5, 8, 11, 13, 11, 8, 5},
                        {5, 8, 11, 13, 11, 8, 5},
                        {4, 6, 8, 10, 8, 6, 4},
                        {3, 4, 5, 7, 5, 4, 3}};

                return evaluationTable[board.getLastRow(col)][col];
            }catch(ArrayIndexOutOfBoundsException e){
                return 0;
            }

        }
/*
        if self.current_move == 1:
            o_color = 2
        elif self.current_move == 2:
            o_color = 1
        my_fours = self.checkForStreak(state, self.current_move, 4)
        my_threes = self.checkForStreak(state, self.current_move, 3)
        my_twos = self.checkForStreak(state, self.current_move, 2)
        comp_fours = self.checkForStreak(state, o_color, 4)
        comp_threes = self.checkForStreak(state, o_color, 3)
        comp_twos = self.checkForStreak(state, o_color, 2)
        return (my_fours * 10 + my_threes * 5 + my_twos * 2) - (comp_fours * 10 + comp_threes * 5 + comp_twos * 2)

    */



    private int findMaxIndex(int[] arr) {
        ArrayList<Integer> maxIndexes = new ArrayList<>();

        int max=arr[0];
        for(int i=1; i<arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        for(int i=0; i<arr.length; i++) {
            if (arr[i] == max) {
                maxIndexes.add(i);
            }
        }

        if(maxIndexes.size()==1) return maxIndexes.get(0);
        else{
            while(true) {
                Random rand = new Random();
                int move = maxIndexes.get(rand.nextInt(maxIndexes.size()));
                if (board.checkLegalMove(move)) return move;
            }
        }
    }

    /*
    private boolean makeFakeMove(int col){
        boolean correctMove = board.addDisk(col, actualPlayer);
        if(correctMove){
            incrementMoves();
            switchTurn();
        }
        if(board.check4()){
            restart();
        }
        if(board.isFull()){
            restart();
        }
        return correctMove;
    }*/

    public boolean makeMove(int col){
        if(!inGame) inGame=true;
        boolean correctMove = board.addDisk(col, actualPlayer);
        if(correctMove){
            incrementMoves();
            switchTurn();
        }
        if(board.check4()){
            restart();
        }
        if(board.isFull()){
            restart();
            return true;
        }
        return false;
    }

    private boolean terminalState(){
        return board.check4() || board.isFull();
    }

    public void makeFakeMove(int col){
        if(!inGame) inGame=true;
        boolean correctMove = board.addDisk(col, actualPlayer);
        if(correctMove){
            incrementMoves();
            switchTurn();
        }

    }
    public void restart(){
        board = new Board();
        yellowMoves = 0;
        redMoves = 0;
        inGame=false;
        actualPlayer=Constants.RED;
        gamemode = Constants.PVP;
    }

    private void incrementMoves(){
        if(actualPlayer==Constants.YELLOW){
            yellowMoves++;
        }else{
            redMoves++;
        }
    }

    private void switchTurn(){
        if(actualPlayer==Constants.YELLOW){
            actualPlayer=Constants.RED;
        }else{
            actualPlayer=Constants.YELLOW;
        }
    }
}
