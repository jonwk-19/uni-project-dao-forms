package dao;

import modelo.Marca;
import util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO {

    public void insertar(Marca m) throws SQLException {
        String sql = "INSERT INTO marcas (nombre) VALUES (?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.executeUpdate();
        }
    }

    public List<Marca> listar() throws SQLException {
        List<Marca> lista = new ArrayList<>();
        String sql = "SELECT * FROM marcas ORDER BY id_marca";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Marca m = new Marca(rs.getString("nombre"));
                m.setIdMarca(rs.getInt("id_marca"));
                lista.add(m);
            }
        }
        return lista;
    }

    public void eliminar(int idMarca) throws SQLException {
        String sql = "DELETE FROM marcas WHERE id_marca = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMarca);
            ps.executeUpdate();
        }
    }
}
