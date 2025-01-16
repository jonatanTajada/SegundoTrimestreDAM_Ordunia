package com.listviewbinding;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear una lista de números (1 al 5)
        ObservableList<Integer> numbers = FXCollections.observableArrayList(1, 2, 3, 4, 5);

        // Crear el ListView y asignarle la lista de números
        ListView<Integer> listView = new ListView<>(numbers);

        // Crear un Label para mostrar el elemento seleccionado
        Label label = new Label("Elemento seleccionado: ");

        // Vincular el texto del Label al elemento seleccionado del ListView
        label.textProperty().bind(
            Bindings.concat("Elemento seleccionado: ", listView.getSelectionModel().selectedItemProperty())
        );

        // Crear un contenedor y añadir los componentes
        VBox root = new VBox(10, label, listView);
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
