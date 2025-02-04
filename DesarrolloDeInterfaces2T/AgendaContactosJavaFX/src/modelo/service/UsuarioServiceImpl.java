package modelo.service;

import modelo.Usuario;
import modelo.dao.UsuarioDAO;
import modelo.dao.UsuarioDAOImpl;

public class UsuarioServiceImpl implements UsuarioService {
    
    private final UsuarioDAO usuarioDAO;
    

    public UsuarioServiceImpl() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    // ✅ Registrar un usuario con validación mejorada
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

    // ✅ Autenticar usuario con validación mejorada
    public Usuario autenticarUsuario(String correo, String password) {
        if (correo == null || correo.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("⚠ Correo y contraseña no pueden estar vacíos.");
            return null;
        }

        return usuarioDAO.autenticarUsuario(correo, password);
    }
    
    public boolean validarUsuario(String correo, String password) {
        if (correo == null || correo.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("⚠ Correo y contraseña no pueden estar vacíos.");
            return false;
        }

        Usuario usuario = usuarioDAO.buscarPorCorreo(correo);
        return usuario != null && usuario.getPassword().equals(password);
    }
}
