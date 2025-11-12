package com.gaaf;



import com.gaaf.Usuario;
import com.gaaf.Rol;
import java.sql.*;

public class AuthDAO {

    // Acepta: contraseña en claro, MD5 o SHA2-256. Usuario puede ser nombre o idUsuario.
    private static final String LOGIN_SQL =
        "SELECT idUsuario, nombre, rol " +
        "FROM usuario " +
        "WHERE activo = 1 " +
        "  AND (nombre = ? OR idUsuario = ?) " +
        "  AND (contrasena = ? OR contrasena = MD5(?) OR contrasena = SHA2(?,256)) " +
        "LIMIT 1";

    public Usuario login(String usuario, String password) {
        int userId = 0;
        String u = (usuario == null) ? "" : usuario.trim();
        String p = (password == null) ? "" : password;
        try { userId = Integer.parseInt(u); } catch (NumberFormatException ignore) {}

        try (Connection con = Db.get();
             PreparedStatement ps = con.prepareStatement(LOGIN_SQL)) {

            ps.setString(1, u);     // nombre
            ps.setInt(2, userId);   // idUsuario si es numérico
            ps.setString(3, p);     // texto plano
            ps.setString(4, p);     // MD5(?)
            ps.setString(5, p);     // SHA2(?,256)

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(rs.getInt("idUsuario"), rs.getString("nombre"), parseRol(rs.getString("rol")));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error autenticando: " + e.getMessage(), e);
        }
    }
    private Rol parseRol(String s){
        if (s == null) return Rol.OPERARIO;
        String k = s.trim().toUpperCase().replace(" ", "_");
        // normalizar variantes comunes de escritura
        if (k.equals("JEFEBODEGA")) k = "JEFE_BODEGA";
        if (k.equals("COORDINADOR")) k = "COORDINADOR";
        if (k.equals("OPERARIO")) k = "OPERARIO";
        if (k.equals("ADMIN")) k = "ADMIN";
        try { return Rol.valueOf(k); } catch (IllegalArgumentException ex) {
            return Rol.OPERARIO;
        }
    }
}