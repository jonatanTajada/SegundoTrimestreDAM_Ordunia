package modelo.service;

import java.util.List;
import modelo.Contacto;
import modelo.dao.ContactoDAOImpl;

public class ContactoService {

    private final ContactoDAOImpl contactoDao;

    public ContactoService() {
        this.contactoDao = new ContactoDAOImpl();
    }

    // Metodo para crear nuevo contacto con validacion
    public void crearContacto(Contacto contacto) {
        if (validarContacto(contacto)) {
            contactoDao.crearContacto(contacto);
        }
    }


    // Metodo para obtener todos los contactos
    public List<Contacto> obtenerContactos() {
        return contactoDao.obtenerContactos();
    }

    // Metodo para actualizar contacto con validacion
    public void actualizarContacto(Contacto contacto) {

        if (validarContacto(contacto) && validarUnicidad(contacto)) {
            contactoDao.actualizarContacto(contacto);
        }
    }

    // Metodo para eliminar contacto
    public void eliminarContacto(int id) {
        contactoDao.eliminarContacto(id);
    }

    // Metodo para validar un contacto antes de insertarlo o actualizarlo
    private boolean validarContacto(Contacto contacto) {

        if (contacto.getNombre() == null || contacto.getNombre().isBlank() || contacto.getNombre().isEmpty()) {
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

    // Metodo para validar unicidad de correo y telefono
    private boolean validarUnicidad(Contacto contacto) {
        List<Contacto> contactosExistentes = contactoDao.obtenerContactos();

        for (Contacto existente : contactosExistentes) {
            // Evitar conflicto con el propio contacto al editar
            if (existente.getId() != contacto.getId()) {
                if (existente.getCorreo().equalsIgnoreCase(contacto.getCorreo())) {
                    throw new IllegalArgumentException("El correo ya está registrado: " + contacto.getCorreo());
                }
                if (existente.getTelefono().equals(contacto.getTelefono())) {
                    throw new IllegalArgumentException("El número de teléfono ya está registrado: " + contacto.getTelefono());
                }
            }
        }

        return true;
    }
}
