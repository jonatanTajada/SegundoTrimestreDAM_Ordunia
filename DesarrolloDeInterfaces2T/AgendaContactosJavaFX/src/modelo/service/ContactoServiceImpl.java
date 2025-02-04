package modelo.service;

import java.util.List;
import modelo.Contacto;
import modelo.dao.ContactoDAOImpl;

public class ContactoServiceImpl implements ContactoService {

    private final ContactoDAOImpl contactoDao;

    public ContactoServiceImpl() {
        this.contactoDao = new ContactoDAOImpl();
    }

    // ✅ Método para crear un nuevo contacto con validación
    public boolean crearContacto(Contacto contacto) {
        try {
            if (validarContacto(contacto) && validarUnicidad(contacto)) {
                contactoDao.crearContacto(contacto);
                return true;
            }
        } catch (IllegalArgumentException e) {
            System.err.println("⚠ Error de validación al crear contacto: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("⚠ Error inesperado al guardar el contacto: " + e.getMessage());
        }
        return false;
    }

    // ✅ Método para obtener todos los contactos
    public List<Contacto> obtenerContactos() {
        return contactoDao.obtenerContactos();
    }

    public void eliminarTodosContactos() {
        contactoDao.eliminarTodos();
    }

    // ✅ Método para actualizar un contacto con validación
    public boolean actualizarContacto(Contacto contacto) {
        try {
            System.out.println("📌 Intentando actualizar contacto con ID: " + contacto.getId());

            if (validarContacto(contacto)) {
                contactoDao.actualizarContacto(contacto);
                System.out.println("✅ Contacto actualizado con éxito.");
                return true;
            } else {
                System.err.println("❌ No se pudo actualizar el contacto por fallos en la validación.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("⚠ Error de validación al actualizar contacto: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Error inesperado al actualizar el contacto: " + e.getMessage());
        }
        return false;
    }

    // ✅ Método para eliminar contacto
    public void eliminarContacto(int id) {
        contactoDao.eliminarContacto(id);
    }

    // ✅ Método mejorado para validar los datos del contacto antes de guardarlo o actualizarlo
    private boolean validarContacto(Contacto contacto) {
        if (contacto.getNombre() == null || contacto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del contacto no puede estar vacío.");
        }

        // 🔹 Validar que el correo tiene un formato correcto
        if (contacto.getCorreo() == null || !contacto.getCorreo().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("El correo electrónico no es válido: " + contacto.getCorreo());
        }

        if (contacto.getTelefono() == null || !contacto.getTelefono().matches("^\\d{9}$")) {
            throw new IllegalArgumentException("El número de teléfono debe contener exactamente 9 dígitos.");
        }

        // 🔹 Validar que la localidad no sea nula o vacía
        if (contacto.getLocalidad() == null || contacto.getLocalidad().isBlank()) {
            throw new IllegalArgumentException("La localidad es obligatoria.");
        }

        return true;
    }

    // ✅ Método optimizado para validar la unicidad de correo y teléfono sin bloquear ediciones
    private boolean validarUnicidad(Contacto contacto) {
        List<Contacto> contactosExistentes = contactoDao.obtenerContactos();

        for (Contacto existente : contactosExistentes) {
            if (existente.getId() != contacto.getId()) { // 🔹 Permitir que se edite el mismo contacto sin error
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
