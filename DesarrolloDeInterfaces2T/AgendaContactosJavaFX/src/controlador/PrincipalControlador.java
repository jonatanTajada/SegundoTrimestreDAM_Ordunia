
/**
 * Gestionar los eventos de la tabla (TableView) y los botones (Añadir, Editar, Eliminar).
   Actualizar la sección de detalles cuando se seleccione un contacto en la tabla.
   Conectar la vista con la capa service para realizar las operaciones CRUD.
 */
package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Contacto;
import modelo.service.ContactoService;

import java.util.List;

public class PrincipalControlador {

    @FXML
    private TableView<Contacto> tablaContactos;

    @FXML
    private TableColumn<Contacto, String> colNombre;

    @FXML
    private TableColumn<Contacto, String> colTelefono;

    @FXML
    private TableColumn<Contacto, String> colCorreo;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblTelefono;

    @FXML
    private Label lblCorreo;

    @FXML
    private Label lblSitioWeb;

    @FXML
    private Button btnAñadir;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    
    private ContactoService contactoService;
    private ObservableList<Contacto> listaContactos;
    

    public PrincipalControlador() {
        this.contactoService = new ContactoService();
    }

    @FXML
    public void initialize() {
        //configurar columnas de la tabla
        colNombre.setCellValueFactory(celda -> celda.getValue().nombreProperty());
        colTelefono.setCellValueFactory(celda -> celda.getValue().telefonoProperty());
        colCorreo.setCellValueFactory(celda -> celda.getValue().correoProperty());

        //cargar contactos en la tabla
        cargarContactos();

        //configurar evento seleccionado en la tabla
        tablaContactos.setOnMouseClicked(this::mostrarDetallesContacto);
       // tablaContactos.setOnMouseClicked(event -> this.mostrarDetallesContacto(event));

    }

    private void cargarContactos() {
        // Obtener contactos desde el servicio
        List<Contacto> contactos = contactoService.obtenerContactos();
        listaContactos = FXCollections.observableArrayList(contactos);
        tablaContactos.setItems(listaContactos);
    }

    private void mostrarDetallesContacto(MouseEvent event) {
        Contacto contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();

        if (contactoSeleccionado != null) {
            lblNombre.setText("Nombre: " + contactoSeleccionado.getNombre());
            lblTelefono.setText("Teléfono: " + contactoSeleccionado.getTelefono());
            lblCorreo.setText("Correo: " + contactoSeleccionado.getCorreo());
            lblSitioWeb.setText("Sitio Web: " + contactoSeleccionado.getSitioWeb());

            // Mostrar imagen (usar una imagen predeterminada si no existe)
            String rutaImagen = contactoSeleccionado.getImagen();
            if (rutaImagen == null || rutaImagen.isEmpty()) {
                imagenPerfil.setImage(new Image("/imagenes/default.png"));
            } else {
                imagenPerfil.setImage(new Image("file:" + rutaImagen));
            }
        }
    }

    @FXML
    private void añadirContacto() {
        // Aquí abriríamos una ventana para añadir un contacto
        Alert alert = new Alert(AlertType.INFORMATION, "Función de añadir contacto aún no implementada.");
        alert.showAndWait();
    }

    @FXML
    private void editarContacto() {
        // Aquí abriríamos una ventana para editar el contacto seleccionado
        Alert alert = new Alert(AlertType.INFORMATION, "Función de editar contacto aún no implementada.");
        alert.showAndWait();
    }

    @FXML
    private void eliminarContacto() {
        Contacto contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();

        if (contactoSeleccionado != null) {
            contactoService.eliminarContacto(contactoSeleccionado.getId());
            listaContactos.remove(contactoSeleccionado);
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Por favor, selecciona un contacto para eliminar.");
            alert.showAndWait();
        }
    }
}
