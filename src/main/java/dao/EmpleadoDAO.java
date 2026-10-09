package dao;

import modelo.Empleado;
import util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    public void insertar(Empleado e) throws SQLException {
        String sql = "INSERT INTO empleados (id_puesto, nombre, apellidos, fecha_contratacion) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, e.getIdPuesto());
            ps.setString(2, e.getNombre());
            ps.setString(3, e.getApellidos());
            ps.setDate(4, e.getFechaContratacion());
            ps.executeUpdate();
        }
    }

    public List<Empleado> listar() throws SQLException {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT e.*, p.nombre AS puesto FROM empleados e "
                + "JOIN puestos p ON e.id_puesto = p.id_puesto ORDER BY e.id_empleado";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Empleado e = new Empleado(rs.getInt("id_puesto"), rs.getString("nombre"),
                        rs.getString("apellidos"), rs.getDate("fecha_contratacion"));
                e.setIdEmpleado(rs.getInt("id_empleado"));
                e.setNombrePuesto(rs.getString("puesto"));
                lista.add(e);
            }
        }
        return lista;
    }

    public void eliminar(int idEmpleado) throws SQLException {
        String sql = "DELETE FROM empleados WHERE id_empleado = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEmpleado);
            ps.executeUpdate();
        }
    }
}
