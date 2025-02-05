package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Contacto;
import modelo.service.ContactoServiceImpl;
import utilities.GestorArchivos;

/**
 * Controlador para la ventana de añadir un nuevo contacto.
 * Maneja la lógica de validación y guardado del contacto en la base de datos.
 */
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

    /**
     * Constructor de la clase. Inicializa el servicio de contacto.
     */
    public AnadirContactoControlador() {
        this.contactoService = new ContactoServiceImpl();
    }

    /**
     * Método de inicialización del controlador.
     * Se ejecuta automáticamente al cargar la ventana.
     */
    @FXML
    private void initialize() {
        inicializarLocalidades();
    }

    /**
     * Carga la lista de localidades en el ComboBox.
     * Si el usuario no selecciona una, se asigna un valor predeterminado.
     */
    private void inicializarLocalidades() {
        ObservableList<String> localidades = FXCollections.observableArrayList(
                "Madrid", "Barcelona", "Bilbao", "Valencia", "Sevilla", "Zaragoza", "Málaga",
                "Murcia", "Palma", "Las Palmas", "Santander", "Alicante", "Granada"
        );
        cmbLocalidad.setItems(localidades);
        cmbLocalidad.setValue("Bilbao"); // Valor predeterminado
    }

    /**
     * Abre el explorador de archivos para seleccionar una imagen.
     * Si se elige una imagen, su ruta se muestra en el campo de texto correspondiente.
     */
    @FXML
    private void seleccionarImagen() {
        String rutaImagen = GestorArchivos.seleccionarImagen();
        if (rutaImagen != null) {
            txtRutaImagen.setText(rutaImagen);
        }
    }

    /**
     * Guarda un nuevo contacto en la base de datos tras validar los datos ingresados.
     * Se muestran alertas en caso de error o éxito.
     */
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
                throw new IllegalArgumentException("Nombre, correo, teléfono y localidad son obligatorios.");
            }

            if (sitioWeb.isEmpty()) sitioWeb = "No especificado";
            if (imagen.isEmpty()) imagen = "No disponible";

            // Mensajes de depuración
            System.out.println("Intentando guardar contacto:");
            System.out.println("Nombre: " + nombre);
            System.out.println("Correo: " + correo);
            System.out.println("Teléfono: " + telefono);
            System.out.println("Localidad: " + localidad);
            System.out.println("Imagen: " + imagen);
            System.out.println("Sitio Web: " + sitioWeb);

            Contacto nuevoContacto = new Contacto(0, nombre, correo, telefono, localidad, imagen, sitioWeb);
            boolean guardado = contactoService.crearContacto(nuevoContacto);
            
            if (guardado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Contacto añadido con éxito.");
                cancelar(); // Cierra la ventana tras un guardado exitoso
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "No se pudo guardar el contacto. Puede que el teléfono o correo ya existan.");
            }

        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Cierra la ventana de añadir contacto.
     */
    @FXML
    private void cancelar() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    /**
     * Muestra una alerta en pantalla con el mensaje y tipo especificados.
     * @param tipo Tipo de alerta (información, error, advertencia).
     * @param mensaje Mensaje a mostrar en la alerta.
     */
    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo, mensaje);
        alert.showAndWait();
    }
}
