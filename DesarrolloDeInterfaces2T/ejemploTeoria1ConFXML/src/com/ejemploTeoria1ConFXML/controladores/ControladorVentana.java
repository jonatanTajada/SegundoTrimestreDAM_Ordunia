package com.ejemploTeoria1ConFXML.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ControladorVentana {

	
	@FXML
	private TextField texto;
	
	@FXML
	private Button boton;
	
	@FXML
	public void saludar() {
		
		String nombre = texto.getText();
		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("Saludo personalizado");
		alerta.setHeaderText(null);
		alerta.setContentText("Hola mundo: " + nombre);
		alerta.show();		
	}
	
	
	
	
	
	
	
	
	
}
