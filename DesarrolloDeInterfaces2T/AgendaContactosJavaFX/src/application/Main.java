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
            // Cargar la vista de Login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Login.fxml"));
            Parent root = loader.load();
            
            // Crear la escena
            Scene scene = new Scene(root, 450, 500); // ⬅ Tamaño ajustado para mejor visualización

            // Aplicar estilos específicos para el Login
            String cssPath = "/application/applicationUsuario.css";
            if (getClass().getResource(cssPath) != null) {
                scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            } else {
                System.err.println("⚠ No se encontró el archivo CSS: " + cssPath);
            }

            primaryStage.setTitle("Login - Gestor de Contactos");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // ⬅ Mantiene el tamaño fijo en Login
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("❌ Error al cargar la ventana de Login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
