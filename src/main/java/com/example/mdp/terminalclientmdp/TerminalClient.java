package com.example.mdp.terminalclientmdp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TerminalClient extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TerminalClient.class.getResource("checkpoint.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Terminal");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}