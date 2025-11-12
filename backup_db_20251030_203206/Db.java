package com.gaaf;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Db {
  private static final Properties P = new Properties();
  static {
    try (InputStream in = Db.class.getResourceAsStream("/db.properties")) {
      if (in == null) throw new RuntimeException("no se encontro db.properties");
      P.load(new InputStreamReader(in, StandardCharsets.UTF_8));
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (Exception e) {
      throw new RuntimeException("error cargando propiedades: " + e.getMessage(), e);
    }
  }
  public static Connection get() throws SQLException {
    return DriverManager.getConnection(
        P.getProperty("db.url"),
        P.getProperty("db.user"),
        P.getProperty("db.password"));
  }
}