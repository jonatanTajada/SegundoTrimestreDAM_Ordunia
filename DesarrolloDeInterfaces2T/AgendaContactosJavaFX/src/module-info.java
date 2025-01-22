module AgendaContactosFX {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens controlador to javafx.fxml;
    opens modelo to javafx.fxml; 

    exports controlador;
    exports modelo;
    exports application;
}
