package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.CalculadoraModelo;

public class CalculadoraControlador {

    @FXML
    private TextField txtCampoNumero1;

    @FXML
    private TextField txtCampoNumero2;

    @FXML
    private Label lblResultado;

    private final CalculadoraModelo modelo = new CalculadoraModelo();

    @FXML
    private void sumar() {
        realizarOperacion("sumar");
    }

    @FXML
    private void restar() {
        realizarOperacion("restar");
    }

    @FXML
    private void multiplicar() {
        realizarOperacion("multiplicar");
    }

    @FXML
    private void dividir() {
        realizarOperacion("dividir");
    }

    private void realizarOperacion(String operacion) {
        try {
            double numero1 = Double.parseDouble(txtCampoNumero1.getText());
            double numero2 = Double.parseDouble(txtCampoNumero2.getText());

            double resultado = switch (operacion) {
                case "sumar" -> modelo.sumar(numero1, numero2);
                case "restar" -> modelo.restar(numero1, numero2);
                case "multiplicar" -> modelo.multiplicar(numero1, numero2);
                case "dividir" -> modelo.dividir(numero1, numero2);
                default -> 0;
            };

            lblResultado.setText("Resultado: " + resultado);

        } catch (NumberFormatException e) {
            lblResultado.setText("Error: Entrada inválida");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            lblResultado.setText("Error: " + e.getMessage());
        }
    }
}
