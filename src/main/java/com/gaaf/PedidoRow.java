package com.gaaf;
import java.math.BigDecimal;
import java.time.LocalDate;
public class PedidoRow {
    private int idPedido, idProveedor, cantidadTotal, recibido;
    private LocalDate fechaEntrega, fechaCreacion;
    private BigDecimal pesoTotal;
    private String observaciones;
    public PedidoRow(int idPedido, int idProveedor, LocalDate fechaEntrega, int cantidadTotal,
                     LocalDate fechaCreacion, BigDecimal pesoTotal, int recibido, String observaciones) {
        this.idPedido=idPedido; this.idProveedor=idProveedor; this.fechaEntrega=fechaEntrega; this.cantidadTotal=cantidadTotal;
        this.fechaCreacion=fechaCreacion; this.pesoTotal=pesoTotal; this.recibido=recibido; this.observaciones=observaciones;
    }
    public int getIdPedido(){return idPedido;} public int getIdProveedor(){return idProveedor;}
    public LocalDate getFechaEntrega(){return fechaEntrega;} public int getCantidadTotal(){return cantidadTotal;}
    public LocalDate getFechaCreacion(){return fechaCreacion;} public BigDecimal getPesoTotal(){return pesoTotal;}
    public int getRecibido(){return recibido;} public String getObservaciones(){return observaciones;}
}

