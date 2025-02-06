package modelo.dao;

import modelo.Usuario;
import java.sql.*;

/**
 * Implementación de la interfaz UsuarioDAO para gestionar usuarios en la base
 * de datos.
 */
public class UsuarioDAOImpl implements UsuarioDAO {

	private static final String URL = "jdbc:mysql://localhost:3306/AgendaDB";
	private static final String USER = "root";
	private static final String PASSWORD = "1234";

	/**
	 * Registra un nuevo usuario en la base de datos.
	 *
	 * @param usuario Objeto Usuario con los datos a registrar.
	 * @return true si el usuario fue registrado con éxito, false en caso contrario.
	 */
	@Override
	public boolean registrarUsuario(Usuario usuario) {
		String sql = "INSERT INTO usuarios (correo, password) VALUES (?, ?)";

		try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setString(1, usuario.getCorreo());
			ps.setString(2, usuario.getPassword()); 

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Autentica un usuario verificando sus credenciales en la base de datos.
	 *
	 * @param correo   Correo electrónico del usuario.
	 * @param password Contraseña del usuario.
	 * @return Un objeto Usuario si las credenciales son correctas, null si no
	 *         coinciden.
	 */
	@Override
	public Usuario autenticarUsuario(String correo, String password) {
		String sql = "SELECT * FROM usuarios WHERE correo = ? AND password = ?";

		try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setString(1, correo);
			ps.setString(2, password); 

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Usuario(rs.getInt("id"), rs.getString("correo"), rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Busca un usuario en la base de datos por su correo electrónico.
	 *
	 * @param correo Correo electrónico del usuario a buscar.
	 * @return Un objeto Usuario si se encuentra en la base de datos, null si no existe.        
	 */
	@Override
	public Usuario buscarPorCorreo(String correo) {
		String sql = "SELECT id, correo, password FROM usuarios WHERE correo = ?";

		Usuario usuario = null;

		try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setString(1, correo);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					usuario = new Usuario(rs.getInt("id"), rs.getString("correo"), rs.getString("password"));
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al buscar usuario por correo: " + e.getMessage());
			e.printStackTrace();
		}

		return usuario;
	}
}
