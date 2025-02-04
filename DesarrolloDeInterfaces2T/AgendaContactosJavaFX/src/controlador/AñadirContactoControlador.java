package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.Contacto;
import modelo.service.ContactoServiceImpl;
import utilities.GestorArchivos;

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

    private final ContactoServiceImpl contactoService;

    public AñadirContactoControlador() {
        this.contactoService = new ContactoServiceImpl();
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
            
            // Intentar guardar
            boolean guardado = contactoService.crearContacto(nuevoContacto);
            
            if (guardado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Contacto añadido con éxito.");
                cancelar();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "No se pudo guardar el contacto. Puede que el teléfono o correo ya existan.");
            }

        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error inesperado: " + e.getMessage());
        }
    }


    @FXML
    private void cancelar() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo, mensaje);
        alert.showAndWait();
    }
}
