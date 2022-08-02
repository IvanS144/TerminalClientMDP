package com.example.mdp.terminalclientmdp.controllers;

import chat_client.ChatClient;
import com.example.mdp.terminalclientmdp.TerminalClient;
import com.example.mdp.terminalclientmdp.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private LoginService loginServiceClient;

    @FXML
    TextField usernameTextField;

    @FXML
    TextField passwordTextField;

    public void login(ActionEvent event) throws IOException {
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();
        boolean response = true;
        System.out.println(response);
        if(response)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(TerminalClient.class.getResource("chat.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Chat");
            stage.setScene(scene);
            ChatController controller = fxmlLoader.getController();
            ChatClient chatClient = new ChatClient(8081);
            chatClient.register(controller);
            controller.setSubject(chatClient);
            chatClient.startClient();
            stage.setOnCloseRequest(e -> {
                e.consume();
                try {
                    chatClient.sendMessage("END");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                chatClient.stop();
                stage.close();

            });
            stage.show();

        }
    }
}
