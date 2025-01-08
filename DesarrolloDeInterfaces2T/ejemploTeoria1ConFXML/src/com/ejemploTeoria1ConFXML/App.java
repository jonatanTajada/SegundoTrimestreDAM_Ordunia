package com.ejemploTeoria1ConFXML;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage ventanaPrincipal) throws Exception {
		
		// Carga el archivo FXML desde la carpeta 'vistas'
		Parent root = FXMLLoader.load(getClass().getResource("/com/ejemploTeoria1ConFXML/vistas/ventana.fxml"));

		// Configura la escena
		Scene scene = new Scene(root, 300, 150);
		ventanaPrincipal.setTitle("Ejemplo con FXML");
		ventanaPrincipal.setScene(scene);
		ventanaPrincipal.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
