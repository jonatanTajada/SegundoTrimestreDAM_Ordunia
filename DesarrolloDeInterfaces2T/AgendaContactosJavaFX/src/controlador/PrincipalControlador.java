package controlador;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.stage.FileChooser;
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

	@FXML
	private Label lblLocalidad;

	@FXML
	private Button btnEstadisticas;

	@FXML
	private TextField txtCorreo;

	@FXML
	private TextField txtLocalidad;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtTelefono;

	@FXML
	private VBox formularioNuevoContacto;

	@FXML
	private Button btnMostrarFormulario;
	@FXML
	private Button btnAnadir;

	@FXML
	private TextField txtImagen;

	@FXML
	private TableColumn<Contacto, String> colLocalidad;

	private ContactoServiceImpl contactoService;

	private ObservableList<Contacto> listaContactos;

	// Constructor
	public PrincipalControlador() {
		this.contactoService = new ContactoServiceImpl();
	}

	
	@FXML
	private void initialize() {
	    System.out.println("Inicializando PrincipalControlador...");

	    colNombre.setCellValueFactory(celda -> celda.getValue().nombreProperty());
	    colTelefono.setCellValueFactory(celda -> celda.getValue().telefonoProperty());
	    colCorreo.setCellValueFactory(celda -> celda.getValue().correoProperty());
	    colLocalidad.setCellValueFactory(celda -> celda.getValue().localidadProperty());

	    // Para que las columnas se expandan con la ventana
	    tablaContactos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

	    // Evento para mostrar detalles del contacto
	    tablaContactos.setOnMouseClicked(event -> {
	        if (event.getClickCount() > 0) {
	            mostrarDetallesContacto(event);
	        }
	    });

	    cargarContactos();
	}

	

	/**
	 * Carga la lista de contactos desde la base de datos y la muestra en la tabla.
	 */
	public void cargarContactos() {
		List<Contacto> lista = contactoService.obtenerContactos();

		//Crear una nueva lista observable para que la tabla se refresque
		tablaContactos.setItems(FXCollections.observableArrayList(lista));
	}

	/**
	 * Muestra los detalles del contacto seleccionado en la parte derecha de la
	 * pantalla.
	 * 
	 * @param event Evento de clic en la tabla
	 */
	@FXML
	private void mostrarDetallesContacto(MouseEvent event) {
		Contacto contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();

		if (contactoSeleccionado != null) {
			System.out.println("📌 Mostrando detalles del contacto ID: " + contactoSeleccionado.getId());

			lblNombre.setText("Nombre: " + contactoSeleccionado.getNombre());
			lblTelefono.setText("Teléfono: " + contactoSeleccionado.getTelefono());
			lblCorreo.setText("Correo: " + contactoSeleccionado.getCorreo());
			
			// Manejo seguro de la localidad
			String localidad = contactoSeleccionado.getLocalidad();
			if (localidad == null || localidad.trim().isEmpty() || localidad.equalsIgnoreCase("NULL")) {
				localidad = "Desconocida";
			}
			lblLocalidad.setText("Localidad: " + localidad);

			// Imagen de perfil
			String rutaImagen = contactoSeleccionado.getImagen();
			if (rutaImagen == null || rutaImagen.isEmpty()) {
				imagenPerfil.setVisible(false);
				imagenPerfil.setManaged(false);
				lblUsuarioSinFoto.setVisible(true);
			} else {
				File archivoImagen = new File(rutaImagen);
				if (archivoImagen.exists() && !archivoImagen.isDirectory()) {
					imagenPerfil.setImage(new Image(archivoImagen.toURI().toString()));
					imagenPerfil.setVisible(true);
					imagenPerfil.setManaged(true);
					lblUsuarioSinFoto.setVisible(false);
				} else {
					imagenPerfil.setVisible(false);
					imagenPerfil.setManaged(false);
					lblUsuarioSinFoto.setVisible(true);
				}
			}
			
			 lblSitioWeb.setText("Sitio Web: " + contactoSeleccionado.getSitioWeb());
			
		} else {
			System.out.println("⚠ No se ha seleccionado ningún contacto.");
		}
	}

	/**
	 * Abre la ventana para añadir un nuevo contacto. La ventana es modal, por lo
	 * que bloquea la principal hasta que se cierre.
	 */
	@FXML
	private void anadirContacto() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/AnadirContacto.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("Añadir Contacto");
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal hasta cerrar esta
			stage.showAndWait();

			// Después de cerrar la ventana, recargar los contactos
			cargarContactos();
		} catch (IOException e) {
			System.err.println("❌ Error al abrir el formulario de añadir contacto: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Abre la ventana para editar un contacto seleccionado en la tabla. Si no hay
	 * un contacto seleccionado, muestra un mensaje de error.
	 */

	@FXML
	private void editarContacto() {
		Contacto seleccionado = tablaContactos.getSelectionModel().getSelectedItem();

		if (seleccionado == null) {
			System.err.println("⚠ Debes seleccionar un contacto para editar.");
			return;
		}

		System.out.println("📝 Editando contacto ID: " + seleccionado.getId());

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/EditarContacto.fxml"));
			Parent root = loader.load();

			// Obtener el controlador y pasar el contacto seleccionado
			EditarContactoControlador controlador = loader.getController();
			controlador.setContacto(seleccionado);
			controlador.setPrincipalControlador(this); 

			Stage stage = new Stage();
			stage.setTitle("Editar Contacto");
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();

			actualizarTabla();

		} catch (IOException e) {
			System.err.println("❌ Error al abrir el formulario de edición: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Método auxiliar para guardar los cambios en la edición.
	 */
	public void guardarCambios(Contacto contacto) {
		contactoService.actualizarContacto(contacto);
		actualizarTabla(); 
	}

	/**
	 * Elimina el contacto seleccionado en la tabla. Muestra una confirmación antes
	 * de proceder con la eliminación. Si no hay un contacto seleccionado, se
	 * muestra una advertencia.
	 */
	@FXML
	private void eliminarContacto() {
		Contacto contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();

		if (contactoSeleccionado != null) {
		
			Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
					"¿Estás seguro de que quieres eliminar este contacto?", ButtonType.YES, ButtonType.NO);
			confirmacion.setTitle("Confirmar eliminación");
			confirmacion.setHeaderText("Se eliminará el contacto: " + contactoSeleccionado.getNombre());

			confirmacion.showAndWait().ifPresent(response -> {
				if (response == ButtonType.YES) {
					contactoService.eliminarContacto(contactoSeleccionado.getId());
					listaContactos.remove(contactoSeleccionado);
				}
			});

		} else {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor, selecciona un contacto para eliminar.");
			alert.showAndWait();
		}
	}

	/**
	 * Busca contactos en la lista basándose en el texto ingresado. Se filtra por
	 * nombre o localidad. Si no se encuentra coincidencia, se muestra un mensaje.
	 * El campo de búsqueda se limpia automáticamente después de ejecutar la acción.
	 */
	@FXML
	private void buscarContacto() {
		String filtro = txtBuscar.getText().toLowerCase();

		if (listaContactos == null) { // 🚀 Asegurar que no sea null
			System.err.println("⚠ listaContactos es NULL, recargando contactos...");
			listaContactos = FXCollections.observableArrayList(contactoService.obtenerContactos());
		}

		// Filtrar contactos basándose en nombre o localidad
		ObservableList<Contacto> contactosFiltrados = FXCollections.observableArrayList(
				listaContactos.stream().filter(contacto -> contacto.getNombre().toLowerCase().contains(filtro)
						|| contacto.getLocalidad().toLowerCase().contains(filtro)).toList());

		// Refrescar la tabla con los contactos filtrados
		tablaContactos.setItems(contactosFiltrados);

		if (contactosFiltrados.isEmpty()) {
			System.out.println("⚠ No se encontraron contactos con el filtro: " + filtro);
			Alert alert = new Alert(Alert.AlertType.INFORMATION,
					"No se encontraron contactos que coincidan con la búsqueda.");
			alert.showAndWait();
		}

		txtBuscar.clear();
	}
	

	/**
	 * Restablece la tabla mostrando todos los contactos almacenados en la base de
	 * datos.
	 */
	@FXML
	private void mostrarTodosContactos() {
		listaContactos = FXCollections.observableArrayList(contactoService.obtenerContactos()); //Recargar desde la bd																						
		tablaContactos.setItems(listaContactos);
	}

	
	/**
	 * Abre la pantalla principal de la aplicación con el diseño adecuado. Carga la
	 * vista "Principal.fxml", aplica el CSS global y ajusta el tamaño de la
	 * ventana. Si la ventana actual es de Login o Registro, se cierra
	 * automáticamente.
	 */
	@FXML
	private void abrirPantallaPrincipal() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Principal.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 800, 600); 

			// Aplicar el CSS global
			String cssPath = "/application/application.css";
			if (getClass().getResource(cssPath) != null) {
				scene.getStylesheets().clear(); 
				scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
			} else {
				System.err.println("⚠ No se encontró el archivo CSS: " + cssPath);
			}

			Stage stage = new Stage();
			stage.setTitle("Gestor de Contactos");
			stage.setScene(scene);
			stage.setMinWidth(800);
			stage.setMinHeight(600);
			stage.show();

			// Cerrar la ventana actual (Login o Registro)
			Stage actualStage = (Stage) txtBuscar.getScene().getWindow();
			if (actualStage != null) {
				actualStage.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Alert errorAlert = new Alert(Alert.AlertType.ERROR,
					"Error al abrir la pantalla principal: " + e.getMessage());
			errorAlert.showAndWait();
		}
	}

	/**
	 * Borra todos los contactos de la base de datos y actualiza la tabla. Antes de
	 * proceder, muestra una alerta de confirmación para evitar eliminaciones
	 * accidentales.
	 */
	@FXML
	private void borrarTodosContactos() {
		// Mostrar una alerta de confirmación
		Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
				"¿Estás seguro de que quieres borrar todos los contactos?", ButtonType.YES, ButtonType.NO);
		confirmacion.setTitle("Confirmar eliminación");
		confirmacion.setHeaderText(null);

		// Esperar la respuesta del usuario
		confirmacion.showAndWait().ifPresent(response -> {
			if (response == ButtonType.YES) {
				try {
					// Llamar al servicio para eliminar todos los contactos
					contactoService.eliminarTodosContactos();
					listaContactos.clear(); 

					// Actualizar la tabla para reflejar los cambios
					tablaContactos.refresh();

					// Mostrar mensaje de éxito
					Alert exito = new Alert(Alert.AlertType.INFORMATION, "Todos los contactos han sido eliminados.");
					exito.setTitle("Eliminación exitosa");
					exito.setHeaderText(null);
					exito.showAndWait();

				} catch (Exception e) {
					// Manejo de errores si ocurre un problema con la eliminación
					Alert error = new Alert(Alert.AlertType.ERROR,
							"Error al eliminar los contactos: " + e.getMessage());
					error.setTitle("Error");
					error.setHeaderText("No se pudieron eliminar los contactos.");
					error.showAndWait();
				}
			}
		});
	}

	/**
	 * Abre la ventana de estadísticas de contactos. Carga la vista
	 * "Estadisticas.fxml" y actualiza los datos antes de mostrarla. Si ocurre un
	 * error al cargar la vista, se muestra una alerta informativa.
	 */
	@FXML
	private void mostrarEstadisticas() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Estadisticas.fxml"));
			Parent root = loader.load();

			// Obtener el controlador y refrescar los datos antes de abrir la ventana
			EstadisticasControlador controlador = loader.getController();
			controlador.refrescarEstadisticas();

			Stage stage = new Stage();
			stage.setTitle("Estadísticas de Contactos");
			stage.setScene(new Scene(root, 600, 400));
			stage.show();
		} catch (Exception e) {
			System.err.println("❌ Error al abrir la ventana de estadísticas: " + e.getMessage());
			Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error al abrir la ventana de estadísticas.");
			errorAlert.showAndWait();
		}
	}

	/**
	 * Muestra u oculta el formulario de agregar un nuevo contacto. Alterna la
	 * visibilidad y el estado de gestión del formulario en la interfaz. También
	 * actualiza el texto del botón según el estado actual.
	 */
	@FXML
	private void mostrarFormulario() {
		boolean estadoActual = formularioNuevoContacto.isVisible();
		formularioNuevoContacto.setVisible(!estadoActual);
		formularioNuevoContacto.setManaged(!estadoActual);

		btnMostrarFormulario.setText(estadoActual ? "Nuevo Contacto" : "Cerrar Formulario");
	}

	/**
	 * Permite al usuario seleccionar una imagen desde su sistema de archivos.
	 * Filtra para permitir solo archivos de imagen y muestra la ruta seleccionada
	 * en el campo de texto. También carga la imagen en el ImageView si se
	 * selecciona correctamente.
	 */
	@FXML
	private void seleccionarImagen() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Seleccionar Imagen");

		// Filtro para solo permitir imágenes
		fileChooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"));

		File archivoSeleccionado = fileChooser.showOpenDialog(txtImagen.getScene().getWindow()); // Usar la ventana actual
																									

		if (archivoSeleccionado != null) {
			// Mostrar la ruta en el campo txtImagen
			txtImagen.setText(archivoSeleccionado.toURI().toString());

			// Cargar la imagen en el ImageView
			imagenPerfil.setImage(new Image(archivoSeleccionado.toURI().toString()));
		}
	}

	/**
	 * Recarga la tabla de contactos con los datos actualizados desde la base de
	 * datos. Obtiene la lista actualizada de contactos y la asigna a la tabla.
	 * También se refresca la vista para reflejar los cambios de inmediato.
	 */
	public void actualizarTabla() {
		System.out.println("🔄 Recargando la tabla de contactos...");

		// Obtener los contactos actualizados desde la base de datos
		List<Contacto> contactosActualizados = contactoService.obtenerContactos();

		// Limpiar la tabla y actualizar con los nuevos datos
		tablaContactos.getItems().setAll(contactosActualizados);
		tablaContactos.refresh(); // Forzar actualización visual

		System.out.println("✅ Tabla actualizada con éxito.");
	}

}
