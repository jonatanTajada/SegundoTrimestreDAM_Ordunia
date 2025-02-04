module AgendaContactosFX {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.graphics;
    requires javafx.base;

    opens controlador to javafx.fxml;
    opens modelo to javafx.fxml;
    opens application to javafx.fxml; 

    exports controlador;
    exports modelo;
    exports application;
}
