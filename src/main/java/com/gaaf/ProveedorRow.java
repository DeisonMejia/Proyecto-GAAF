package com.gaaf;
public class ProveedorRow {
    private int idProveedor, activo; private String nombre, direccion, telefono, correo, ciudad, vereda, observaciones;
    public ProveedorRow(int idProveedor,String nombre,String direccion,String telefono,String correo,String ciudad,String vereda,String observaciones,int activo){
        this.idProveedor=idProveedor; this.nombre=nombre; this.direccion=direccion; this.telefono=telefono; this.correo=correo;
        this.ciudad=ciudad; this.vereda=vereda; this.observaciones=observaciones; this.activo=activo;
    }
    public int getIdProveedor(){return idProveedor;} public String getNombre(){return nombre;} public String getDireccion(){return direccion;}
    public String getTelefono(){return telefono;} public String getCorreo(){return correo;} public String getCiudad(){return ciudad;}
    public String getVereda(){return vereda;} public String getObservaciones(){return observaciones;} public int getActivo(){return activo;}
}

