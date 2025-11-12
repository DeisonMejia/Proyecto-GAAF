package com.gaaf;

public class Sesion {
    private static Usuario usuarioActual;

    public static void iniciarSesion(Usuario usr) {
        usuarioActual = usr;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static int getId() {
        if (usuarioActual != null) {
            return usuarioActual.getId(); // tu clase Usuario debe tener getId()
        } else {
            return -1; // si no hay usuario logueado
        }
    }
}
