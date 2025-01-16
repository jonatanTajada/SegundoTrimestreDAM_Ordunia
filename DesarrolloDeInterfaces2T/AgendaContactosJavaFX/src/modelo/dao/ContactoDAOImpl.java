package modelo.dao;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import modelo.Contacto;

public class ContactoDAOImpl implements ContactoDAO {

	// conexion a la base de datos:AgendaDB
	private static final String URL = "jdbc:mysql://localhost:3306/AgendaDB";
	private static final String USER = "root";
	private static final String PASSWORD = "1234";

	@Override
	public void crearContacto(Contacto contacto) {

		String sql = "INSERT INTO contactos (nombre, correo, telefono, imagen, sitioWeb) VALUES (?, ?, ?, ?, ?)";

		try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conexion.prepareStatement(sql)) {

			// asignar valores a los parametros
			ps.setString(1, contacto.getNombre());
			ps.setString(2, contacto.getCorreo());
			ps.setString(3, contacto.getTelefono());
			ps.setString(4, contacto.getImagen());
			ps.setString(5, contacto.getSitioWeb());

			// ejecutar y mostrar mensaje consulta
			ps.executeUpdate();

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Exito");
			alert.setHeaderText("Contacto añadido");
			alert.setContentText("El contacto fue añadido con éxito.");
			alert.showAndWait();
		} catch (SQLException e) {

			// Mostrar mensaje de error en una ventana emergente
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error al crear el contacto");
			alert.setContentText("No se pudo guardar el contacto: " + e.getMessage());
			alert.showAndWait();
		}

	}

	@Override
	public List<Contacto> obtenerContactos() {

		String sql = "SELECT * FROM contactos";
		List<Contacto> listaContactos = new ArrayList<Contacto>();

		try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = conexion.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {

				Contacto contacto = new Contacto(rs.getInt("id"), rs.getString("nombre"), rs.getString("correo"),
						rs.getString("telefono"), rs.getString("imagen"), rs.getString("sitioWeb"));

				listaContactos.add(contacto);
			}
		} catch (SQLException e) {

			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setTitle("Error");
			alerta.setHeaderText("Error al obtener contactos");
			alerta.setContentText("No se pudieron recuperar los contactos: " + e.getMessage());
			alerta.showAndWait();
		}

		return listaContactos;
	}

	
	//implementacion de metodos CRUD
	@Override
	public void actualizarContacto(Contacto contacto) {

		String sql = "UPDATE contactos SET nombre = ?, correo = ?, telefono = ?, imagen = ?, sitioWeb = ? WHERE id = ?";

		try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
			 PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setString(1, contacto.getNombre());
			ps.setString(2, contacto.getCorreo());
			ps.setString(3, contacto.getTelefono());
			ps.setString(4, contacto.getImagen());
			ps.setString(5, contacto.getSitioWeb());
			ps.setInt(6, contacto.getId());

			int filasActualizadas = ps.executeUpdate();

			// Comprobar si se actualizó el contacto
			if (filasActualizadas > 0) {

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Éxito");
				alert.setHeaderText("Contacto actualizado");
				alert.setContentText("El contacto fue actualizado correctamente.");
				alert.showAndWait();
			} else {

				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Advertencia");
				alert.setHeaderText("Contacto no encontrado");
				alert.setContentText("No se encontró ningún contacto con el ID especificado.");
				alert.showAndWait();
			}

		} catch (SQLException e) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error al actualizar el contacto");
			alert.setContentText("No se pudo actualizar el contacto: " + e.getMessage());
			alert.showAndWait();
		}

	}

	@Override
	public void eliminarContacto(int id) {

		String sql = "DELETE FROM contactos WHERE id = ?";

		try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setInt(1, id);

			int filasEliminadas = ps.executeUpdate();

			if (filasEliminadas > 0) {

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Exito");
				alert.setHeaderText("Contacto eliminado");
				alert.setContentText("El contacto fue eliminado correctamente.");
				alert.showAndWait();

			} else {

				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Advertencia");
				alert.setHeaderText("Contacto no encontrado");
				alert.setContentText("No se encontró ningún contacto con el ID especificado.");
				alert.showAndWait();
			}

		} catch (SQLException e) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error al eliminar el contacto");
			alert.setContentText("No se pudo eliminar el contacto: " + e.getMessage());
			alert.showAndWait();
		}
	}

}
