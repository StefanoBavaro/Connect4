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

            /*
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Help");
            alert.setHeaderText("How the app works?");
            alert.setContentText("...");
            alert.showAndWait();*/

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
