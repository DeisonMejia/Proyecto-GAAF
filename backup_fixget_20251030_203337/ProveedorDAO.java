package com.gaaf;
import java.sql.*; import java.util.*;
public class ProveedorDAO {
    public java.util.List<ProveedorRow> listar() throws SQLException {
        // Cambia 'proveedor' por tu VISTA si aplica (p.ej. v_proveedor)
        final String sql = "SELECT idProveedor,nombre,direccion,telefono,correo,ciudad,vereda,observaciones,activo FROM proveedor ORDER BY idProveedor";
        java.util.List<ProveedorRow> out = new java.util.ArrayList<>();
        try (Connection cn = Db.get(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new ProveedorRow(
                    rs.getInt("idProveedor"), rs.getString("nombre"), rs.getString("direccion"), rs.getString("telefono"),
                    rs.getString("correo"), rs.getString("ciudad"), rs.getString("vereda"), rs.getString("observaciones"),
                    rs.getInt("activo")
                ));
            }
        }
        return out;
    }
    public java.util.List<String> listarIncompletos() throws java.sql.SQLException {
        final String sql = "SELECT nombre FROM v_proveedores_incompletos ORDER BY nombre";
        java.util.List<String> out = new java.util.ArrayList<>();
        try (java.sql.Connection cn = Db.get();
             java.sql.PreparedStatement ps = cn.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { out.add(rs.getString("nombre")); }
        }
        return out;
    }
}