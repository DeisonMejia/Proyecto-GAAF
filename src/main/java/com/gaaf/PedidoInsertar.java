package com.gaaf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PedidoInsertar extends BorderPane {

    private static final String URL = "jdbc:mysql://localhost:3306/gaaf";
    private static final String USER = "gaaf";
    private static final String PASSWORD = "gaaf";

    private ComboBox<Proveedor> comboProveedor;

    public PedidoInsertar(App app) {
        if (!app.tieneRol(Rol.ADMIN, Rol.COORDINADOR)) {
            mostrarAlerta("Permiso denegado",
                    "Tu rol no tiene acceso a Insertar Pedido.", Alert.AlertType.WARNING);
            setDisable(true); // bloquea interacción en esta vista
        }

        // --- CABECERA ---
        Button btnRegresar = new Button("← Regresar");
        btnRegresar.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        btnRegresar.setOnAction(e -> {
            Node n = (Node) e.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.getScene().setRoot(new HomeView(app).getRoot());
        });

        Label titulo = new Label("Insertar Pedido");
        titulo.setFont(new Font("Arial", 22));

        HBox header = new HBox(10, btnRegresar, titulo);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 20, 10, 20));
        setTop(header);

        // --- FORMULARIO ---
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20, 40, 20, 40));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(70);
        col2.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1, col2);

        // --- CAMPOS ---
        comboProveedor = new ComboBox<>();
        comboProveedor.setPromptText("Seleccione un proveedor");
        cargarProveedores();

        DatePicker dpFechaEntrega = new DatePicker();
        DatePicker dpFechaCreacion = new DatePicker(LocalDate.now());

        TextField txtCantidadTotal = new TextField();
        txtCantidadTotal.setPromptText("Cantidad total");

        TextField txtPesoTotal = new TextField();
        txtPesoTotal.setPromptText("Peso total (kg)");

        TextField txtObservaciones = new TextField();
        txtObservaciones.setPromptText("Observaciones");

        // --- AÑADIR CAMPOS ---
        grid.addRow(0, new Label("Proveedor:"), comboProveedor);
        grid.addRow(1, new Label("Fecha de Entrega:"), dpFechaEntrega);
        grid.addRow(2, new Label("Fecha de Creación:"), dpFechaCreacion);
        grid.addRow(3, new Label("Cantidad Total:"), txtCantidadTotal);
        grid.addRow(4, new Label("Peso Total:"), txtPesoTotal);
        grid.addRow(5, new Label("Observaciones:"), txtObservaciones);

        // --- BOTONES ---
        Button btnGuardar = new Button("Guardar");
        Button btnLimpiar = new Button("Limpiar");
        Button btnCancelar = new Button("Cancelar");

        btnGuardar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        btnLimpiar.setStyle("-fx-background-color: #ffc107; -fx-text-fill: white;");
        btnCancelar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");

        // --- ACCIÓN GUARDAR ---
        btnGuardar.setOnAction(e -> guardarPedido(dpFechaEntrega, dpFechaCreacion,
                                                  txtCantidadTotal, txtPesoTotal, txtObservaciones));

        btnLimpiar.setOnAction(e -> limpiarCampos(dpFechaCreacion, txtCantidadTotal, txtPesoTotal, txtObservaciones));

        btnCancelar.setOnAction(e -> {
            Node n = (Node) e.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.getScene().setRoot(new HomeView(app).getRoot());
        });

        HBox botones = new HBox(15, btnGuardar, btnLimpiar, btnCancelar);
        botones.setAlignment(Pos.CENTER);
        botones.setPadding(new Insets(20));

        setCenter(grid);
        setBottom(botones);
        BorderPane.setAlignment(grid, Pos.CENTER);
        BorderPane.setAlignment(botones, Pos.CENTER);
    }

    private void guardarPedido(DatePicker dpFechaEntrega, DatePicker dpFechaCreacion,
                               TextField txtCantidadTotal, TextField txtPesoTotal, TextField txtObservaciones) {

        if (comboProveedor.getValue() == null || txtCantidadTotal.getText().isEmpty()
                || txtPesoTotal.getText().isEmpty() || dpFechaEntrega.getValue() == null) {
            mostrarAlerta("Campos vacíos", "Por favor, completa todos los campos obligatorios.", Alert.AlertType.WARNING);
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "INSERT INTO pedido (idProveedor, fechaEntrega, cantidadTotal, fechaCreacion, pesoTotal, observaciones) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, comboProveedor.getValue().getIdProveedor());
            stmt.setDate(2, java.sql.Date.valueOf(dpFechaEntrega.getValue()));
            stmt.setInt(3, Integer.parseInt(txtCantidadTotal.getText()));
            stmt.setDate(4, java.sql.Date.valueOf(dpFechaCreacion.getValue()));
            stmt.setFloat(5, Float.parseFloat(txtPesoTotal.getText()));
            stmt.setString(6, txtObservaciones.getText().isEmpty() ? null : txtObservaciones.getText());

            stmt.executeUpdate();

            mostrarAlerta("Éxito", "Pedido insertado correctamente.", Alert.AlertType.INFORMATION);
            limpiarCampos(dpFechaCreacion, txtCantidadTotal, txtPesoTotal, txtObservaciones);

        } catch (SQLException ex) {
            mostrarAlerta("Error", "Error al insertar pedido: " + ex.getMessage(), Alert.AlertType.ERROR);
            ex.printStackTrace();
        }
    }

    private void limpiarCampos(DatePicker dpFechaCreacion, TextField txtCantidadTotal,
                               TextField txtPesoTotal, TextField txtObservaciones) {
        comboProveedor.setValue(null);
        dpFechaCreacion.setValue(LocalDate.now());
        txtCantidadTotal.clear();
        txtPesoTotal.clear();
        txtObservaciones.clear();
    }

    private void cargarProveedores() {
        ObservableList<Proveedor> lista = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT idProveedor, nombre FROM proveedor");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Proveedor(rs.getInt("idProveedor"), rs.getString("nombre")));
            }

        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar los proveedores: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        comboProveedor.setItems(lista);
    }

    public static class Proveedor {
        private final int idProveedor;
        private final String nombre;

        public Proveedor(int idProveedor, String nombre) {
            this.idProveedor = idProveedor;
            this.nombre = nombre;
        }

        public int getIdProveedor() {
            return idProveedor;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
