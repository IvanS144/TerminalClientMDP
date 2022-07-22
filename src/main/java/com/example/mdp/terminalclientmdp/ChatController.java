package com.example.mdp.terminalclientmdp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController  implements Observer, Initializable {
    @FXML
    BorderPane vBoxPane;
    @FXML
    ListView<Chat> contactsList;
    @Override
    public void update(String message) {
        String[] messageParts = message.split("#");
        Message m = new Message(messageParts[0], messageParts[1]);
        Chat contact = contactsList.getItems().stream().filter(c -> c.getContactID().equals(m.getContactID())).findFirst().orElse(null);
        if(contact!=null)
        {
            contact.getMessages().add(m);
            Text text = new Text(m.getText());
            text.setFill(Color.GRAY);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setPadding(new Insets(5, 10, 5,10));
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5,5,5,10));
            hBox.getChildren().add(textFlow);
            contact.getVBox().getChildren().add(hBox);
            Platform.runLater(()->{
                contact.getVBox().getChildren().add(hBox);
            });
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactsList.getSelectionModel().selectedItemProperty().addListener((observableValue, chat, t1) -> {
            VBox vBox = contactsList.getSelectionModel().getSelectedItem().getVBox();
            vBoxPane.setCenter(vBox);
        });
        Chat c1 = new Chat();
        c1.setContactID("terminal 1-1");
        c1.getVBox().setStyle("-fx-background-color: red");
        Chat c2 = new Chat();
        c2.getVBox().setStyle("-fx-background-color: blue");
        c2.setContactID("terminal 1-2");
        Chat[] testChats = new Chat[]{c1, c2};
        contactsList.getItems().addAll(testChats);
    }
}
