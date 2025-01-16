module FigurasGeometricasEventosJavaFX {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.figurasgeometricas to javafx.fxml;
    exports com.figurasgeometricas;
}
