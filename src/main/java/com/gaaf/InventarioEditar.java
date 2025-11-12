package com.gaaf;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class InventarioEditar {

    private final App app;
    private final BorderPane root = new BorderPane();
    private final TableView<InventarioRow> tabla = new TableView<>();
    private final InventarioDAO dao = new InventarioDAO();
    private final Label pie = new Label("Inventario cargado.");

    // Formulario
    private final TextField txtIdInventario = new TextField();
    private final TextField txtIdBodega = new TextField();
    private final TextField txtIdPedido = new TextField();
    private final TextField txtCantidadTotal = new TextField();
    private final TextField txtCantidadPorBodega = new TextField();

    public InventarioEditar(App app){
        this.app = app;
        root.setPadding(new Insets(10));

        // Header
        Button btnBack = new Button("<- Regresar");
        btnBack.setOnAction(e -> app.mostrarInventario());

        Label titulo = new Label("Editar Inventario");
        titulo.getStyleClass().add("titulo");

        HBox header = new HBox(10, btnBack, titulo);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(6));

        // Tabla
        TableColumn<InventarioRow, Integer> cIdInv = new TableColumn<>("ID_inventario");
        cIdInv.setCellValueFactory(new PropertyValueFactory<>("idInventario"));
        cIdInv.setPrefWidth(120);

        TableColumn<InventarioRow, Integer> cIdBod = new TableColumn<>("ID_bodega");
        cIdBod.setCellValueFactory(new PropertyValueFactory<>("idBodega"));

        TableColumn<InventarioRow, Integer> cIdPed = new TableColumn<>("ID_pedido");
        cIdPed.setCellValueFactory(new PropertyValueFactory<>("idPedido"));

        TableColumn<InventarioRow, BigDecimal> cCantTot = new TableColumn<>("Cantidad total");
        cCantTot.setCellValueFactory(new PropertyValueFactory<>("cantidadTotal"));

        TableColumn<InventarioRow, BigDecimal> cCantBod = new TableColumn<>("Cantidad Por Bodega");
        cCantBod.setCellValueFactory(new PropertyValueFactory<>("cantidadPorBodega"));

        tabla.getColumns().addAll(cIdInv, cIdBod, cIdPed, cCantTot, cCantBod);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) cargarFormulario(sel);
        });

        recargarTabla();

        // Formulario a la derecha
        txtIdInventario.setPromptText("ID_inventario");
        txtIdInventario.setEditable(false);
        txtIdBodega.setPromptText("ID_bodega");
        txtIdBodega.setEditable(false);
        txtIdPedido.setPromptText("ID_pedido");
        txtIdPedido.setEditable(false);
        txtCantidadTotal.setPromptText("Cantidad total (KG)");
        txtCantidadPorBodega.setPromptText("Cantidad por bodega (KG)");

        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.addRow(0, new Label("ID_inventario:"), txtIdInventario);
        form.addRow(1, new Label("ID_bodega:"), txtIdBodega);
        form.addRow(2, new Label("ID_pedido:"), txtIdPedido);
        form.addRow(3, new Label("Cantidad total:"), txtCantidadTotal);
        form.addRow(4, new Label("Cantidad por bodega:"), txtCantidadPorBodega);

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setDefaultButton(true);
        btnGuardar.setOnAction(e -> guardarCambios());

        VBox derecha = new VBox(10, form, btnGuardar);
        derecha.setAlignment(Pos.TOP_LEFT);
        derecha.setPadding(new Insets(6));

        // Layout
        root.setTop(header);
        root.setCenter(new VBox(6, tabla, pie));
        root.setRight(derecha);
    }

    private void recargarTabla() {
        try {
            tabla.getItems().setAll(dao.listar());
            pie.setText("Inventario cargado.");
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Error cargando inventario: " + ex.getMessage()).showAndWait();
            pie.setText("Error al cargar inventario.");
        }
    }

    private void cargarFormulario(InventarioRow r){
        txtIdInventario.setText(String.valueOf(r.getIdInventario()));
        txtIdBodega.setText(String.valueOf(r.getIdBodega()));
        txtIdPedido.setText(String.valueOf(r.getIdPedido()));
        txtCantidadTotal.setText(r.getCantidadTotal() != null ? r.getCantidadTotal().toPlainString() : "");
        txtCantidadPorBodega.setText(r.getCantidadPorBodega() != null ? r.getCantidadPorBodega().toPlainString() : "");
    }

    private void guardarCambios() {
        if (txtIdInventario.getText().isBlank()) {
            new Alert(Alert.AlertType.WARNING, "Selecciona una fila de la tabla.").showAndWait();
            return;
        }
        try {
            int idInventario = Integer.parseInt(txtIdInventario.getText().trim());
            int idBodega = Integer.parseInt(txtIdBodega.getText().trim());
            int idPedido = Integer.parseInt(txtIdPedido.getText().trim());
            BigDecimal cantTot = new BigDecimal(txtCantidadTotal.getText().trim());
            BigDecimal cantBod = new BigDecimal(txtCantidadPorBodega.getText().trim());

            final String sql = "UPDATE inventario " +
                    "SET idBodega=?, idPedido=?, cantidadTotal=?, cantidadPorBodega=? " +
                    "WHERE idInventario=?";

            try (Connection cn = Db.get();
                 PreparedStatement ps = cn.prepareStatement(sql)) {

                ps.setInt(1, idBodega);
                ps.setInt(2, idPedido);
                ps.setBigDecimal(3, cantTot);
                ps.setBigDecimal(4, cantBod);
                ps.setInt(5, idInventario);

                int updated = ps.executeUpdate();
                if (updated > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Actualizado correctamente.").showAndWait();
                    app.mostrarInventario(); // volver al inventario
                    return;
                } else {
                    new Alert(Alert.AlertType.WARNING, "No se actualizó ninguna fila. Verifica el ID.").showAndWait();
                }
            }
        } catch (NumberFormatException nf) {
            new Alert(Alert.AlertType.WARNING, "Revisa que los campos numéricos sean válidos.").showAndWait();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Error al guardar: " + ex.getMessage()).showAndWait();
        }
    }

    public BorderPane getRoot(){ return root; }
}