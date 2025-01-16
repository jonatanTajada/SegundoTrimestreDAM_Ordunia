module AgendaContactosFX {
	
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;

    opens controlador to javafx.fxml;
    exports controlador;
    exports modelo;
    exports application;
}
