/**
 * Módulo principal de la aplicación AgendaContactosFX.
 * 
 * Este módulo define los requerimientos y configuraciones necesarias
 * para el correcto funcionamiento del proyecto, incluyendo dependencias 
 * de JavaFX y acceso a bases de datos mediante JDBC.
 */
module AgendaContactosFX {
    
				    // ==============================
				    // 🔹 Requerimientos de Módulos 
				    // ==============================

    /** Permite el uso de controles de JavaFX como botones, etiquetas y tablas. */
    requires javafx.controls; 
    
    /** Necesario para cargar archivos FXML y vincularlos con controladores. */
    requires javafx.fxml; 
    
    /** Permite la conexión con bases de datos mediante JDBC. */
    requires java.sql; 
    
    /** Asegura que los módulos dependientes puedan acceder a las clases gráficas de JavaFX. */
    requires transitive javafx.graphics; 
    
    /** Incluye clases base para propiedades y eventos en JavaFX. */
    requires javafx.base; 
    

			    // ==============================
			    // 🔹 Configuración de Acceso
			    // ==============================

    /** Permite que los controladores sean accesibles por el motor de FXML. */
    opens controlador to javafx.fxml; 
    
    /** Permite que las clases del modelo sean utilizadas en FXML. */
    opens modelo to javafx.fxml; 
    
    /** Permite el acceso a la aplicación principal desde FXML. */
    opens application to javafx.fxml; 
    

			    // ==============================
			    // 🔹 Exportación de Paquetes
			    // ==============================

    /** Exporta el paquete de controladores para su uso en otras partes del proyecto. */
    exports controlador; 
    
    /** Exporta el paquete del modelo para ser accesible desde otros módulos. */
    exports modelo; 
    
    /** Exporta el paquete de la aplicación principal. */
    exports application; 
    
}
