package com.gaaf;

import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Aplica permisos por rol SIN ocultar:
 *  - Solo setDisable(true/false) y (opcional) tooltip/opacity.
 *
 * Reglas (lista blanca):
 *  - OPERARIO:        solo "ver-inventario"
 *  - COORDINADOR:     "ver-bodega","ver-pedidos","ver-inventario","insertar-proveedor","insertar-pedido"
 *  - JEFE_BODEGA:     "ver-inventario","editar-inventario"
 *  - ADMIN:           todo
 *  - "cerrar-sesion": siempre permitido
 *
 * Prioriza IDs de botón (setId), si no hay usa el texto visible.
 */
public final class PermisosHelper {

    public static void aplicarEn(Parent root, App app) {
        if (root == null || app == null) return;

        final boolean isAdmin = app.tieneRol(Rol.ADMIN);
        final boolean isCoord = app.tieneRol(Rol.COORDINADOR);
        final boolean isJefe  = app.tieneRol(Rol.JEFE_BODEGA);
        final boolean isOper  = app.tieneRol(Rol.OPERARIO);

        // Construir lista blanca según “case” (rol)
        final Set<String> allow = new HashSet<>();
        if (isAdmin) {
            // Admin: todo permitido -> lo resolvemos más abajo (no hace falta poblar)
        } else if (isCoord) {
            allow.add("ver-bodega");
            allow.add("ver-pedidos");
            allow.add("ver-inventario");
            allow.add("insertar-proveedor");
            allow.add("insertar-pedido");
            allow.add("ver-proveedores");
        } else if (isJefe) {
            allow.add("ver-inventario");
            allow.add("editar-inventario");
        } else if (isOper) {
            allow.add("ver-inventario");
        }

        // Buscar todos los botones en el árbol
        List<Button> botones = new ArrayList<>();
        Deque<Parent> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Parent p = stack.pop();
            for (Node n : p.getChildrenUnmodifiable()) {
                if (n instanceof Button) botones.add((Button) n);
                if (n instanceof Parent) stack.push((Parent) n);
            }
        }

        for (Button b : botones) {
            String id = (b.getId() == null ? "" : b.getId().trim().toLowerCase(Locale.ROOT));
            String t  = (b.getText() == null ? "" : b.getText().trim().toLowerCase(Locale.ROOT));
            String key = normalizeKey(id, t);

            // “Cerrar Sesión” siempre habilitado
            if ("cerrar-sesion".equals(key)) {
                b.setDisable(false);
                b.setOpacity(1.0);
                continue;
            }

            boolean ok = isAdmin || allow.contains(key);

            // Solo deshabilitar/habilitar (sin alerts, sin tocar onAction)
            b.setDisable(!ok);
            if (!ok) {
                b.setOpacity(0.6);
                b.setTooltip(new Tooltip("No permitido para tu rol"));
            } else {
                b.setOpacity(1.0);
            }
        }
    }

    // Mapea ID/TEXTO a “feature key” estable
    private static String normalizeKey(String id, String text) {
        if (id != null && !id.isEmpty()) return id.toLowerCase(Locale.ROOT);
        String t = (text == null ? "" : text.toLowerCase(Locale.ROOT));
        if (t.contains("cerrar sesion") || t.contains("cerrar sesión")) return "cerrar-sesion";
        if (t.contains("inventario") && (t.contains("ver") || !t.contains("editar"))) return "ver-inventario";
        if (t.contains("editar") && t.contains("inventario")) return "editar-inventario";
        if (t.contains("bodega")) return "ver-bodega";
        if (t.contains("ver") && t.contains("pedidos")) return "ver-pedidos";
        if (t.contains("insertar") && t.contains("pedido")) return "insertar-pedido";
        if ((t.contains("insertar") || t.contains("nuevo") || t.contains("agregar")) && t.contains("proveedor")) return "insertar-proveedor";
        return t; // desconocido: quedará deshabilitado salvo admin
    }

    private PermisosHelper() {}
}