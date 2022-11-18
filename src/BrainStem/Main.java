package BrainStem;

import BrainStem.Brain.LeftBrain.Inv;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Brain/RightBrain/Face.fxml"));
        primaryStage.setTitle("Inventory System: Face");
        primaryStage.setScene(new Scene(root, 850, 550));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
