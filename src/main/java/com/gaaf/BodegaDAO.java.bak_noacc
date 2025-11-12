package com.gaaf;
import java.sql.*; import java.util.*;
public class BodegaDAO {
    public java.util.List<BodegaRow> listar() throws SQLException {
        // Cambia 'bodega' por tu VISTA si aplica (p.ej. v_bodega)
        final String sql = "SELECT idBodega,lugar,movimiento FROM bodega ORDER BY idBodega";
        java.util.List<BodegaRow> out = new java.util.ArrayList<>();
        try (Connection cn = Db.get(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { out.add(new BodegaRow(rs.getInt("idBodega"), rs.getString("lugar"), rs.getInt("movimiento"))); }
        }
        return out;
    }
}

