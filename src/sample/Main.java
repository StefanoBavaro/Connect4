package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
            primaryStage.setTitle("Connect4");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Help");
            alert.setHeaderText("You are about to play Connect4. But how the app works?");
            alert.setContentText("As you can see, the menu bar has 3 sections.\nIn the 'File' section you can choose to start a new match or to exit the app.\n"+
                    "In the 'Options' section you can choose which of the two colors will start; consider that, if you are playing against an AI, the yellow player will be the AI.\n"+
                    "In the 'Players' section you can choose the gamemode: the first four allow you to play the game, the last three will just make you watch an AI VS AI game."+
                    "By default, you are playing a Human VS Human game, with red player starting.");
            alert.showAndWait();

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
