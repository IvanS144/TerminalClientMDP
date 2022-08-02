module com.example.mdp.terminalclientmdp {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens com.example.mdp.terminalclientmdp to javafx.fxml;
    exports com.example.mdp.terminalclientmdp;
    exports com.example.mdp.terminalclientmdp.chat;
    opens com.example.mdp.terminalclientmdp.chat to javafx.fxml;
    exports com.example.mdp.terminalclientmdp.controllers;
    opens com.example.mdp.terminalclientmdp.controllers to javafx.fxml;
    exports com.example.mdp.terminalclientmdp.model;
    opens com.example.mdp.terminalclientmdp.model to javafx.fxml;
}