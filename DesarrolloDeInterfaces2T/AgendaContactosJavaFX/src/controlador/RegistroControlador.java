package controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.service.UsuarioServiceImpl;

public class RegistroControlador {

	@FXML
	private TextField txtCorreo;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private PasswordField txtConfirmarPassword;

	private final UsuarioServiceImpl usuarioService = new UsuarioServiceImpl();

	/**
	 * Registra un nuevo usuario validando los datos ingresados. Muestra alertas en
	 * caso de error o confirmación de registro exitoso.
	 */
	@FXML
	private void registrarUsuario() {
		String correo = txtCorreo.getText();
		String password = txtPassword.getText();
		String confirmarPassword = txtConfirmarPassword.getText();

		if (correo.isBlank() || password.isBlank() || confirmarPassword.isBlank()) {
			mostrarAlerta(Alert.AlertType.WARNING, "Todos los campos son obligatorios.");
			return;
		}

		if (!password.equals(confirmarPassword)) {
			mostrarAlerta(Alert.AlertType.WARNING, "Las contraseñas no coinciden.");
			return;
		}

		boolean registrado = usuarioService.registrarUsuario(correo, password);
		if (registrado) {
			mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso. Ahora puedes iniciar sesión.");

			// Cierra la ventana de registro sin abrir una nueva de Login
			Stage ventanaRegistro = (Stage) txtCorreo.getScene().getWindow();
			ventanaRegistro.close();
		} else {
			mostrarAlerta(Alert.AlertType.ERROR, "Error al registrar. Intenta con otro correo.");
		}
	}

	/**
	 * Cierra la ventana actual y vuelve a la pantalla de login.
	 */
	@FXML
	private void abrirLogin() {
		Stage ventanaRegistro = (Stage) txtCorreo.getScene().getWindow();
		ventanaRegistro.close();
	}

	/**
	 * Abre la ventana de registro con un tamaño adecuado y evita que se pueda
	 * redimensionar.
	 */
	@FXML
	private void abrirRegistro() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Registro.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 450, 500);

			Stage stage = new Stage();
			stage.setTitle("Registro de Usuario");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Muestra una alerta en pantalla con un mensaje personalizado.
	 */
	private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
		Alert alert = new Alert(tipo, mensaje);
		alert.showAndWait();
	}

	/**
	 * Cierra la ventana de registro y abre la pantalla de login. Aplica los estilos
	 * CSS a la nueva ventana.
	 */
	@FXML
	private void volverAlLogin() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Login.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 600, 400);

			// Aplicar el CSS global
			String cssPath = "/application/application.css";
			if (getClass().getResource(cssPath) != null) {
				scene.getStylesheets().clear();
				scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
			} else {
				System.err.println("No se encontró el archivo CSS: " + cssPath);
			}

			Stage stage = new Stage();
			stage.setTitle("Gestor de Contactos - Login");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();

			// Cierra la ventana de registro
			Stage ventanaRegistro = (Stage) txtCorreo.getScene().getWindow();
			ventanaRegistro.close();

		} catch (Exception e) {
			e.printStackTrace();
			mostrarAlerta(Alert.AlertType.ERROR, "Error al volver al Login.");
		}
	}
}
