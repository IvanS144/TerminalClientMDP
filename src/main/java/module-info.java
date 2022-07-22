module com.example.mdp.terminalclientmdp {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;


    opens com.example.mdp.terminalclientmdp to javafx.fxml;
    exports com.example.mdp.terminalclientmdp;
}