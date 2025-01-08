package com.calculadora;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage ventanaPrincipal) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/calculadora/vistas/calculadora.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ventanaPrincipal.setTitle("Calculadora con Binding");
        ventanaPrincipal.setScene(scene);
        ventanaPrincipal.sizeToScene();
        ventanaPrincipal.show();
    }

    
    
    
    public static void main(String[] args) {
        launch();
    }
}
