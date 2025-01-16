package utilities;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class GestorArchivos {

    // Método para seleccionar una imagen con un FileChooser
    public static String seleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));

        File archivoSeleccionado = fileChooser.showOpenDialog(null);

        if (archivoSeleccionado != null) {
            try {
                // Copiar imagen seleccionada a la carpeta del proyecto
                String rutaDestino = "imagenes/" + archivoSeleccionado.getName();
                Path destino = Path.of(rutaDestino);
                Files.createDirectories(destino.getParent());
                Files.copy(archivoSeleccionado.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

                return destino.toString(); // Retorna la ruta de la imagen copiada

            } catch (IOException e) {
                mostrarError("Error al copiar la imagen: " + e.getMessage());
            }
        }

        return null; // Retorna null si no se seleccionó ninguna imagen
    }

    // Método para mostrar alertas de error
    public static void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
