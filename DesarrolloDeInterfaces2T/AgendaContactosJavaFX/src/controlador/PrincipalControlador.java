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
    private void initialize() {
        // Configurar columnas de la tabla
        colNombre.setCellValueFactory(celda -> celda.getValue().nombreProperty());
        colTelefono.setCellValueFactory(celda -> celda.getValue().telefonoProperty());
        colCorreo.setCellValueFactory(celda -> celda.getValue().correoProperty());

        // Cargar contactos en la tabla
        cargarContactos();

        // Configurar evento seleccionado en la tabla
        tablaContactos.setOnMouseClicked(this::mostrarDetallesContacto);

        // Esperar hasta que la ventana esté completamente cargada antes de acceder a la escena
        tablaContactos.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) { // Solo si la escena ya está disponible
                newScene.widthProperty().addListener((obsWidth, oldWidth, newWidth) -> {
                    double nuevoTamaño = newWidth.doubleValue() * 0.1; // 10% del ancho de la ventana
                    imagenPerfil.setFitWidth(nuevoTamaño);
                    imagenPerfil.setFitHeight(nuevoTamaño);
                });
            }
        });
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/AñadirContacto.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Añadir Contacto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

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
            Contacto contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();

            if (contactoSeleccionado == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING, "Por favor, selecciona un contacto para editar.");
                alerta.showAndWait();
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/EditarContacto.fxml"));
            Parent root = loader.load();

            EditarContactoControlador controlador = loader.getController();
            controlador.setContacto(contactoSeleccionado);

            Stage stage = new Stage();
            stage.setTitle("Editar Contacto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

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

        ObservableList<Contacto> contactosFiltrados = FXCollections.observableArrayList(
            listaContactos.stream()
                .filter(contacto -> contacto.getNombre().toLowerCase().contains(filtro))
                .toList()
        );

        tablaContactos.setItems(contactosFiltrados);

        if (contactosFiltrados.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No se encontraron contactos que coincidan con la búsqueda.");
            alert.showAndWait();
        }
    }

    @FXML
    private void mostrarTodosContactos() {
        tablaContactos.setItems(listaContactos);
    }

    /**
     * Asegura que la pantalla principal use su propio CSS**
     */
    @FXML
    private void abrirPantallaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Principal.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Aplicar el CSS específico de la pantalla principal
            scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Gestor de Contactos");
            stage.setScene(scene);
            stage.show();

            // Cerrar la ventana de login
            ((Stage) txtBuscar.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
