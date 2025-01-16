package com.booleanbinding;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
    	
        // Crear campos de texto
        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");

        // Crear un botón
        Button entrarButton = new Button("Entrar");
        entrarButton.setDisable(true); // Inicialmente deshabilitado

        // Crear un BooleanBinding para habilitar el botón si ambos campos están llenos
        BooleanBinding camposLlenos = nombreField.textProperty().isEmpty()
                .or(passwordField.textProperty().isEmpty());
        entrarButton.disableProperty().bind(camposLlenos);

        // Crear un diseño GridPane para organizar los controles
        GridPane root = new GridPane();
        root.setVgap(10);
        root.setHgap(10);
        root.add(nombreField, 0, 0);
        root.add(passwordField, 0, 1);
        root.add(entrarButton, 0, 2);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Configurar la escena y mostrarla
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("JavaFX Boolean Binding");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Lanzar la aplicación JavaFX
    }
}
