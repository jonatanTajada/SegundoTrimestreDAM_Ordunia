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

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmarPassword;

    private final UsuarioServiceImpl usuarioService = new UsuarioServiceImpl();

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

    @FXML
    private void abrirLogin() {
        // Cierra la ventana actual (Registro)
        Stage ventanaRegistro = (Stage) txtCorreo.getScene().getWindow();
        ventanaRegistro.close();
    }

    /**
     * 🔹 Nuevo Método: `abrirRegistro()`
     * Se encarga de abrir la ventana de registro con un tamaño adecuado
     * y evita que se pueda redimensionar.
     */
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

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo, mensaje);
        alert.showAndWait();
    }
}
