package modelo.dao;

import java.util.List;
import modelo.Contacto;

/**
 * Interfaz que define las operaciones CRUD para la entidad Contacto.
 */
public interface ContactoDAO {
    
    /**
     * Guarda un nuevo contacto en la base de datos.
     *
     * @param contacto Objeto Contacto a guardar.
     */
    void crearContacto(Contacto contacto);

    /**
     * Obtiene la lista de todos los contactos almacenados en la base de datos.
     *
     * @return Lista de contactos.
     */
    List<Contacto> obtenerContactos();

    /**
     * Actualiza los datos de un contacto existente en la base de datos.
     *
     * @param contacto Objeto Contacto con los nuevos valores.
     */
    void actualizarContacto(Contacto contacto);

    /**
     * Elimina un contacto de la base de datos mediante su ID.
     *
     * @param id Identificador del contacto a eliminar.
     */
    void eliminarContacto(int id);

    /**
     * Elimina todos los contactos almacenados en la base de datos.
     */
    void eliminarTodos();
}
