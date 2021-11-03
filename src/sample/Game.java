package sample;

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

    public static Game getActualGame() {
        return actualGame;
    }

    public int parseNextMove(){
        int move = Constants.ERROR;
        if(gamemode==Constants.PVRandom){
            move = randomMove();
        }else if(gamemode == Constants.PVMinimax){
            //move = minimaxMove();
        }
        return move;
    }

    public int randomMove(){
        return (int)Math.floor(Math.random() * (6) + 1);
    }

    public void makeMove(int col){
        if(!inGame) inGame=true;
        boolean correctMove = board.addDisk(col, actualPlayer);
        if(correctMove){
            incrementMoves();
            switchTurn();
        }
        if(board.check4()){
            restart();
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
