package modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contacto {

	private final StringProperty nombre;
	private final StringProperty correo;
	private final StringProperty telefono;
	private String imagen;
	private String sitioWeb;
	private int id;

	public Contacto(int id, String nombre, String correo, String telefono, String imagen, String sitioWeb) {
		this.id = id;
		this.nombre = new SimpleStringProperty(nombre);
		this.correo = new SimpleStringProperty(correo);
		this.telefono = new SimpleStringProperty(telefono);
		this.imagen = imagen;
		this.sitioWeb = sitioWeb;
	}

	//getter y setter
	public StringProperty nombreProperty() {
		return nombre;
	}

	public String getNombre() {
		return nombre.get();
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public StringProperty correoProperty() {
		return correo;
	}

	public String getCorreo() {
		return correo.get();
	}

	public void setCorreo(String correo) {
		this.correo.set(correo);
	}

	public StringProperty telefonoProperty() {
		return telefono;
	}

	public String getTelefono() {
		return telefono.get();
	}

	public void setTelefono(String telefono) {
		this.telefono.set(telefono);
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getSitioWeb() {
		return sitioWeb;
	}

	public void setSitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
