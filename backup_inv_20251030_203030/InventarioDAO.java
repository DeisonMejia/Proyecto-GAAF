package com.gaaf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class InventarioDAO {

    public ObservableList<InventarioRow> listar() {
        String sql;
        sql = SqlLoader.load("sql/v_inventario.sql");
        

        ObservableList<InventarioRow> data = FXCollections.observableArrayList();

        try (Connection con = Db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Integer idInventario = rs.getInt("idInventario");
                Integer idPedido     = rs.getInt("idPedido");
                Integer idBodega     = rs.getInt("idBodega");
                BigDecimal cantTot   = rs.getBigDecimal("cantidadTotal");
                BigDecimal cantBod   = rs.getBigDecimal("cantidadPorBodega");

                InventarioRow r = new InventarioRow(
                        idInventario,
                        idPedido,
                        idBodega,
                        cantTot,
                        cantBod
                );
                data.add(r);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error consultando v_inventario", ex);
        }

        return data;
    }

    public BigDecimal totalDesdePedidoProducto() {
        String sql;
        sql = SqlLoader.load("sql/pedido_producto_total.sql");
        

        try (Connection con = Db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                BigDecimal v = rs.getBigDecimal(1);
                return v != null ? v : BigDecimal.ZERO;
            }
            return BigDecimal.ZERO;
        } catch (Exception ex) {
            throw new RuntimeException("Error ejecutando totalDesdePedidoProducto", ex);
        }
    }
}