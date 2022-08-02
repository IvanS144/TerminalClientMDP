package com.example.mdp.terminalclientmdp.chat;

public interface Observer {

    void update(String message);
    void initialiseContacts(String[] contacts);
}
