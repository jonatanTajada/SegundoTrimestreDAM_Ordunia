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
	        Parent root = FXMLLoader.load(getClass().getResource("/vista/Login.fxml"));
	        Scene scene = new Scene(root, 450, 500); // ⬅ Tamaño más grande
	        
	        scene.getStylesheets().add(getClass().getResource("/application/applicationUsuario.css").toExternalForm());
	        
	        primaryStage.setTitle("Login - Gestor de Contactos");
	        primaryStage.setScene(scene);
	        primaryStage.setResizable(false); // ⬅ Evita que se expanda descontroladamente
	        primaryStage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


    public static void main(String[] args) {
        launch(args);
    }
}
