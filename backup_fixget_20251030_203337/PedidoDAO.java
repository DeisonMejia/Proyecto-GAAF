package com.gaaf;
import java.sql.*; import java.util.*; import java.time.LocalDate; import java.math.BigDecimal;
public class PedidoDAO {
    public List<PedidoRow> listar() throws SQLException {
        // Cambia 'pedido' por el nombre de tu VISTA si ya la expones (p.ej. v_pedido)
        final String sql = "SELECT idPedido,idProveedor,fechaEntrega,cantidadTotal,fechaCreacion,pesoTotal,recibido,observaciones FROM pedido ORDER BY idPedido";
        List<PedidoRow> out = new ArrayList<>();
        try (Connection cn = Db.get(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new PedidoRow(
                    rs.getInt("idPedido"),
                    rs.getInt("idProveedor"),
                    rs.getDate("fechaEntrega").toLocalDate(),
                    rs.getInt("cantidadTotal"),
                    rs.getDate("fechaCreacion").toLocalDate(),
                    rs.getBigDecimal("pesoTotal"),
                    rs.getInt("recibido"),
                    rs.getString("observaciones")
                ));
            }
        }
        return out;
    }
    public PedidoResumen resumenPendientes() throws java.sql.SQLException {
        final String sql = "SELECT pendientes, total_kg FROM v_resumen_pedidos_pendientes";
        try (java.sql.Connection cn = Db.get();
             java.sql.PreparedStatement ps = cn.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new PedidoResumen(rs.getInt("pendientes"), rs.getBigDecimal("total_kg"));
            }
            return new PedidoResumen(0, java.math.BigDecimal.ZERO);
        }
    }
}