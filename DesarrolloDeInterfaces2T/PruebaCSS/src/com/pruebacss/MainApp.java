package com.pruebacss;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
	
    @Override
    public void start(Stage primaryStage) {
    	
        // Contenedor principal
        VBox root = new VBox(10);
        root.setStyle("-fx-alignment: center; -fx-padding: 20px; -fx-background-color: #f4f4f4;");

        // === ELEMENTOS DE LA INTERFAZ ===

        // Botón verde con estilo de CSS
        Button botonVerde = new Button("Pulsa");
        botonVerde.getStyleClass().add("boton-verde");

        // Etiqueta normal
        Label etiquetaNormal = new Label("Soy normal");
        etiquetaNormal.getStyleClass().add("label-normal");

        // Etiqueta especial
        Label etiquetaEspecial = new Label("Soy especial");
        etiquetaEspecial.getStyleClass().add("label-especial");

        // Botones adicionales con estilos específicos
        Button boton1 = new Button("Pulsa1");
        boton1.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        Button boton2 = new Button("Pulsa2");
        boton2.getStyleClass().add("boton-azul");

        Button boton3 = new Button("Pulsa3");
        boton3.getStyleClass().add("boton-azul");

        Button boton4 = new Button("Pulsa4");
        boton4.getStyleClass().add("boton-verde");

        // Añadir elementos al contenedor
        root.getChildren().addAll(botonVerde, etiquetaNormal, etiquetaEspecial, boton1, boton2, boton3, boton4);

        // Escena y configuración de la ventana
        Scene scene = new Scene(root, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/resources/fichero.css").toExternalForm());

        primaryStage.setTitle("Pruebas CSS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
