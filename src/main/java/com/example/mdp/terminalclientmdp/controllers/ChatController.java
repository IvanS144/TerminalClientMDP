package com.example.mdp.terminalclientmdp.controllers;

import com.example.mdp.terminalclientmdp.model.Chat;
import com.example.mdp.terminalclientmdp.model.ContextHolder;
import com.example.mdp.terminalclientmdp.model.Message;
import com.example.mdp.terminalclientmdp.chat.Observer;
import com.example.mdp.terminalclientmdp.chat.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChatController  implements Observer, Initializable {
    @FXML
    BorderPane vBoxPane;
    @FXML
    ListView<Chat> contactsList;

    @FXML
    TextArea textArea;

    @FXML
    Button sendButton;

    private Subject subject;

    private final Pattern contactsPattern = Pattern.compile("Server#(NC|CD)#\\d+-\\d+[PC]");
    @Override
    public void update(String message) {
        String[] messageParts = message.split("#");
        if(contactsPattern.matcher(message).matches())
        {
            String contactID = messageParts[2];
            Chat contact = contactsList.getItems().stream().filter(c -> c.getContactID().equals(contactID)).findFirst().orElse(null);
            if("CD".equals(messageParts[1]))
            {
                if(contact!=null) {
                    contact.setActive(false);
                }
            }
            if("NC".equals(messageParts[1]))
            {
                if(contact!=null)
                {
                    contact.setActive(true);
                }
                else
                {
                    Chat chat = new Chat();
                    chat.setContactID(contactID);
                    contactsList.getItems().add(chat);
                }
            }
        }
        else {
            Message m = new Message(messageParts[2], messageParts[0]);
            Chat contact = contactsList.getItems().stream().filter(c -> c.getContactID().equals(m.getContactID())).findFirst().orElse(null);
            if (contact != null) {
                contact.getMessages().add(m);
                Text text = new Text(m.getText());
                TextFlow textFlow = new TextFlow(text);
                textFlow.setPadding(new Insets(5, 10, 5, 10));
                textFlow.setStyle("-fx-color: white; -fx-background-color: cyan; -fx-background-radius: 20px;");
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(5, 5, 5, 10));
                hBox.getChildren().add(textFlow);
                contact.getVBox().getChildren().add(hBox);
//            Platform.runLater(()->{
//                contact.getVBox().getChildren().add(hBox);
//                System.out.println("dodao hbox");
//            });
            }
        }

    }

    @Override
    public void initialiseContacts(String[] contacts) {
        List<Chat> chatList = Stream.of(contacts).map(Chat::new).toList();
        contactsList.getItems().addAll(chatList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactsList.getSelectionModel().selectedItemProperty().addListener((observableValue, chat, t1) -> {
            VBox vBox = contactsList.getSelectionModel().getSelectedItem().getVBox();
            vBoxPane.setCenter(vBox);
        });
//        Chat c1 = new Chat();
//        c1.setContactID("1-1C");
//        c1.getVBox().setStyle("-fx-background-color: red");
//        Chat c2 = new Chat();
//        c2.getVBox().setStyle("-fx-background-color: blue");
//        c2.setContactID("terminal 1-2");
//        Chat[] testChats = new Chat[]{c1, c2};
//        contactsList.getItems().addAll(testChats);
//        contactsList.getItems().add(c1);
    }

    public void sendMessage(ActionEvent event)
    {
        if(!(textArea.getText().isEmpty() || textArea.getText().isBlank()))
        {
            String text = textArea.getText();
            Chat contact = contactsList.getSelectionModel().getSelectedItem();
            if(contact.isActive()) {
                try {
                    String recipient = contact.getContactID();
                    String message = ContextHolder.getInstance().getLoggedInCheckpointID() + "#" + "U#" + recipient + "#" + "M" + "#" + text;
                    subject.sendMessage(message);
                    Text textObj = new Text(text);
                    TextFlow textFlow = new TextFlow(textObj);
                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    textFlow.setStyle("-fx-color: white; -fx-background-color: blue; -fx-background-radius: 20px;");
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));
                    hBox.getChildren().add(textFlow);
                    contact.getVBox().getChildren().add(hBox);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setSubject(Subject subject)
    {
        this.subject = subject;
    }
}
