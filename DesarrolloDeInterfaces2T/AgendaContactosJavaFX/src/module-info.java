module AgendaContactosFX {
	
	
	
    
    // Requerimientos de módulos de JavaFX
	
    requires javafx.controls; // Permite usar controles como botones, etiquetas, tablas, etc.
    
    requires javafx.fxml; // Necesario para cargar archivos FXML y controladores
    
    requires java.sql; // Permite la conexión con bases de datos mediante JDBC
    
    requires transitive javafx.graphics; // Asegura que los módulos dependientes puedan usar las clases gráficas de JavaFX
    
    requires javafx.base; // Incluye clases base para propiedades y eventos en JavaFX
    

    
    
    // Permite que los paquetes especificados sean accesibles desde FXML
    
    opens controlador to javafx.fxml; // Permite que los controladores sean accesibles por el motor de FXML
    
    opens modelo to javafx.fxml; // Permite que las clases del modelo sean utilizadas en FXML
    
    opens application to javafx.fxml; // Permite el acceso a la aplicación principal desde FXML
    

    
    
    
    // Exporta paquetes para que puedan ser utilizados por otros módulos
    
    exports controlador; // Exporta el paquete de controladores
    
    exports modelo; // Exporta el paquete del modelo
    
    exports application; // Exporta el paquete de la aplicación principal
    
}
