module ejemploTeoria1ConFXML {
	
	requires javafx.controls;
	requires javafx.fxml;

	opens com.ejemploTeoria1ConFXML.controladores to javafx.fxml;

	exports com.ejemploTeoria1ConFXML;
	
}
