package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Cargar el archivo FXML
			Parent root = FXMLLoader.load(getClass().getResource("/vista/PersonaApp.fxml"));

			// Crear la escena usando el nodo raíz cargado
			Scene scene = new Scene(root, 600, 400);

			// Aplicar el archivo CSS, si lo necesitas
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// Configurar la escena y mostrar la ventana principal
			primaryStage.setScene(scene);
			primaryStage.setTitle("Persona App");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
