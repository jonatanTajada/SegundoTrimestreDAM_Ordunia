package modelo.service;

import modelo.Contacto;
import java.util.List;

public interface ContactoService {

    boolean crearContacto(Contacto contacto);

    List<Contacto> obtenerContactos();

    void eliminarTodosContactos();

    boolean actualizarContacto(Contacto contacto);

    void eliminarContacto(int id);
}
