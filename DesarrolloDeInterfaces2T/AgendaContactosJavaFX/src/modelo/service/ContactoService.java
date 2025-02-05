package modelo.service;

import modelo.Contacto;
import java.util.List;

/**
 * Interfaz que define los métodos para la gestión de contactos.
 */
public interface ContactoService {

    /**
     * Crea un nuevo contacto en la base de datos.
     *
     * @param contacto Objeto Contacto con los datos a registrar.
     * @return true si el contacto fue creado con éxito, false en caso contrario.
     */
    boolean crearContacto(Contacto contacto);

    /**
     * Obtiene la lista de todos los contactos almacenados en la base de datos.
     *
     * @return Lista de objetos Contacto.
     */
    List<Contacto> obtenerContactos();

    /**
     * Elimina todos los contactos de la base de datos.
     */
    void eliminarTodosContactos();

    /**
     * Actualiza la información de un contacto existente en la base de datos.
     *
     * @param contacto Objeto Contacto con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    boolean actualizarContacto(Contacto contacto);

    /**
     * Elimina un contacto específico de la base de datos.
     *
     * @param id Identificador único del contacto a eliminar.
     */
    void eliminarContacto(int id);
}
