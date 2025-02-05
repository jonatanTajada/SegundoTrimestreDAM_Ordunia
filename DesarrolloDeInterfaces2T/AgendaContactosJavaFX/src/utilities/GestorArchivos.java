package utilities;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Clase utilitaria para la gestión de archivos en la aplicación.
 * Permite seleccionar imágenes y copiar archivos a una ubicación específica.
 */
public class GestorArchivos {

    /**
     * Permite al usuario seleccionar una imagen mediante un cuadro de diálogo.
     * La imagen seleccionada se copia a la carpeta del proyecto para su uso.
     *
     * @return La ruta del archivo copiado, o null si no se seleccionó ninguna imagen.
     */
    public static String seleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");

        // Filtro para limitar la selección a archivos de imagen
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));

        File archivoSeleccionado = fileChooser.showOpenDialog(null);

        if (archivoSeleccionado != null) {
            try {
                // Ruta donde se almacenará la imagen dentro del proyecto
                String rutaDestino = "imagenes/" + archivoSeleccionado.getName();
                Path destino = Path.of(rutaDestino);

                // Crear la carpeta si no existe
                Files.createDirectories(destino.getParent());

                // Copiar la imagen seleccionada a la ubicación destino
                Files.copy(archivoSeleccionado.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

                return destino.toString(); 

            } catch (IOException e) {
                mostrarError("Error al copiar la imagen: " + e.getMessage());
            }
        }

        return null; 
    }

    /**
     * Muestra una alerta de error con el mensaje proporcionado.
     *
     * @param mensaje Mensaje de error que se mostrará en la alerta.
     */
    public static void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
