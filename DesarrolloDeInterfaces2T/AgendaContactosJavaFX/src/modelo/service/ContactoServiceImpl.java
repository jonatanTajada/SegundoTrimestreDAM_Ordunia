package modelo.service;

import java.util.List;

import modelo.Contacto;
import modelo.dao.ContactoDAOImpl;

public class ContactoServiceImpl implements ContactoService {

	private final ContactoDAOImpl contactoDao;

	public ContactoServiceImpl() {
		this.contactoDao = new ContactoDAOImpl();
	}

	// Método para crear un nuevo contacto con validación
	public boolean crearContacto(Contacto contacto) {

		try {

			if (validarContacto(contacto) && validarUnicidad(contacto)) {
				contactoDao.crearContacto(contacto);
				return true;
			}

		} catch (IllegalArgumentException e) {
			System.err.println("⚠ Error de validación: " + e.getMessage());

		} catch (Exception e) {
			System.err.println("⚠ Error al guardar el contacto: " + e.getMessage());
		}

		return false;
	}

	// Método para obtener todos los contactos
	public List<Contacto> obtenerContactos() {
		return contactoDao.obtenerContactos();
	}

	public void eliminarTodosContactos() {
		contactoDao.eliminarTodos();
	}

	// Método para actualizar un contacto con validación
	public boolean actualizarContacto(Contacto contacto) {
		try {
			if (validarContacto(contacto) && validarUnicidad(contacto)) {
				contactoDao.actualizarContacto(contacto);
				return true; // Contacto actualizado con éxito
			}
		} catch (IllegalArgumentException e) {
			System.err.println("⚠ Error de validación: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("⚠ Error al actualizar el contacto: " + e.getMessage());
		}
		return false; // No se pudo actualizar el contacto
	}

	// Método para eliminar contacto
	public void eliminarContacto(int id) {
		contactoDao.eliminarContacto(id);
	}

	// Método para validar un contacto antes de insertarlo o actualizarlo
	private boolean validarContacto(Contacto contacto) {
		if (contacto.getNombre() == null || contacto.getNombre().isBlank()) {
			throw new IllegalArgumentException("El nombre del contacto no puede estar vacío.");
		}

		if (contacto.getCorreo() == null || !contacto.getCorreo().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
			throw new IllegalArgumentException("El correo electrónico no es válido.");
		}

		if (contacto.getTelefono() == null || !contacto.getTelefono().matches("^\\d{9}$")) {
			throw new IllegalArgumentException("El número de teléfono debe contener exactamente 9 dígitos.");
		}

		return true;
	}

	// Método para validar unicidad de correo y teléfono
	private boolean validarUnicidad(Contacto contacto) {
		List<Contacto> contactosExistentes = contactoDao.obtenerContactos();

		for (Contacto existente : contactosExistentes) {

			// Evitar conflicto con el propio contacto al editar
			if (existente.getId() != contacto.getId()) {

				if (existente.getCorreo().equalsIgnoreCase(contacto.getCorreo())) {
					throw new IllegalArgumentException("El correo ya está registrado: " + contacto.getCorreo());
				}

				if (existente.getTelefono().equals(contacto.getTelefono())) {
					throw new IllegalArgumentException(
							"El número de teléfono ya está registrado: " + contacto.getTelefono());
				}
			}
		}

		return true;
	}
}
