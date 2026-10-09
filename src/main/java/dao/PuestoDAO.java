package dao;

import modelo.Puesto;
import util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PuestoDAO {

    public void insertar(Puesto p) throws SQLException {
        String sql = "INSERT INTO puestos (nombre, salario_base) VALUES (?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setFloat(2, p.getSalarioBase());
            ps.executeUpdate();
        }
    }

    public List<Puesto> listar() throws SQLException {
        List<Puesto> lista = new ArrayList<>();
        String sql = "SELECT * FROM puestos ORDER BY id_puesto";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Puesto(rs.getInt("id_puesto"), rs.getString("nombre"),
                        rs.getFloat("salario_base")));
            }
        }
        return lista;
    }
}
