package com.gaaf;
import java.math.BigDecimal;

public class PedidoResumen {
    private final int cantidad;
    private final BigDecimal totalKg;

    public PedidoResumen(int cantidad, BigDecimal totalKg){
        this.cantidad = cantidad;
        this.totalKg  = totalKg;
    }

    public int getCantidad(){ return cantidad; }
    public BigDecimal getTotalKg(){ return totalKg; }
}
