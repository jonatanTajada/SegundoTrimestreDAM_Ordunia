package modelo.service;

import java.util.List;

import modelo.Contacto;
import modelo.dao.ContactoDAOImpl;

public class ContactoService {

	private final ContactoDAOImpl contactoDao;

	public ContactoService() {
		this.contactoDao = new ContactoDAOImpl();
	}

	// metodo para crear nuevo contacto con validacion
	public void crearContacto(Contacto contacto) {

		if (validarContacto(contacto)) {
			contactoDao.crearContacto(contacto);
		}
	}

	// metodo para obtener todos los contactos
	public List<Contacto> obtenerContactos() {
		return contactoDao.obtenerContactos();
	}

	// metodo para actualizar contacto con validacion
	public void actualizarContacto(Contacto contacto) {

		if (validarContacto(contacto)) {
			contactoDao.actualizarContacto(contacto);
		}
	}

	// metodo para eliminar contacto
	public void eliminarContacto(int id) {
		contactoDao.eliminarContacto(id);
	}

	// metodo para validar un contacto antes de insertalo o actualizarlo
	private boolean validarContacto(Contacto contacto) {

		if (contacto.getNombre() == null || contacto.getNombre().isBlank() || contacto.getNombre().isEmpty()) {
			throw new IllegalAccessError("El nombre del contacto no puede estar vacio.");
		}

		if (contacto.getCorreo() == null || !contacto.getCorreo().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
			throw new IllegalArgumentException("El correo electronico no es valido.");
		}
		
		if (contacto.getTelefono() == null || !contacto.getTelefono().matches("^\\d{9}$")) {
		    throw new IllegalArgumentException("El número de teléfono debe contener exactamente 9 dígitos.");
		}
		
		return true;
	}

}
