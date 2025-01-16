module CalculadoraFX {
    requires javafx.controls;
    requires javafx.fxml;

    opens controlador to javafx.fxml;
    opens vista to javafx.fxml;

    exports application to javafx.graphics;
}
