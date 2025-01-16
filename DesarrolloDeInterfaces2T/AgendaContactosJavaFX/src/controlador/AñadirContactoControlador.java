package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Contacto;
import modelo.service.ContactoService;
import utilities.GestorArchivos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class AñadirContactoControlador {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtSitioWeb;

    @FXML
    private TextField txtRutaImagen;

    private ContactoService contactoService;

    public AñadirContactoControlador() {
        this.contactoService = new ContactoService();
    }

    @FXML
    private void seleccionarImagen() {
        String rutaImagen = GestorArchivos.seleccionarImagen();
        if (rutaImagen != null) {
            txtRutaImagen.setText(rutaImagen);
        }
    }


    @FXML
    private void guardarContacto() {
        try {
            // Validar datos ingresados
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String telefono = txtTelefono.getText();
            String sitioWeb = txtSitioWeb.getText();
            String imagen = txtRutaImagen.getText();

            if (nombre.isBlank() || correo.isBlank() || telefono.isBlank()) {
                throw new IllegalArgumentException("Nombre, correo y teléfono son obligatorios.");
            }

            // Crear un nuevo contacto
            Contacto nuevoContacto = new Contacto(0, nombre, correo, telefono, imagen, sitioWeb);
            contactoService.crearContacto(nuevoContacto);

            // Mostrar mensaje de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Contacto añadido con éxito.");
            alert.showAndWait();

            // Cerrar la ventana
            cancelar();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    private void cancelar() {
        // Cerrar la ventana actual
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }
}
