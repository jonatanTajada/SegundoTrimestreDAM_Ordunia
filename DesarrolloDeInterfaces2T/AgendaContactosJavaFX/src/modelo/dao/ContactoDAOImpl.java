package modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Contacto;

public class ContactoDAOImpl implements ContactoDAO {
    
    private static final String URL = "jdbc:mysql://localhost:3306/AgendaDB";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    @Override
    public void crearContacto(Contacto contacto) {
        if (contacto.getNombre().isEmpty() || contacto.getCorreo().isEmpty() || 
            contacto.getTelefono().isEmpty() || contacto.getLocalidad() == null || contacto.getLocalidad().isEmpty()) {
            System.err.println("❌ No se pudo guardar el contacto: datos incompletos.");
            return;
        }

        String sql = "INSERT INTO contactos (nombre, telefono, correo, localidad, imagen, sitioWeb) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, contacto.getNombre());
            ps.setString(2, contacto.getTelefono());
            ps.setString(3, contacto.getCorreo());
            ps.setString(4, contacto.getLocalidad());
            ps.setString(5, contacto.getImagen() != null ? contacto.getImagen() : "No disponible");
            ps.setString(6, contacto.getSitioWeb() != null ? contacto.getSitioWeb() : "No especificado");

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("✅ Contacto añadido correctamente.");
            } else {
                System.err.println("❌ No se añadió ningún contacto.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Contacto> obtenerContactos() {
        String sql = "SELECT id, nombre, telefono, correo, localidad, imagen, sitioWeb FROM contactos";
        List<Contacto> listaContactos = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Contacto contacto = new Contacto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("localidad"),
                        rs.getString("imagen") != null ? rs.getString("imagen") : "No disponible",
                        rs.getString("sitioWeb") != null ? rs.getString("sitioWeb") : "No especificado"
                );

                listaContactos.add(contacto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaContactos;
    }

    @Override
    public void actualizarContacto(Contacto contacto) {
        if (contacto.getId() <= 0) {
            System.err.println("❌ No se puede actualizar: ID no válido.");
            return;
        }

        if (!existeContacto(contacto.getId())) {
            System.err.println("❌ No se encontró el contacto con ID: " + contacto.getId());
            return;
        }

        String sql = "UPDATE contactos SET nombre = ?, telefono = ?, correo = ?, localidad = ?, imagen = ?, sitioWeb = ? WHERE id = ?";

        try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, contacto.getNombre());
            ps.setString(2, contacto.getTelefono());
            ps.setString(3, contacto.getCorreo());
            ps.setString(4, contacto.getLocalidad());
            ps.setString(5, contacto.getImagen());
            ps.setString(6, contacto.getSitioWeb());
            ps.setInt(7, contacto.getId());

            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("✅ Contacto actualizado correctamente en la BD.");
            } else {
                System.err.println("❌ No se actualizó el contacto. Verifica los datos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean existeContacto(int id) {
        String sql = "SELECT COUNT(*) FROM contactos WHERE id = ?";

        try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void eliminarTodos() {
        String sql = "DELETE FROM contactos";
        try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            
            int filasEliminadas = ps.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("✅ Se han eliminado todos los contactos.");
            } else {
                System.out.println("⚠ No hay contactos para eliminar.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarContacto(int id) {
        if (!existeContacto(id)) {
            System.err.println("❌ No se encontró el contacto con ID: " + id);
            return;
        }

        String sql = "DELETE FROM contactos WHERE id = ?";
        
        try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            int filasEliminadas = ps.executeUpdate();
            
            if (filasEliminadas > 0) {
                System.out.println("✅ Contacto eliminado correctamente.");
            } else {
                System.err.println("❌ No se eliminó el contacto. Verifica el ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
