package modelo.service;

import modelo.Usuario;
import modelo.dao.UsuarioDAO;
import modelo.dao.UsuarioDAOImpl;

/**
 * Implementación de la interfaz {@link UsuarioService}.
 * Proporciona métodos para registrar y autenticar usuarios,
 * incluyendo validaciones básicas de datos.
 */
public class UsuarioServiceImpl implements UsuarioService {
    
    private final UsuarioDAO usuarioDAO;
    
    /**
     * Constructor que inicializa la instancia de {@link UsuarioDAO}.
     */
    public UsuarioServiceImpl() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    /**
     * Registra un nuevo usuario en el sistema después de validar los datos ingresados.
     *
     * @param correo   El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return {@code true} si el usuario se registró correctamente, {@code false} si hubo errores.
     */
    public boolean registrarUsuario(String correo, String password) {
        if (correo == null || correo.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("⚠ Correo y contraseña no pueden estar vacíos.");
            return false;
        }

        // Validación básica de formato de correo
        if (!correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            System.out.println("⚠ El correo ingresado no es válido.");
            return false;
        }

        Usuario usuario = new Usuario(0, correo, password);
        return usuarioDAO.registrarUsuario(usuario);
    }

    /**
     * Autentica a un usuario verificando sus credenciales en la base de datos.
     *
     * @param correo   El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return Un objeto {@link Usuario} si la autenticación es exitosa, {@code null} si las credenciales son incorrectas.
     */
    public Usuario autenticarUsuario(String correo, String password) {
        if (correo == null || correo.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("⚠ Correo y contraseña no pueden estar vacíos.");
            return null;
        }

        return usuarioDAO.autenticarUsuario(correo, password);
    }
    
    /**
     * Valida si un usuario con las credenciales ingresadas existe en la base de datos.
     *
     * @param correo   El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return {@code true} si el usuario existe y las credenciales coinciden, {@code false} en caso contrario.
     */
    public boolean validarUsuario(String correo, String password) {
        if (correo == null || correo.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("⚠ Correo y contraseña no pueden estar vacíos.");
            return false;
        }

        Usuario usuario = usuarioDAO.buscarPorCorreo(correo);
        return usuario != null && usuario.getPassword().equals(password);
        
    }
}

