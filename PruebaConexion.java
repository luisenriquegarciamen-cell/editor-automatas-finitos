package bibliotecauniversitaria;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            if (conexion != null) {
                System.out.println("Conexión realizada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("No se pudo establecer la conexión.");
            System.out.println("Detalle: " + e.getMessage());
        }
    }
}
