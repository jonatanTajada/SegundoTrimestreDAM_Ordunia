package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Contacto;
import modelo.service.ContactoServiceImpl;
import utilities.GestorArchivos;

public class AnadirContactoControlador {

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

    @FXML
    private ComboBox<String> cmbLocalidad;

    private final ContactoServiceImpl contactoService;

    public AnadirContactoControlador() {
        this.contactoService = new ContactoServiceImpl();
    }

    @FXML
    private void initialize() {
        inicializarLocalidades();
    }

    private void inicializarLocalidades() {
        ObservableList<String> localidades = FXCollections.observableArrayList(
                "Madrid", "Barcelona", "Bilbao", "Valencia", "Sevilla", "Zaragoza", "Málaga",
                "Murcia", "Palma", "Las Palmas", "Santander", "Alicante", "Granada"
        );
        cmbLocalidad.setItems(localidades);

        // 🚀 Evitar que la localidad sea null si el usuario no selecciona nada
        cmbLocalidad.setValue("Bilbao");
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
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String sitioWeb = txtSitioWeb.getText().trim();
            String imagen = txtRutaImagen.getText().trim();
            String localidad = cmbLocalidad.getValue();

            if (nombre.isBlank() || correo.isBlank() || telefono.isBlank() || localidad == null) {
                throw new IllegalArgumentException("⚠ Nombre, correo, teléfono y localidad son obligatorios.");
            }

            // 🚀 Evitar valores nulos en imagen y sitio web
            if (sitioWeb.isEmpty()) sitioWeb = "No especificado";
            if (imagen.isEmpty()) imagen = "No disponible";

            // 📌 Verificar que los datos sean correctos antes de guardar
            System.out.println("Intentando guardar contacto:");
            System.out.println("Nombre: " + nombre);
            System.out.println("Correo: " + correo);
            System.out.println("Teléfono: " + telefono);
            System.out.println("Localidad: " + localidad);
            System.out.println("Imagen: " + imagen);
            System.out.println("Sitio Web: " + sitioWeb);

            // Crear objeto contacto
            Contacto nuevoContacto = new Contacto(0, nombre, correo, telefono, localidad, imagen, sitioWeb);
            
            // Intentar guardar
            boolean guardado = contactoService.crearContacto(nuevoContacto);
            
            if (guardado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "✅ Contacto añadido con éxito.");
                cancelar(); // 🚀 Cerrar ventana sólo si el guardado es exitoso
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "❌ No se pudo guardar el contacto. Puede que el teléfono o correo ya existan.");
            }

        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "❌ Error inesperado: " + e.getMessage());
            e.printStackTrace(); // 📌 Imprimir detalles del error en la consola
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
