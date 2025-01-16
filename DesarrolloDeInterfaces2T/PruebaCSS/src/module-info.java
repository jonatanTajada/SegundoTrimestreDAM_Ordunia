module PruebaCSS {
	
    requires javafx.controls;

    opens com.pruebacss to javafx.fxml;

    exports com.pruebacss;
}
