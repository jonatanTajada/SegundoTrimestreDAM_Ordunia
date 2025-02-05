package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        cambiarVentana("/vista/Login.fxml", "Gestor de Contactos - Login", 600, 400, false);
    }

    /**
     * 📌 Cambia entre Login, Registro y Principal reutilizando la misma ventana.
     */
    public static void cambiarVentana(String fxmlPath, String titulo, int width, int height, boolean resizable) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root, width, height);
            
            aplicarCSS(scene);

            primaryStage.setTitle(titulo);
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Error al cargar la ventana: " + fxmlPath);
        }
    }

    /**
     * 📌 Aplica el CSS global a todas las pantallas.
     */
    public static void aplicarCSS(Scene scene) {
        String cssPath = "/application/application.css";

        if (Main.class.getResource(cssPath) != null) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(Main.class.getResource(cssPath).toExternalForm());
        } else {
            System.err.println("⚠ No se encontró el archivo CSS: " + cssPath);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
