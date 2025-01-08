module BindingBidireccionalFX {
	
	
    requires javafx.controls;
    requires javafx.fxml;

    exports com.ejemplo.binding.controladores.Controlador.java; // Exporta el paquete de controladores
    opens com.ejemplo.binding.controladores to javafx.fxml; // Permite reflexión para el paquete de controladores
}
