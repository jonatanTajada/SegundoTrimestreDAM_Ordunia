package modelo.dao;

import modelo.Usuario;

public interface UsuarioDAO {
    
    boolean registrarUsuario(Usuario usuario);
    
    Usuario autenticarUsuario(String correo, String password);
    
    Usuario buscarPorCorreo(String correo);
}
