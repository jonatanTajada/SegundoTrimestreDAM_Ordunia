package controlador;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Contacto;
import modelo.service.ContactoServiceImpl;

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
	
	@FXML
	private Button btnBorrarTodos;

	@FXML
	private Label lblUsuarioSinFoto;

	
	private ContactoServiceImpl contactoService;
	private ObservableList<Contacto> listaContactos;

	public PrincipalControlador() {
		this.contactoService = new ContactoServiceImpl();
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
	        // 🔹 Nombre SIEMPRE en negro
	        lblNombre.setText("Nombre: " + contactoSeleccionado.getNombre());
	        lblNombre.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black;");

	        // 🔹 Control de la imagen y el mensaje de "Usuario sin foto"
	        if (contactoSeleccionado.getImagen() == null || contactoSeleccionado.getImagen().isEmpty()) {
	            imagenPerfil.setVisible(false); // Ocultar la imagen
	            imagenPerfil.setManaged(false); // Evitar que ocupe espacio
	            lblUsuarioSinFoto.setVisible(true); // ✅ Mostrar mensaje de "Usuario sin foto"
	        } else {
	            imagenPerfil.setImage(new Image("file:" + contactoSeleccionado.getImagen()));
	            imagenPerfil.setVisible(true);
	            imagenPerfil.setManaged(true);
	            lblUsuarioSinFoto.setVisible(false); // ✅ Ocultar el mensaje si hay foto
	        }

	        // 🔹 Teléfono y correo en negro
	        lblTelefono.setText("Teléfono: " + contactoSeleccionado.getTelefono());
	        lblTelefono.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black;");

	        lblCorreo.setText("Correo: " + contactoSeleccionado.getCorreo());
	        lblCorreo.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black;");

	        // 🔹 Si no hay sitio web, mostrar "Usuario sin sitio web" en rojo
	        if (contactoSeleccionado.getSitioWeb() == null || contactoSeleccionado.getSitioWeb().isEmpty()) {
	            lblSitioWeb.setText("Usuario sin sitio web");
	            lblSitioWeb.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: red;");
	        } else {
	            lblSitioWeb.setText("Sitio Web: " + contactoSeleccionado.getSitioWeb());
	            lblSitioWeb.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black;");
	        }

	        // 🔹 Alinear los textos a la izquierda
	        lblNombre.setAlignment(Pos.BASELINE_LEFT);
	        lblTelefono.setAlignment(Pos.BASELINE_LEFT);
	        lblCorreo.setAlignment(Pos.BASELINE_LEFT);
	        lblSitioWeb.setAlignment(Pos.BASELINE_LEFT);
	        imagenPerfil.setStyle("-fx-padding: 5 0 0 0;");

	        // 🔹 Asegurar que los contenedores de los labels estén alineados a la izquierda
	        ((VBox) lblNombre.getParent()).setAlignment(Pos.TOP_LEFT);
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
	
	@FXML
	private void borrarTodosContactos() {
	    // Mostrar una alerta de confirmación
	    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, 
	        "¿Estás seguro de que quieres borrar todos los contactos?", 
	        ButtonType.YES, ButtonType.NO);
	    confirmacion.setTitle("Confirmar eliminación");
	    confirmacion.setHeaderText(null);

	    // Esperar la respuesta del usuario
	    confirmacion.showAndWait().ifPresent(response -> {
	        if (response == ButtonType.YES) {
	            // Llamar al servicio para eliminar todos los contactos
	            contactoService.eliminarTodosContactos();
	            listaContactos.clear(); // Vaciar la tabla
	        }
	    });
	}
}
