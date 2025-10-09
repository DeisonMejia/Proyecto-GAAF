package com.gaaf;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos de inventario.
 * Toda la logica SQL esta en vistas y archivos .sql.
 */
public class InventarioDAO {

  /** lista filas de inventario desde la vista v_inventario */
  public List<InventarioRow> listar() {
    String sql = SqlLoader.load("sql/inventario_listar.sql");
    List<InventarioRow> out = new ArrayList<>();
    try (Connection c = Db.get();
         PreparedStatement ps = c.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        out.add(new InventarioRow(
            rs.getInt("idInventario"),
            rs.getInt("idPedido"),
            rs.getInt("idBodega"),
            rs.getBigDecimal("cantidadTotal"),
            rs.getBigDecimal("cantidadPorBodega")
        ));
      }
    } catch (SQLException e) {
      throw new RuntimeException("error listando inventario: " + e.getMessage(), e);
    }
    return out;
  }

  /** devuelve la suma de pedido_producto.cantidad usando la vista v_total_pedido_producto */
  public BigDecimal totalDesdePedidoProducto() {
    String sql = SqlLoader.load("sql/pedido_producto_total.sql");
    try (Connection c = Db.get();
         PreparedStatement ps = c.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      return rs.next() ? rs.getBigDecimal(1) : BigDecimal.ZERO;
    } catch (SQLException e) {
      throw new RuntimeException("error sumando cantidad: " + e.getMessage(), e);
    }
  }
}