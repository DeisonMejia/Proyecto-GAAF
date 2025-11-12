package com.gaaf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthDAO {

    public Usuario login(String nombre, String contrasena) throws Exception {
        // Carga la consulta desde resources (no hay SQL en el codigo Java)
        String sql = SqlLoader.load("/sql/auth_login.sql");

        try (Connection cn = Db.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, contrasena);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("rol")
                    );
                }
                // Log de diagnostico (no expone datos sensibles)
                System.out.println("AuthDAO: login fallido para usuario='" + nombre + "'");
                return null;
            }
        }
    }
}
