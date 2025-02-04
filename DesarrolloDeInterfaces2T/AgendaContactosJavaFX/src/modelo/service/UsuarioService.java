package modelo.service;

import modelo.Usuario;

public interface UsuarioService {

    boolean registrarUsuario(String correo, String password);

    
    Usuario autenticarUsuario(String correo, String password);
    
}
