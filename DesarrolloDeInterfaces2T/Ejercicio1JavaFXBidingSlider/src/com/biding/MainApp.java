package com.biding;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear el Slider con valores entre 0 y 1
        Slider slider = new Slider(0, 1, 0);
        slider.setShowTickMarks(true); // Mostrar marcas
        slider.setShowTickLabels(true); // Mostrar etiquetas

        // Crear la ProgressBar
        ProgressBar progressBar = new ProgressBar(0);

        // Crear un Label para mostrar el valor del Slider
        Label label = new Label("Valor del Slider: 0.00");

        // Enlazar el valor del Slider al texto del Label
        label.textProperty().bind(slider.valueProperty().asString("Valor del Slider: %.2f"));

        // Vincular el ProgressBar al Slider (binding unidireccional)
        progressBar.progressProperty().bind(slider.valueProperty());

        // Crear un contenedor VBox y añadir los componentes
        VBox root = new VBox(10, slider, label, progressBar);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Configurar la escena y mostrarla
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("JavaFX Binding");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Lanzar la aplicación JavaFX
    }
}
