package com.gaaf;

public class Usuario {
    private final int id;
    private final String nombre;
    private final Rol rol;

    public Usuario(int id, String nombre, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public Rol getRol() { return rol; }
}