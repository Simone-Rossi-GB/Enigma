package com.enigma.enigma;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EnigmaApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(EnigmaApplication.class.getResource("enigma-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Enigma Machine Simulator");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}