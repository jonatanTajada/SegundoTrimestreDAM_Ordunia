module EjemploBindingFX {
	
    requires javafx.controls;
    requires javafx.fxml;

    exports com.ejemplo.binding;
    exports com.ejemplo.binding.controladores;

    opens com.ejemplo.binding.controladores to javafx.fxml;
}
