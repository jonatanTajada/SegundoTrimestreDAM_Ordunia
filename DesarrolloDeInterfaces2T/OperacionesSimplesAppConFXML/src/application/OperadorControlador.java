package application;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class OperadorControlador {

    @FXML private TextField operando1;
    @FXML private TextField operando2;
    @FXML private TextField resultado;
    
    @FXML private RadioButton rbSuma;
    @FXML private RadioButton rbResta;
    @FXML private RadioButton rbMultiplicacion;
    @FXML private RadioButton rbDivision;

    private ToggleGroup grupoOperaciones;

    @FXML
    public void initialize() {
    	
        //envolver los radio button con ToggleGroup
        grupoOperaciones = new ToggleGroup();
        
        rbSuma.setToggleGroup(grupoOperaciones);
        rbResta.setToggleGroup(grupoOperaciones);
        rbMultiplicacion.setToggleGroup(grupoOperaciones);
        rbDivision.setToggleGroup(grupoOperaciones);
    }

    @FXML
    public void operar() {
    	
        try {
        	
            double num1 = Double.parseDouble(operando1.getText());
            double num2 = Double.parseDouble(operando2.getText());
            double resultadoOperacion = 0;

            // realiza operacion segun seleccion
            if (rbSuma.isSelected()) {
            	
                resultadoOperacion = num1 + num2;
            } else if (rbResta.isSelected()) {
            	
                resultadoOperacion = num1 - num2;
            } else if (rbMultiplicacion.isSelected()) {
            	
                resultadoOperacion = num1 * num2;
            } else if (rbDivision.isSelected()) {
            	
                if (num2 != 0) {
                    resultadoOperacion = num1 / num2;
                } else {
                    resultado.setText("Error: División por 0");
                    return;
                }
            }
            
            resultado.setText(String.valueOf(resultadoOperacion));
        } catch (NumberFormatException e) {
            resultado.setText("Error: vuelva a intentarlo, recuerda, solo valores numericos, no puede estar vacio ningun campo: " + e.getMessage());
        }
    }
}
