package controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.Usuario;
import modelo.service.UsuarioService;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class LoginControlador {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    private void iniciarSesion() {
        String correo = txtCorreo.getText();
        String password = txtPassword.getText();

        Usuario usuario = usuarioService.autenticarUsuario(correo, password);
        if (usuario != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Inicio de sesión exitoso.");
            alert.showAndWait();
            abrirPantallaPrincipal();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Credenciales incorrectas.");
            alert.showAndWait();
        }
    }

    @FXML
    private void abrirRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Registro.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 450, 500); // ⬅ Tamaño ajustado

            Stage stage = new Stage();
            stage.setTitle("Registro de Usuario");
            stage.setScene(scene);
            stage.setResizable(false); // ⬅ Evita que se expanda de forma descontrolada
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirPantallaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Principal.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Gestor de Contactos");
            stage.setScene(scene);
            stage.show();

            // Cerrar la ventana de login
            ((Stage) txtCorreo.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 🔹 Nuevo Método: `abrirLogin()`
     * Se encarga de abrir la ventana de Login con un tamaño adecuado
     * y evita que se pueda redimensionar.
     */
    @FXML
    private void abrirLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 450, 500); // ⬅ Tamaño ajustado

            Stage stage = new Stage();
            stage.setTitle("Iniciar Sesión");
            stage.setScene(scene);
            stage.setResizable(false); // ⬅ Evita que se expanda de forma descontrolada
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
