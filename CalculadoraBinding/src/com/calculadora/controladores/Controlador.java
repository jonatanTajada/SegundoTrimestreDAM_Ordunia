package com.calculadora.controladores;



import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controlador {
	
	 @FXML
	    private TextField campoValor1;

	    @FXML
	    private TextField campoValor2;

	    @FXML
	    private Label etiquetaResultado;

    private final DoubleProperty valor1 = new SimpleDoubleProperty();
    private final DoubleProperty valor2 = new SimpleDoubleProperty();
    private final DoubleProperty resultado = new SimpleDoubleProperty();

    
    public Controlador() {
        // Bindeo bidireccional para calcular automáticamente el resultado
        resultado.bind(valor1.add(valor2));
    }

   
    @FXML
    public void initialize() {
    	
        // Enlazar las propiedades con los campos del formulario
        campoValor1.textProperty().addListener((obs, oldText, newText) -> {
        	 
            try {
                valor1.set(Double.parseDouble(newText));
            } catch (NumberFormatException e) {
                valor1.set(0);
            }
        });

        campoValor2.textProperty().addListener((obs, oldText, newText) -> {
            try {
                valor2.set(Double.parseDouble(newText));
            } catch (NumberFormatException e) {
                valor2.set(0);
            }
        });

        etiquetaResultado.textProperty().bind(resultado.asString("%.2f"));
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}









//---------------------------------------------------------------------------------------------------------------------------------------------------------------
//
//package com.calculadora.controladores;
//
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//
//public class Controlador {
//	
//	private final StringProperty valor1 = new SimpleStringProperty();
//	private final StringProperty valor2 = new SimpleStringProperty();
//	private final StringProperty resultado = new SimpleStringProperty();
//	
//	public Controlador() {
//		
//		// Bindeo bidireccional para actualizar automáticamente el resultado
//		resultado.bind(valor1.concat(" + ").concat(valor2).concat(" = ")
//				.concat(valor1.concat(valor2)));
//	}
//	
//	public StringProperty valor1Property() {
//		return valor1;
//	}
//	
//	public StringProperty valor2Property() {
//		return valor2;
//	}
//	
//	public StringProperty resultadoProperty() {
//		return resultado;
//	}
//}
















