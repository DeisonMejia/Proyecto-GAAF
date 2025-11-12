package com.gaaf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SqlLoader {

    public static String read(String resourcePath) throws IOException {
        String path = resourcePath.replace('\\', '/');

        InputStream stream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(path);

        if (stream == null && !path.startsWith("/")) {
            stream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("/" + path);
        }

        if (stream == null) {
            throw new IOException("No encontre el recurso en classpath: " + path);
        }

        byte[] bytes;
        try {
            bytes = stream.readAllBytes();
        } finally {
            try { stream.close(); } catch (IOException ignore) {}
        }

        // Quitar BOM (EF BB BF) si viniera
        int offset = 0;
        if (bytes.length >= 3
                && (bytes[0] & 0xFF) == 0xEF
                && (bytes[1] & 0xFF) == 0xBB
                && (bytes[2] & 0xFF) == 0xBF) {
            offset = 3;
        }

        String sql = new String(bytes, offset, bytes.length - offset, StandardCharsets.UTF_8);

        // Por seguridad, si quedara U+FEFF al inicio, también lo removemos
        if (!sql.isEmpty() && sql.charAt(0) == '\uFEFF') {
            sql = sql.substring(1);
        }
        return sql;
    }

    // alias por compatibilidad
    public static String load(String resourcePath) throws IOException {
        return read(resourcePath);
    }
}