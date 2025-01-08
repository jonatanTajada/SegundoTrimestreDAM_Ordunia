package modelo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class Persona {
	private SimpleStringProperty nombre;
	private SimpleStringProperty apellidos;
	private SimpleIntegerProperty edad;

	public Persona(String nombre, String apellidos, int edad) {
		this.nombre = new SimpleStringProperty(nombre);
		this.apellidos = new SimpleStringProperty(apellidos);
		this.edad = new SimpleIntegerProperty(edad);
	}

	// Getters
	public String getNombre() {
		return nombre.get();
	}

	public String getApellidos() {
		return apellidos.get();
	}

	public int getEdad() {
		return edad.get();
	}

	// Setters
	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public void setApellidos(String apellidos) {
		this.apellidos.set(apellidos);
	}

	public void setEdad(int edad) {
		this.edad.set(edad);
	}

	// Métodos Property para el enlace con la tabla
	public StringProperty nombreProperty() {
		return nombre;
	}

	public StringProperty apellidosProperty() {
		return apellidos;
	}

	public IntegerProperty edadProperty() {
		return edad;
	}
}
