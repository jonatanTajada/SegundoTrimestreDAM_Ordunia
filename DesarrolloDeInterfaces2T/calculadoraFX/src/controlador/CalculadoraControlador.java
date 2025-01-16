package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.CalculadoraModelo;

public class CalculadoraControlador {

	@FXML
	private TextField campoNumero1;

	@FXML
	private TextField campoNumero2;

	@FXML
	private Label resultado;

	private CalculadoraModelo modelo = new CalculadoraModelo();

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
			
			double num1 = Double.parseDouble(campoNumero1.getText());
			double num2 = Double.parseDouble(campoNumero2.getText());
			
			double resultadoOperacion = switch (operacion) {
			
			case "sumar" -> modelo.sumar(num1, num2);
			case "restar" -> modelo.restar(num1, num2);
			case "multiplicar" -> modelo.multiplicar(num1, num2);
			case "dividir" -> modelo.dividir(num1, num2);
			default -> 0;
			
			};
			
			resultado.setText("Resultado: " + resultadoOperacion);
		} catch (NumberFormatException e) {
			
			resultado.setText("Error: Entrada inválida");
		} catch (IllegalArgumentException e) {
			
			resultado.setText("Error: " + e.getMessage());
		}
	}

}
