package modelo.service;

import modelo.Usuario;

/**
 * Interfaz que define los servicios relacionados con la gestión de usuarios.
 * Proporciona métodos para el registro y autenticación de usuarios.
 */
public interface UsuarioService {
    
    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param correo   El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return {@code true} si el usuario se registró correctamente, {@code false} en caso contrario.
     */
    boolean registrarUsuario(String correo, String password);

    /**
     * Autentica a un usuario verificando sus credenciales.
     *
     * @param correo   El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return Un objeto {@link Usuario} si las credenciales son válidas, {@code null} si no lo son.
     */
    Usuario autenticarUsuario(String correo, String password);
}
