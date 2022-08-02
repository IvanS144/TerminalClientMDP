package com.example.mdp.terminalclientmdp.chat;

import java.io.IOException;

public interface Subject {
    void sendMessage(String text) throws IOException;
}
