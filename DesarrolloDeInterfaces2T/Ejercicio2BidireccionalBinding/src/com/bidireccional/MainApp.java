package com.bidireccional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
    	
        // Crear dos TextFields
        TextField textField1 = new TextField();
        TextField textField2 = new TextField();

        // Configurar un texto inicial (opcional)
        textField1.setText("Este es un enlace en doble vía.");

        // Crear un enlace bidireccional entre ambos TextFields
        textField1.textProperty().bindBidirectional(textField2.textProperty());

        // Crear un contenedor y añadir los TextFields
        VBox root = new VBox(10, textField1, textField2);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Configurar la escena y mostrarla
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("JavaFX Bidirectional Binding");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Lanzar la aplicación JavaFX
    }
}
