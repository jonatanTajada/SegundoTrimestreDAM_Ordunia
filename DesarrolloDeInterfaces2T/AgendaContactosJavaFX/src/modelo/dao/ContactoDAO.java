package modelo.dao;

import java.util.List;

import modelo.Contacto;

public interface ContactoDAO {
	
	void crearContacto(Contacto contacto);
	List<Contacto> obtenerContactos();
	void actualizarContacto(Contacto contacto);
	void eliminarContacto(int id);

}
