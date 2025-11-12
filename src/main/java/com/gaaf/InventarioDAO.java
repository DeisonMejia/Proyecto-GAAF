package com.gaaf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class InventarioDAO {

    public ObservableList<InventarioRow> listar() {
        String sql =
                "SELECT idInventario, idPedido, idBodega, cantidadTotal, cantidadPorBodega " +
                "FROM v_inventario";

        ObservableList<InventarioRow> data = FXCollections.observableArrayList();

        try (Connection con = Db.get();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Integer idInventario = rs.getInt("idInventario");
                Integer idPedido     = rs.getInt("idPedido");
                Integer idBodega     = rs.getInt("idBodega");
                BigDecimal cantTot   = rs.getBigDecimal("cantidadTotal");
                BigDecimal cantBod   = rs.getBigDecimal("cantidadPorBodega");

                InventarioRow r = new InventarioRow(
                        idInventario, idPedido, idBodega, cantTot, cantBod
                );
                data.add(r);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error consultando v_inventario", ex);
        }
        return data;
    }

    // Usada por InventarioView; toma el total desde la vista existente
    public BigDecimal totalDesdePedidoProducto() {
        String sql = "SELECT total FROM v_total_pedido_producto";
        try (Connection con = Db.get();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                BigDecimal t = rs.getBigDecimal(1);
                return (t != null) ? t : BigDecimal.ZERO;
            }
            return BigDecimal.ZERO;
        } catch (Exception ex) {
            throw new RuntimeException("Error consultando v_total_pedido_producto", ex);
        }
    }
}