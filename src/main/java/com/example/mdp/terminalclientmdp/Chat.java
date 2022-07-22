package com.example.mdp.terminalclientmdp;

import javafx.scene.layout.VBox;
import lombok.Data;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

@Data
public class Chat {
    private String contactID;
    private List<Message> messages = new ArrayList<>();
    private VBox vBox = new VBox();

    @Override
    public String toString()
    {
        return contactID;
    }
}
