package controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    
    @FXML
    private TextField txtBuscar;


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
        try {
            // Cargar el archivo FXML del formulario
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/AñadirContacto.fxml"));
            Parent root = loader.load();

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setTitle("Añadir Contacto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquear la ventana principal mientras está abierta
            stage.showAndWait();

            // Actualizar la tabla después de cerrar el formulario
            cargarContactos();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al abrir el formulario: " + e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    private void editarContacto() {
        try {
            // Obtener el contacto seleccionado
            Contacto contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();

            if (contactoSeleccionado == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING, "Por favor, selecciona un contacto para editar.");
                alerta.showAndWait();
                return;
            }

            // Cargar el archivo FXML del formulario de editar contacto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/EditarContacto.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasar el contacto seleccionado
            EditarContactoControlador controlador = loader.getController();
            controlador.setContacto(contactoSeleccionado);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setTitle("Editar Contacto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquear la ventana principal mientras esta está abierta
            stage.showAndWait();

            // Actualizar la tabla después de editar el contacto
            cargarContactos();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al abrir el formulario: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void eliminarContacto() {
        Contacto contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();

        if (contactoSeleccionado != null) {
            contactoService.eliminarContacto(contactoSeleccionado.getId());
            listaContactos.remove(contactoSeleccionado);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor, selecciona un contacto para eliminar.");
            alert.showAndWait();
        }
    }
    
 

    @FXML
    private void buscarContacto() {
        String filtro = txtBuscar.getText().toLowerCase();

        // Filtrar contactos por nombre
        ObservableList<Contacto> contactosFiltrados = FXCollections.observableArrayList(
            listaContactos.stream()
                .filter(contacto -> contacto.getNombre().toLowerCase().contains(filtro))
                .toList()
        );

        // Actualizar la tabla con los resultados filtrados
        tablaContactos.setItems(contactosFiltrados);

        // Mostrar mensaje si no hay resultados
        if (contactosFiltrados.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No se encontraron contactos que coincidan con la búsqueda.");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void mostrarTodosContactos() {
        tablaContactos.setItems(listaContactos); // Restaura la lista completa
    }


}
