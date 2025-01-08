package controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.Persona;

public class PersonaControlador {

	@FXML
	private TextField nombreField;
	@FXML
	private TextField apellidosField;
	@FXML
	private TextField edadField;
	@FXML
	private TableView<Persona> tablaPersonas;
	@FXML
	private TableColumn<Persona, String> columnaNombre;
	@FXML
	private TableColumn<Persona, String> columnaApellidos;
	@FXML
	private TableColumn<Persona, Integer> columnaEdad;

	private ObservableList<Persona> listaPersonas;

	@FXML
	public void initialize() {
		// Inicializar lista de personas
		listaPersonas = FXCollections.observableArrayList();
		tablaPersonas.setItems(listaPersonas);

		// Enlazar columnas con las propiedades de la clase Persona
		columnaNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		columnaApellidos.setCellValueFactory(cellData -> cellData.getValue().apellidosProperty());
		columnaEdad.setCellValueFactory(cellData -> cellData.getValue().edadProperty().asObject());
	}

	@FXML
	public void agregarPersona() {
		try {
			String nombre = nombreField.getText();
			String apellidos = apellidosField.getText();
			int edad = Integer.parseInt(edadField.getText());

			Persona nuevaPersona = new Persona(nombre, apellidos, edad);
			listaPersonas.add(nuevaPersona);

			nombreField.clear();
			apellidosField.clear();
			edadField.clear();
		} catch (NumberFormatException e) {
			System.out.println("Edad inválida");
		}
	}
}
