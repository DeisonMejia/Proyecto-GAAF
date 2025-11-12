package com.gaaf;


import javafx.scene.control.Tooltip;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class InventarioView {
    private final BorderPane root;
    private final TableView<InventarioRow> tabla = new TableView<>();
    private final Label lblConsulta = new Label();   // aquí mostramos el mensaje
    private final InventarioDAO dao = new InventarioDAO();

    public InventarioView(App app){
        root = new BorderPane();
        root.setPadding(new Insets(6));

        // Header: solo regresar + título
        Button btnBack = new Button("<- Regresar");
        btnBack.setOnAction(e -> app.mostrarHome());

        Label titulo = new Label("Inventario");
        titulo.getStyleClass().add("titulo");

        HBox header = new HBox(10, btnBack, titulo);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(6));

        // Tabla
        TableColumn<InventarioRow, Integer> cIdInv = new TableColumn<>("ID_inventario");
        cIdInv.setCellValueFactory(new PropertyValueFactory<>("idInventario"));

        TableColumn<InventarioRow, Integer> cIdPed = new TableColumn<>("ID_pedido");
        cIdPed.setCellValueFactory(new PropertyValueFactory<>("idPedido"));

        TableColumn<InventarioRow, Integer> cIdBod = new TableColumn<>("ID_bodega");
        cIdBod.setCellValueFactory(new PropertyValueFactory<>("idBodega"));

        TableColumn<InventarioRow, BigDecimal> cCantTot = new TableColumn<>("Cantidad total");
        cCantTot.setCellValueFactory(new PropertyValueFactory<>("cantidadTotal"));

        TableColumn<InventarioRow, BigDecimal> cCantBod = new TableColumn<>("Cantidad por bodega");
        cCantBod.setCellValueFactory(new PropertyValueFactory<>("cantidadPorBodega"));

        tabla.getColumns().addAll(cIdInv, cIdPed, cIdBod, cCantTot, cCantBod);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        // Cargar datos tabla inventario
        cargarDB();

        // Barra de consulta: texto + botón "Actualizar consulta"
        Button btnActualizar = new Button("Actualizar consulta");
        btnActualizar.setOnAction(e -> actualizarConsultaDesdePedidoProducto());

        lblConsulta.setPadding(new Insets(0, 8, 0, 0));
        HBox barraConsulta = new HBox(10, lblConsulta, btnActualizar);
        barraConsulta.setAlignment(Pos.CENTER); // centrados
        barraConsulta.setPadding(new Insets(6, 0, 0, 0));

        // Botón Editar centrado abajo
        Button btnEditar = new Button("Editar");
if (!(app.tieneRol(Rol.ADMIN, Rol.JEFE_BODEGA))) {
    btnEditar.setDisable(true);
    btnEditar.setOpacity(0.6);
    btnEditar.setTooltip(new Tooltip("No permitido para tu rol"));
}
        btnEditar.setOnAction(e -> app.mostrarInventarioEditar());
        boolean puedeEditar = app.tieneRol(Rol.ADMIN, Rol.JEFE_BODEGA);
        btnEditar.setVisible(puedeEditar);
        btnEditar.setManaged(puedeEditar);
        HBox barraEditar = new HBox(btnEditar);
        barraEditar.setAlignment(Pos.CENTER);
        barraEditar.setPadding(new Insets(6, 0, 6, 0));

        // Layout
        VBox centro = new VBox(6, tabla, barraConsulta, barraEditar);
        root.setTop(header);
        root.setCenter(centro);

        // Mostrar el mensaje inicial consultando pedido_producto
        actualizarConsultaDesdePedidoProducto();
    }

    private void cargarDB() {
        try {
            tabla.getItems().setAll(dao.listar());
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Error al cargar inventario: " + ex.getMessage()).showAndWait();
        }
    }

    // Consulta en la tabla pedido_producto y pinta:
    // "La cantidad total en bodega es de X KG"
    private void actualizarConsultaDesdePedidoProducto() {
        BigDecimal total = BigDecimal.ZERO;
        final String sql = "SELECT COALESCE(SUM(cantidad), 0) AS total FROM pedido_producto"; // <-- cambia 'cantidad' si tu columna es otra

        try (Connection cn = Db.get();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getBigDecimal(1);
                if (total == null) total = BigDecimal.ZERO;
            }
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Error consultando pedido_producto: " + ex.getMessage()).showAndWait();
        }

        lblConsulta.setText("La cantidad total en bodega es de " + total + " KG");
    }

    public BorderPane getRoot() { return root; }
}