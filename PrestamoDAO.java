package bibliotecauniversitaria;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    public boolean registrar(Prestamo prestamo) {
        String sql = "INSERT INTO prestamo "
                + "(libro_id, usuario_id, fecha_prestamo, fecha_devolucion, estado) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, prestamo.getLibroId());
            ps.setInt(2, prestamo.getUsuarioId());
            ps.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));

            if (prestamo.getFechaDevolucion() == null || prestamo.getFechaDevolucion().isEmpty()) {
                ps.setNull(4, Types.DATE);
            } else {
                ps.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
            }

            ps.setString(5, prestamo.getEstado());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar préstamo: " + e.getMessage());
            return false;
        }
    }

    public List<Prestamo> listar() {
        List<Prestamo> prestamos = new ArrayList<>();

        // JOIN con libro y usuario para no mostrar solo los IDs en el JTable
        String sql = "SELECT p.id, p.libro_id, p.usuario_id, p.fecha_prestamo, "
                + "p.fecha_devolucion, p.estado, "
                + "l.titulo AS titulo_libro, u.nombre AS nombre_usuario "
                + "FROM prestamo p "
                + "JOIN libro l ON p.libro_id = l.id "
                + "JOIN usuario u ON p.usuario_id = u.id "
                + "ORDER BY p.id";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setId(rs.getInt("id"));
                prestamo.setLibroId(rs.getInt("libro_id"));
                prestamo.setUsuarioId(rs.getInt("usuario_id"));
                prestamo.setFechaPrestamo(String.valueOf(rs.getDate("fecha_prestamo")));

                Date devolucion = rs.getDate("fecha_devolucion");
                prestamo.setFechaDevolucion(devolucion != null ? devolucion.toString() : "");

                prestamo.setEstado(rs.getString("estado"));
                prestamo.setTituloLibro(rs.getString("titulo_libro"));
                prestamo.setNombreUsuario(rs.getString("nombre_usuario"));
                prestamos.add(prestamo);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar préstamos: " + e.getMessage());
        }

        return prestamos;
    }
}
