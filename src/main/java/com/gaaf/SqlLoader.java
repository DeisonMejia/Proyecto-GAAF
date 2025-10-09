package com.gaaf;
import java.io.*;
import java.nio.charset.StandardCharsets;

public final class SqlLoader {
  private SqlLoader() {}

  public static String load(String path) {
    String p1 = path.startsWith("/") ? path : "/" + path;
    try (InputStream in = SqlLoader.class.getResourceAsStream(p1)) {
      if (in != null) return new String(in.readAllBytes(), StandardCharsets.UTF_8);
      // intento alterno por si algun empaquetado cambia el ClassLoader
      InputStream in2 = Thread.currentThread().getContextClassLoader()
          .getResourceAsStream(path.startsWith("/") ? path.substring(1) : path);
      if (in2 != null) return new String(in2.readAllBytes(), StandardCharsets.UTF_8);
      throw new RuntimeException("no se encontro el sql en classpath: " + path + " (probado: " + p1 + ")");
    } catch (IOException e) {
      throw new RuntimeException("error leyendo sql " + path + ": " + e.getMessage(), e);
    }
  }
}
