/**
 * 
 */
/**
 * 
 */
module CalculadoraBinding {
	
	requires javafx.controls;
	requires javafx.fxml;
	
	opens com.calculadora.controladores to javafx.fxml;
	
	exports com.calculadora.controladores;
	exports com.calculadora;
	
	
	
}