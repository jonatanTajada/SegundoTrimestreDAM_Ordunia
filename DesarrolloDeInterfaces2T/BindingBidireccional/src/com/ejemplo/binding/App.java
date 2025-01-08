package com.ejemplo.binding;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage ventanaPrincipal) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/com/ejemplo/binding/vistas/bindingBidireccional.fxml"));
		Scene scene = new Scene(root, 650, 750);

		ventanaPrincipal.setTitle("Ejemplo de Biding Bidireccional");
		ventanaPrincipal.sizeToScene();
		ventanaPrincipal.setScene(scene);
		ventanaPrincipal.show();

	}

	
	public static void main(String[] args) {
		launch(args);
	}

	
}
