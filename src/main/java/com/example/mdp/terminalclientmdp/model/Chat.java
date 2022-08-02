package com.example.mdp.terminalclientmdp.model;

import javafx.scene.layout.VBox;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Chat {
    private String contactID;
    private boolean isActive = true;
    private List<Message> messages = new ArrayList<>();
    private VBox vBox = new VBox();

    public Chat(String contactID)
    {
        this.contactID = contactID;
    }

    @Override
    public String toString()
    {
        return contactID;
    }
}
