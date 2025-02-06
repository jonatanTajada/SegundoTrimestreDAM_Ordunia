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
 * Controlador para la ventana de edición de un contacto existente. Permite
 * modificar los datos del contacto y guardarlos en la base de datos.
 */
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
	private ComboBox<String> cmbLocalidad; 

	private Contacto contactoSeleccionado;
	
	private final ContactoServiceImpl contactoService;
	
	private PrincipalControlador principalControlador; 

	/**
	 * Constructor de la clase. Inicializa el servicio de contacto.
	 */
	public EditarContactoControlador() {
		this.contactoService = new ContactoServiceImpl();
	}

	/**
	 * Método de inicialización del controlador. Se ejecuta automáticamente al
	 * cargar la ventana.
	 */
	@FXML
	private void initialize() {
		inicializarLocalidades();
	}

	/**
	 * Carga la lista de localidades disponibles en el ComboBox.
	 */
	public void inicializarLocalidades() {
		ObservableList<String> localidades = FXCollections.observableArrayList("Madrid", "Barcelona", "Bilbao",
				"Valencia", "Sevilla", "Zaragoza", "Málaga", "Murcia", "Palma", "Las Palmas", "Santander", "Alicante",
				"Granada");
		cmbLocalidad.setItems(localidades);
	}

	/**
	 * Precarga los datos del contacto en los campos de edición.
	 * 
	 * @param contacto El contacto que se va a editar.
	 */
	public void setContacto(Contacto contacto) {
		if (contacto == null) {
			System.err.println("Error: El contacto recibido es NULL.");
			return;
		}

		this.contactoSeleccionado = contacto;

		System.out.println("Editando contacto ID: " + contacto.getId());

		precargarDatos();
	}

	/**
	 * Establece la referencia al controlador principal para actualizar la tabla
	 * tras la edición.
	 * 
	 * @param controlador Referencia del controlador principal.
	 */
	public void setPrincipalControlador(PrincipalControlador controlador) {
		this.principalControlador = controlador;
	}

	/**
	 * Carga los datos del contacto en los campos de texto.
	 */
	private void precargarDatos() {
		if (contactoSeleccionado != null) {
			txtNombre.setText(contactoSeleccionado.getNombre());
			txtCorreo.setText(contactoSeleccionado.getCorreo());
			txtTelefono.setText(contactoSeleccionado.getTelefono());
			txtSitioWeb.setText(contactoSeleccionado.getSitioWeb());
			txtRutaImagen.setText(contactoSeleccionado.getImagen());

			// Si la localidad no está en la lista, se agrega temporalmente
			if (!cmbLocalidad.getItems().contains(contactoSeleccionado.getLocalidad())) {
				cmbLocalidad.getItems().add(contactoSeleccionado.getLocalidad());
			}
			cmbLocalidad.setValue(contactoSeleccionado.getLocalidad());
		}
	}

	/**
	 * Abre el explorador de archivos para seleccionar una imagen. Si se elige una
	 * imagen, su ruta se muestra en el campo correspondiente.
	 */
	@FXML
	private void seleccionarImagen() {
		String rutaImagen = GestorArchivos.seleccionarImagen();
		if (rutaImagen != null) {
			txtRutaImagen.setText(rutaImagen);
		}
	}

	/**
	 * Guarda los cambios del contacto editado en la base de datos.
	 */
	@FXML
	private void guardarCambios() {
		try {
			if (contactoSeleccionado == null) {
				System.err.println("No hay contacto seleccionado para actualizar.");
				return;
			}

			String nombre = txtNombre.getText();
			String correo = txtCorreo.getText();
			String telefono = txtTelefono.getText();
			String sitioWeb = txtSitioWeb.getText();
			String imagen = txtRutaImagen.getText();
			String localidad = cmbLocalidad.getValue();

			// Imprimir los valores antes de validar
			System.out.println("DATOS INGRESADOS ANTES DE VALIDAR:");
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

			System.out.println("Guardando cambios para contacto ID: " + contactoSeleccionado.getId());

			boolean actualizado = contactoService.actualizarContacto(contactoSeleccionado);

			if (actualizado) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Contacto actualizado con éxito.");
				alert.showAndWait();

				// Actualizar la tabla en `PrincipalControlador`
				if (principalControlador != null) {
					principalControlador.actualizarTabla();
				}

				cancelar(); 
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo actualizar el contacto.");
				alert.showAndWait();
			}

		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
			alert.showAndWait();
		}
	}

	/**
	 * Cierra la ventana de edición.
	 */
	@FXML
	private void cancelar() {
		Stage stage = (Stage) txtNombre.getScene().getWindow();
		stage.close();
	}
}
