package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Contacto;
import modelo.service.ContactoServiceImpl;
import utilities.GestorArchivos;

public class EditarContactoControlador {

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
    private ComboBox<String> cmbLocalidad; // ComboBox para localidades

    private Contacto contactoSeleccionado;
    private final ContactoServiceImpl contactoService;
    private PrincipalControlador principalControlador; // Referencia al controlador principal

    public EditarContactoControlador() {
        this.contactoService = new ContactoServiceImpl();
    }

    @FXML
    private void initialize() {
        inicializarLocalidades();
    }

    /**
     * Cargar la lista de localidades disponibles en el ComboBox
     */
    public void inicializarLocalidades() {
        ObservableList<String> localidades = FXCollections.observableArrayList(
                "Madrid", "Barcelona", "Bilbao", "Valencia", "Sevilla", "Zaragoza", "Málaga",
                "Murcia", "Palma", "Las Palmas", "Santander", "Alicante", "Granada"
        );
        cmbLocalidad.setItems(localidades);
    }

    /**
     * Precargar los datos del contacto en el formulario de edición.
     */
    public void setContacto(Contacto contacto) {
        if (contacto == null) {
            System.err.println("⚠ Error: El contacto recibido es NULL.");
            return;
        }

        this.contactoSeleccionado = contacto;

        // 🛑 Debug: Verificar que el contacto tiene ID
        System.out.println("📌 Editando contacto ID: " + contacto.getId());

        precargarDatos();
    }

    /**
     * Asigna el controlador principal para actualizar la tabla después de la edición
     */
    public void setPrincipalControlador(PrincipalControlador controlador) {
        this.principalControlador = controlador;
    }

    /**
     * Cargar los datos del contacto en los campos de texto
     */
    private void precargarDatos() {
        if (contactoSeleccionado != null) {
            txtNombre.setText(contactoSeleccionado.getNombre());
            txtCorreo.setText(contactoSeleccionado.getCorreo());
            txtTelefono.setText(contactoSeleccionado.getTelefono());
            txtSitioWeb.setText(contactoSeleccionado.getSitioWeb());
            txtRutaImagen.setText(contactoSeleccionado.getImagen());

            // 🔄 Si la localidad del contacto no está en la lista, la añadimos temporalmente
            if (!cmbLocalidad.getItems().contains(contactoSeleccionado.getLocalidad())) {
                cmbLocalidad.getItems().add(contactoSeleccionado.getLocalidad());
            }
            cmbLocalidad.setValue(contactoSeleccionado.getLocalidad()); // Seleccionar la localidad del contacto
        }
    }

    /**
     * Permite seleccionar una imagen desde el sistema de archivos.
     */
    @FXML
    private void seleccionarImagen() {
        String rutaImagen = GestorArchivos.seleccionarImagen();
        if (rutaImagen != null) {
            txtRutaImagen.setText(rutaImagen);
        }
    }

    /**
     * Guardar los cambios del contacto editado.
     */
    @FXML
    private void guardarCambios() {
        try {
            if (contactoSeleccionado == null) {
                System.err.println("❌ No hay contacto seleccionado para actualizar.");
                return;
            }

            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String telefono = txtTelefono.getText();
            String sitioWeb = txtSitioWeb.getText();
            String imagen = txtRutaImagen.getText();
            String localidad = cmbLocalidad.getValue();

            // 🛑 Imprimir los valores antes de validar
            System.out.println("📌 DATOS INGRESADOS ANTES DE VALIDAR:");
            System.out.println("Nombre: " + nombre);
            System.out.println("Correo: " + correo);
            System.out.println("Teléfono: " + telefono);
            System.out.println("Sitio Web: " + sitioWeb);
            System.out.println("Imagen: " + imagen);
            System.out.println("Localidad: " + localidad);

            if (nombre.isBlank() || correo.isBlank() || telefono.isBlank() || localidad == null) {
                throw new IllegalArgumentException("Nombre, correo, teléfono y localidad son obligatorios.");
            }

            // Asignar los valores actualizados
            contactoSeleccionado.setNombre(nombre);
            contactoSeleccionado.setCorreo(correo);
            contactoSeleccionado.setTelefono(telefono);
            contactoSeleccionado.setSitioWeb(sitioWeb);
            contactoSeleccionado.setImagen(imagen);
            contactoSeleccionado.setLocalidad(localidad);

            System.out.println("📌 Guardando cambios para contacto ID: " + contactoSeleccionado.getId());

            boolean actualizado = contactoService.actualizarContacto(contactoSeleccionado);

            if (actualizado) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "✅ Contacto actualizado con éxito.");
                alert.showAndWait();

                // 🔄 Actualizar la tabla en `PrincipalControlador`
                if (principalControlador != null) {
                    principalControlador.actualizarTabla();
                }

                cancelar(); // Cerrar la ventana después de actualizar
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "❌ No se pudo actualizar el contacto.");
                alert.showAndWait();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "❌ Error: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Cerrar la ventana de edición
     */
    @FXML
    private void cancelar() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }
}
