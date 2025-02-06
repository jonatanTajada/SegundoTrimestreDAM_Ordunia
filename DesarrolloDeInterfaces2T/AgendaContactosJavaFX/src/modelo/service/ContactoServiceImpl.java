package modelo.service;

import java.util.List;
import modelo.Contacto;
import modelo.dao.ContactoDAOImpl;

/**
 * Implementación del servicio para la gestión de contactos. Se encarga de la
 * validación y comunicación con la capa DAO.
 */
public class ContactoServiceImpl implements ContactoService {

	private final ContactoDAOImpl contactoDao;

	/**
	 * Constructor que inicializa la capa DAO de contactos.
	 */
	public ContactoServiceImpl() {
		this.contactoDao = new ContactoDAOImpl();
	}

	/**
	 * Crea un nuevo contacto después de validar los datos.
	 *
	 * @param contacto Objeto Contacto con los datos a registrar.
	 * @return true si el contacto fue creado con éxito, false en caso contrario.
	 */
	@Override
	public boolean crearContacto(Contacto contacto) {
		try {
			if (validarContacto(contacto) && validarUnicidad(contacto)) {
				contactoDao.crearContacto(contacto);
				return true;
			}
		} catch (IllegalArgumentException e) {
			System.err.println("Error de validación al crear contacto: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Error inesperado al guardar el contacto: " + e.getMessage());
		}
		return false;
	}

	/**
	 * Obtiene la lista de todos los contactos almacenados en la base de datos.
	 *
	 * @return Lista de objetos Contacto.
	 */
	@Override
	public List<Contacto> obtenerContactos() {
		return contactoDao.obtenerContactos();
	}

	/**
	 * Elimina todos los contactos de la base de datos.
	 */
	@Override
	public void eliminarTodosContactos() {
		contactoDao.eliminarTodos();
	}

	/**
	 * Actualiza un contacto existente después de validar sus datos.
	 *
	 * @param contacto Objeto Contacto con los datos actualizados.
	 * @return true si la actualización fue exitosa, false en caso contrario.
	 */
	@Override
	public boolean actualizarContacto(Contacto contacto) {
		try {
			System.out.println("Intentando actualizar contacto con ID: " + contacto.getId());

			if (validarContacto(contacto)) {
				contactoDao.actualizarContacto(contacto);
				System.out.println("Contacto actualizado con éxito.");
				return true;
			} else {
				System.err.println("No se pudo actualizar el contacto por fallos en la validación.");
			}
		} catch (IllegalArgumentException e) {
			System.err.println("Error de validación al actualizar contacto: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Error inesperado al actualizar el contacto: " + e.getMessage());
		}
		return false;
	}

	/**
	 * Elimina un contacto específico de la base de datos.
	 *
	 * @param id Identificador único del contacto a eliminar.
	 */
	@Override
	public void eliminarContacto(int id) {
		contactoDao.eliminarContacto(id);
	}

	/**
	 * Valida los datos de un contacto antes de guardarlo o actualizarlo.
	 *
	 * @param contacto Objeto Contacto a validar.
	 * @return true si los datos son válidos, de lo contrario lanza una excepción.
	 * @throws IllegalArgumentException si los datos del contacto no son correctos.
	 */
	private boolean validarContacto(Contacto contacto) {
		if (contacto.getNombre() == null || contacto.getNombre().isBlank()) {
			throw new IllegalArgumentException("El nombre del contacto no puede estar vacío.");
		}

		if (contacto.getCorreo() == null || !contacto.getCorreo().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			throw new IllegalArgumentException("El correo electrónico no es válido: " + contacto.getCorreo());
		}

		if (contacto.getTelefono() == null || !contacto.getTelefono().matches("^\\d{9}$")) {
			throw new IllegalArgumentException("El número de teléfono debe contener exactamente 9 dígitos.");
		}

		if (contacto.getLocalidad() == null || contacto.getLocalidad().isBlank()) {
			throw new IllegalArgumentException("La localidad es obligatoria.");
		}

		return true;
	}

	/**
	 * Verifica que el correo y el teléfono del contacto no estén ya registrados en
	 * la base de datos.
	 *
	 * @param contacto Objeto Contacto a verificar.
	 * @return true si los datos son únicos, de lo contrario lanza una excepción.
	 * @throws IllegalArgumentException si el correo o el teléfono ya están registrados.
	 */
	private boolean validarUnicidad(Contacto contacto) {
		List<Contacto> contactosExistentes = contactoDao.obtenerContactos();

		for (Contacto existente : contactosExistentes) {
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
