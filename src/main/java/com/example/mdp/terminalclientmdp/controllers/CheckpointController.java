package com.example.mdp.terminalclientmdp.controllers;

import com.example.mdp.terminalclientmdp.TerminalClient;
import com.example.mdp.terminalclientmdp.model.ContextHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckpointController implements Initializable {

    @FXML
    private TextField checkpointTextField;

    @FXML
    private TextField terminalTextField;

    @FXML
    private ComboBox<String> typeComboBox;

    public void onCheckpointChosen(ActionEvent e) throws IOException {
        Integer checkpointID = Integer.parseInt(checkpointTextField.getText().trim());
        Integer terminalID = Integer.parseInt(terminalTextField.getText().trim());
        String type = typeComboBox.getSelectionModel().getSelectedItem().equals("Carinski")? "C" : "P";
        ContextHolder.getInstance().setLoggedInCheckpointID(""+terminalID + "-" + checkpointID +type);
        FXMLLoader fxmlLoader = new FXMLLoader(TerminalClient.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setTitle("Prijava");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeComboBox.getItems().addAll("Policijski", "Carinski");
    }
}
