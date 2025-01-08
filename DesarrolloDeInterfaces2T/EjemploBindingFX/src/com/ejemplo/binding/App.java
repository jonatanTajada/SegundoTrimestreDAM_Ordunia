package com.ejemplo.binding;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



//ejemplo de BIDING UNIDIRECCIONAL
public class App extends Application {
    @Override
    public void start(Stage ventanaPrincipal) throws Exception {
        // Cargar el archivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("/com/ejemplo/binding/vistas/bindingEjemplo.fxml"));

        // Crear la escena
        Scene scene = new Scene(root, 650, 725);

        // Configurar el escenario (ventana principal)
        ventanaPrincipal.setTitle("Ejemplo Binding FX");
        ventanaPrincipal.setScene(scene);
        
        // Ajusta el tamaño inicial del Stage
       // ventanaPrincipal.setWidth(800); // Cambia según tus necesidades
        //ventanaPrincipal.setHeight(600);
        ventanaPrincipal.sizeToScene();

        
        ventanaPrincipal.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
