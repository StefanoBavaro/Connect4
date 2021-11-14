package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class GameController{
    Game actualGame = Game.getActualGame();

    @FXML
    GridPane grid;
    @FXML
    Pane labelPane;

    public void newMatch(ActionEvent e){
        actualGame.restart();
        repaint();
    }

    public void exit(ActionEvent e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit the application.");
        alert.setContentText("Are you sure you want to quit the game?");

        if(alert.showAndWait().get()== ButtonType.OK){
            MenuItem source = (MenuItem)e.getSource();
            Stage stage = (Stage)source.getParentPopup().getOwnerWindow();
            stage.close();
        }
    }

    public void setActualPlayer(ActionEvent e){
        String text = ((MenuItem)e.getSource()).getText();
        if(!actualGame.inGame){
            if(text.equals("Yellow starts")){
                actualGame.actualPlayer=Constants.YELLOW;
                if(actualGame.gamemode!=Constants.PVP) makeAIMove(Constants.HumanPlayer);
            }else if(text.equals("Red starts")){
                actualGame.actualPlayer=Constants.RED;
            }
        }else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You cannot select a new starting player during a match; click 'New' to start a new match and select your options.");
            errorAlert.showAndWait();
        }
    }

    public void setGamemode(ActionEvent e){
        String text = ((MenuItem)e.getSource()).getText();
        if(!actualGame.inGame){
            if(text.equals("Human VS Human")){
                actualGame.gamemode=Constants.PVP;
            }else if(text.equals("Human VS Random Player")){
                actualGame.gamemode=Constants.PVRandom;
            }else if(text.equals("Human VS Minimax Player")){
                actualGame.gamemode=Constants.PVMinimax;
                if(actualGame.actualPlayer==Constants.YELLOW) makeAIMove(Constants.HumanPlayer);
            }else if(text.equals("Human VS PrunedMinimax Player")){
                actualGame.gamemode=Constants.PVPrunedMinimax;
                if(actualGame.actualPlayer==Constants.YELLOW) makeAIMove(Constants.HumanPlayer);
            }else if(text.equals("Random Player VS Minimax Player")){
                System.out.print("entro");
                actualGame.gamemode=Constants.RandomVMinimax;
                startRandomVSMinimaxGame();
            }else if(text.equals("Random Player VS PrunedMinimax Player")){
                actualGame.gamemode=Constants.RandomVPrunedMinimax;
            }else if(text.equals("Minimax Player VS PrunedMinimaxPlayer")){
                actualGame.gamemode=Constants.MinimaxVPrunedMinimax;
            }
        }else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You cannot change the gamemode during a match; click 'New' to start a new match and select your options.");
            errorAlert.showAndWait();
        }
    }

    private void startRandomVSMinimaxGame(){
        actualGame.inGame=true;
        char player=actualGame.actualPlayer;
        boolean tie=false;
        while(actualGame.inGame||tie){
            player=actualGame.actualPlayer;
            tie = makeAIMove(Constants.RandomPlayer);
            if(tie || actualGame.inGame==false) break;
            player=actualGame.actualPlayer;
            tie = makeAIMove(Constants.MinimaxPlayer);
        }
        if (actualGame.inGame == false) {
            if (tie) {
                printTie();
            } else {
                printWin(player);
            }
            repaint();
        }
    }

    public void addDisk(ActionEvent e){
        String text =((Button)e.getSource()).getText();
        int column = Integer.parseInt(text)-1;
        if(actualGame.board.checkLegalMove(column)) {
            char player = actualGame.actualPlayer; //need to save this because if a win happens, the status of the game is cancelled and i would not be able to print the right winner.
            paint(column);
            boolean tie = actualGame.makeMove(column);

            if (actualGame.gamemode != Constants.PVP) {
                player = Constants.YELLOW;
                tie = makeAIMove(Constants.HumanPlayer);
            }

            if (actualGame.inGame == false) {
                if (tie) {
                    printTie();
                } else {
                    printWin(player);
                }
                repaint();
            }
        }
    }

    private boolean makeAIMove(int player){
        Timer t = new Timer();
        t.start();
        int nextMove = actualGame.parseAIMove(player);
        paint(nextMove);

        boolean tie = actualGame.makeMove(nextMove);
        t.stop();
        changeTimerLabel(t.elapsed());
        return tie;
    }

    private void repaint(){
        ObservableList<Node> children = grid.getChildren();
        for (Node node : children) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);

            if (columnIndex == null)
                columnIndex = 0;
            if (rowIndex == null)
                rowIndex = 0;

            Circle toRepaint = (Circle)node;
            toRepaint.setFill(Color.WHITE);
        }
        changeMovesLabels(Constants.WIN);
    }

    private void printWin(char winner){
        Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
        winAlert.setHeaderText("Winner");
        if(winner==Constants.YELLOW) winAlert.setContentText("Yellow player won the match! A new match will start with default settings.");
        else winAlert.setContentText("Red player won the match! A new match will start with default settings.");
        winAlert.showAndWait();
    }

    private void printTie(){
        Alert tieAlert = new Alert(Alert.AlertType.INFORMATION);
        tieAlert.setHeaderText("Game result:");
        tieAlert.setContentText("Tie! A new match will start with default settings.");
        tieAlert.showAndWait();
    }

    private void paint(int col){
        int row = actualGame.board.getLastRow(col);
        if(row!=Constants.ERROR) {
            Circle toPaint = getNodeFromGrid(col, row);
            toPaint.setFill(getCircleColor(actualGame.actualPlayer));
            changeMovesLabels(actualGame.actualPlayer);
        }
    }

    private void changeMovesLabels(char player){
        Label colorLabel;
        if(player==Constants.YELLOW){
            colorLabel = (Label)labelPane.getChildren().get(1);
            colorLabel.setText("Yellow Moves: "+(actualGame.yellowMoves+1));
        }else if (player== Constants.RED){
            colorLabel = (Label)labelPane.getChildren().get(0);
            colorLabel.setText("Red Moves: "+(actualGame.redMoves+1));
        }else if(player == Constants.WIN){
            colorLabel = (Label)labelPane.getChildren().get(1);
            colorLabel.setText("Yellow Moves: 0");
            colorLabel = (Label)labelPane.getChildren().get(0);
            colorLabel.setText("Red Moves: 0");
            colorLabel = (Label)labelPane.getChildren().get(2);
            colorLabel.setText("Total Moves: 0");
            return;
        }
        colorLabel = (Label)labelPane.getChildren().get(2);
        colorLabel.setText("Total Moves: " + (actualGame.redMoves+actualGame.yellowMoves+1));
    }

    private void changeTimerLabel(String time){
        Label timeLabel = (Label)labelPane.getChildren().get(3);
        timeLabel.setText("Last AI Move Time: "+time);
    }

    private Color getCircleColor(char color){
        if(color==Constants.RED) return Color.RED;
        else if(color==Constants.YELLOW) return Color.YELLOW;
        return null;
    }

    private Circle getNodeFromGrid(int col, int row) {
            ObservableList<Node> children = grid.getChildren();
            for (Node node : children) {
                Integer columnIndex = GridPane.getColumnIndex(node);
                Integer rowIndex = GridPane.getRowIndex(node);

                if (columnIndex == null)
                    columnIndex = 0;
                if (rowIndex == null)
                    rowIndex = 0;

                if (columnIndex == col && rowIndex == row) {
                    return (Circle)node;
                }
            }
            return null;
    }
}
