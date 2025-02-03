package modelo.service;

import modelo.Usuario;
import modelo.dao.UsuarioDAO;
import modelo.dao.UsuarioDAOImpl;

public class UsuarioService {
	
    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    // Registrar un usuario
    public boolean registrarUsuario(String correo, String password) {
        if (correo.isBlank() || password.isBlank()) {
            System.out.println("Correo y contraseña no pueden estar vacíos.");
            return false;
        }

        Usuario usuario = new Usuario(0, correo, password);
        return usuarioDAO.registrarUsuario(usuario);
    }

    // Autenticar usuario
    public Usuario autenticarUsuario(String correo, String password) {
        if (correo.isBlank() || password.isBlank()) {
            System.out.println("Correo y contraseña no pueden estar vacíos.");
            return null;
        }

        return usuarioDAO.autenticarUsuario(correo, password);
    }
}
