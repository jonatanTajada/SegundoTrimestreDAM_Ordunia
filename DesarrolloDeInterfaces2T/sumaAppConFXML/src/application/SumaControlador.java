package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SumaControlador {

	  @FXML private TextField operando1;
	  @FXML private TextField operando2;
	  @FXML private Label resultado;

	
	  @FXML
	public void sumar() {

		try {

			double num1 = Double.parseDouble(operando1.getText());
			double num2 = Double.parseDouble(operando2.getText());

			resultado.setText("Resultado: " + (num1 + num2));

		} catch (NumberFormatException e) {
			resultado.setText("Error, vuelva a intentarlo, introduciendo valores numericos: " + e.getMessage());
		}

	}

}
