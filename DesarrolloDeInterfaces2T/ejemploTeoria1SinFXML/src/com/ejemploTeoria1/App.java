package com.ejemploTeoria1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
	@Override
	public void start(Stage ventanaPrincipal) {
		
		
		// Crear un campo de texto y un botón
		TextField texto = new TextField();
		Button boton = new Button("Saludar");

		// Acción del botón
		boton.setOnAction(e -> {
			String nombre = texto.getText();
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Saludo Personalizado");
			alerta.setContentText("Hola mundo " + nombre);
			alerta.show();
		});

		// Crear un VBox y configurar su alineación
		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(20, 20, 20, 20));
		vbox.getChildren().addAll(texto, boton);

		// Crear una escena y configurar la ventana principal
		Scene scene = new Scene(vbox, 300, 150);
		ventanaPrincipal.setTitle("Ejemplo sin FXML");
		ventanaPrincipal.setScene(scene);
		ventanaPrincipal.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
