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
		colNombre.setCellValueFactory(celda -> celda.getValue().nombreProperty());
		colTelefono.setCellValueFactory(celda -> celda.getValue().telefonoProperty());
		colCorreo.setCellValueFactory(celda -> celda.getValue().correoProperty());

		cargarContactos();
		tablaContactos.setOnMouseClicked(this::mostrarDetallesContacto);

		tablaContactos.sceneProperty().addListener((obs, oldScene, newScene) -> {
			if (newScene != null) {
				newScene.widthProperty().addListener((obsWidth, oldWidth, newWidth) -> {
					double nuevoTamaño = newWidth.doubleValue() * 0.1;
					imagenPerfil.setFitWidth(nuevoTamaño);
					imagenPerfil.setFitHeight(nuevoTamaño);
				});
			}
		});
	}

	private void cargarContactos() {
		List<Contacto> contactos = contactoService.obtenerContactos();
		listaContactos = FXCollections.observableArrayList(contactos);
		tablaContactos.setItems(listaContactos);
	}

	@FXML
	private void mostrarDetallesContacto(MouseEvent event) {
	    Contacto contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();

	    if (contactoSeleccionado != null) {
	        lblNombre.setText("Nombre: " + contactoSeleccionado.getNombre());
	        lblTelefono.setText("Teléfono: " + contactoSeleccionado.getTelefono());
	        lblCorreo.setText("Correo: " + contactoSeleccionado.getCorreo());
	        lblSitioWeb.setText("Sitio Web: " + contactoSeleccionado.getSitioWeb());

	        String rutaImagen = contactoSeleccionado.getImagen();

	        if (rutaImagen == null || rutaImagen.isEmpty()) {
	            // Ocultar la imagen y mostrar el mensaje en rojo y negrita
	            imagenPerfil.setVisible(false);
	            lblNombre.setText("Usuario sin foto");
	            lblNombre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: red;");
	        } else {
	            // Mostrar la imagen y ocultar el mensaje
	            imagenPerfil.setImage(new Image("file:" + rutaImagen));
	            imagenPerfil.setVisible(true);
	            lblNombre.setText("Nombre: " + contactoSeleccionado.getNombre());
	            lblNombre.setStyle(""); // Eliminar estilos previos si se ha asignado imagen
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

		ObservableList<Contacto> contactosFiltrados = FXCollections.observableArrayList(listaContactos.stream()
				.filter(contacto -> contacto.getNombre().toLowerCase().contains(filtro)).toList());

		tablaContactos.setItems(contactosFiltrados);

		if (contactosFiltrados.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION,
					"No se encontraron contactos que coincidan con la búsqueda.");
			alert.showAndWait();
		}
	}

	@FXML
	private void mostrarTodosContactos() {
		tablaContactos.setItems(listaContactos);
	}

	@FXML
	private void abrirPantallaPrincipal() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Principal.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);

			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());

			Stage stage = new Stage();
			stage.setTitle("Gestor de Contactos");
			stage.setScene(scene);
			stage.show();

			((Stage) txtBuscar.getScene().getWindow()).close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
