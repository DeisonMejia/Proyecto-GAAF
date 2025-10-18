package com.gaaf;

import java.math.BigDecimal;

/**
 * DTO simple que representa una fila de la tabla inventario.
 * Se usa como item de la TableView.
 */
public class InventarioRow {
  private Integer idInventario;
  private Integer idPedido;
  private Integer idBodega;
  private BigDecimal cantidadTotal;
  private BigDecimal cantidadPorBodega;

  public InventarioRow(Integer idInventario, Integer idPedido, Integer idBodega,
                       BigDecimal cantidadTotal, BigDecimal cantidadPorBodega) {
    this.idInventario = idInventario;
    this.idPedido = idPedido;
    this.idBodega = idBodega;
    this.cantidadTotal = cantidadTotal;
    this.cantidadPorBodega = cantidadPorBodega;
  }

  public Integer getIdInventario() { return idInventario; }
  public Integer getIdPedido() { return idPedido; }
  public Integer getIdBodega() { return idBodega; }
  public BigDecimal getCantidadTotal() { return cantidadTotal; }
  public BigDecimal getCantidadPorBodega() { return cantidadPorBodega; }
}