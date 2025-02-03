package modelo.dao;

import modelo.Usuario;
import java.sql.*;

public class UsuarioDAOImpl implements UsuarioDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/AgendaDB";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    @Override
    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (correo, password) VALUES (?, ?)";

        try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario.getCorreo());
            ps.setString(2, usuario.getPassword()); // Por ahora, almacenamos la contraseña sin cifrar

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Usuario autenticarUsuario(String correo, String password) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND password = ?";

        try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, password); // Aquí en el futuro usaremos una versión cifrada

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Usuario(rs.getInt("id"), rs.getString("correo"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
