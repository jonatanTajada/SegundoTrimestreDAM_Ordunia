package controlador;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.service.UsuarioServiceImpl;

/**
 * Controlador para la pantalla de inicio de sesión. Permite validar
 * credenciales, abrir la pantalla principal y redirigir al registro de
 * usuarios.
 */
public class LoginControlador {

	@FXML
	private TextField txtCorreo;

	@FXML
	private PasswordField txtPassword;

	private final UsuarioServiceImpl usuarioService = new UsuarioServiceImpl();

	/**
	 * Inicia sesión verificando las credenciales del usuario. Si son válidas, abre
	 * la ventana principal y cierra la actual.
	 */
	@FXML
	private void iniciarSesion() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Principal.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 800, 600);

			// Aplicar el CSS azul a la escena
			aplicarCSS(scene);

			Stage stage = new Stage();
			stage.setTitle("Gestor de Contactos");
			stage.setScene(scene);
			stage.show();

			// Cerrar la ventana de login
			Stage actualStage = (Stage) txtCorreo.getScene().getWindow();
			actualStage.close();

		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(Alert.AlertType.ERROR, "Error al abrir la agenda: " + e.getMessage());
			alert.showAndWait();
		}
	}

	/**
	 * Abre la ventana de registro de usuarios.
	 */
	@FXML
	private void abrirRegistro() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Registro.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 600, 400);

			// Aplicar el CSS manualmente
			String cssPath = "/application/application.css";
			if (getClass().getResource(cssPath) != null) {
				scene.getStylesheets().clear();
				scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
			} else {
				System.err.println("No se encontró el archivo CSS: " + cssPath);
			}

			Stage stage = new Stage();
			stage.setTitle("Registro de Usuario");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Valida las credenciales del usuario verificando en la base de datos.
	 * 
	 * @return true si las credenciales son correctas, false si son incorrectas.
	 */
	private boolean validarCredenciales() {
		String correo = txtCorreo.getText();
		String password = txtPassword.getText();
		return usuarioService.validarUsuario(correo, password);
	}

	/**
	 * Aplica la hoja de estilos CSS a la escena.
	 * 
	 * @param scene La escena donde se aplicará el estilo.
	 */
	private void aplicarCSS(Scene scene) {
		String cssPath = "/application/application.css";
		if (getClass().getResource(cssPath) != null) {
			scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
		} else {
			System.err.println("No se encontró el archivo CSS: " + cssPath);
		}
	}
}
