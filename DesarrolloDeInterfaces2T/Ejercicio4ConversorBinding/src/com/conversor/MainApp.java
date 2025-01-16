package com.conversor;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear un Slider (0 a 100, valor inicial 0)
        Slider slider = new Slider(0, 100, 0);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);

        // Crear campos de texto para las conversiones
        TextField metrosField = new TextField();
        TextField pulgadasField = new TextField();

        // Hacer los campos de texto no editables
        metrosField.setEditable(false);
        pulgadasField.setEditable(false);

        // Crear un Label para el encabezado
        Label headerLabel = new Label("Conversor de longitud, en Pies:");

        // Crear propiedades para las conversiones
        DoubleProperty metrosProperty = new SimpleDoubleProperty();
        DoubleProperty pulgadasProperty = new SimpleDoubleProperty();

        // Vincular las propiedades a la posición del slider
        metrosProperty.bind(slider.valueProperty().multiply(0.3048)); // 1 pie = 0.3048 metros
        pulgadasProperty.bind(slider.valueProperty().multiply(12));  // 1 pie = 12 pulgadas

        // Vincular las propiedades a los campos de texto
        metrosField.textProperty().bind(Bindings.format("En metros: %.2f", metrosProperty));
        pulgadasField.textProperty().bind(Bindings.format("En pulgadas: %.2f", pulgadasProperty));

        // Crear un contenedor y añadir los controles
        VBox root = new VBox(10, headerLabel, slider, metrosField, pulgadasField);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Configurar la escena y mostrarla
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("JavaFX Binding");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Lanzar la aplicación JavaFX
    }
}
