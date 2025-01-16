package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		try {

			Parent root = FXMLLoader.load(getClass().getResource("/vista/Calculadora.fxml"));

			primaryStage.setTitle("Calculadora JavaFX");
			primaryStage.setScene(new Scene(root, 400, 300));
			primaryStage.show();

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
