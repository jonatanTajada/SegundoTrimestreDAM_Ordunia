package modelo.dao;

import modelo.Usuario;

/**
 * Interfaz que define las operaciones de acceso a datos para la entidad Usuario.
 */
public interface UsuarioDAO {

    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * @param usuario Objeto Usuario con los datos a registrar.
     * @return true si el usuario fue registrado con éxito, false si ya existe o hubo un error.
     */
    boolean registrarUsuario(Usuario usuario);

    
    /**
     * Autentica un usuario verificando sus credenciales en la base de datos.
     *
     * @param correo   Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Un objeto Usuario si las credenciales son correctas, null si no coinciden.
     */
    Usuario autenticarUsuario(String correo, String password);

    
    /**
     * Busca un usuario en la base de datos por su correo electrónico.
     *
     * @param correo Correo electrónico del usuario a buscar.
     * @return Un objeto Usuario si se encuentra en la base de datos, null si no existe.
     */
    Usuario buscarPorCorreo(String correo);
    
    
}
