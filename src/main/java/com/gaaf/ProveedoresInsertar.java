package com.gaaf;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ProveedoresInsertar extends BorderPane {

    public ProveedoresInsertar(App app) {

        // --- CABECERA ---
        Button btnRegresar = new Button("← Regresar");
        btnRegresar.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        btnRegresar.setOnAction(e -> {
            Node n = (Node) e.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.getScene().setRoot(new HomeView(app).getRoot());
        });

        Label titulo = new Label("Insertar Proveedor");
        titulo.setFont(new Font("Arial", 24));

        HBox header = new HBox(10, btnRegresar, titulo);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 20, 10, 20));
        setTop(header);

        // --- CAMPOS ---
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del proveedor");

        TextField txtDireccion = new TextField();
        txtDireccion.setPromptText("Dirección");

        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono");

        TextField txtCorreo = new TextField();
        txtCorreo.setPromptText("Correo electrónico");

        TextField txtCiudad = new TextField();
        txtCiudad.setPromptText("Ciudad");

        TextField txtVereda = new TextField();
        txtVereda.setPromptText("Vereda");

        TextArea txtObservaciones = new TextArea();
        txtObservaciones.setPromptText("Observaciones");
        txtObservaciones.setPrefRowCount(3);

        CheckBox chkActivo = new CheckBox("Activo");
        chkActivo.setSelected(true);

        // --- BOTONES ---
        Button btnGuardar = new Button("Guardar");
        Button btnLimpiar = new Button("Limpiar");
        Button btnCancelar = new Button("Cancelar");

        btnGuardar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        btnLimpiar.setStyle("-fx-background-color: #ffc107; -fx-text-fill: white;");
        btnCancelar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");

        // --- ACCIÓN GUARDAR ---
        btnGuardar.setOnAction(e -> {
            try (Connection conn = Db.get()) {
                String sql = "INSERT INTO proveedor (nombre, direccion, telefono, correo, ciudad, vereda, observaciones, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, txtNombre.getText());
                    ps.setString(2, txtDireccion.getText());
                    ps.setString(3, txtTelefono.getText());
                    ps.setString(4, txtCorreo.getText());
                    ps.setString(5, txtCiudad.getText());
                    ps.setString(6, txtVereda.getText());
                    ps.setString(7, txtObservaciones.getText());
                    ps.setBoolean(8, chkActivo.isSelected());
                    ps.executeUpdate();
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("¡Proveedor guardado correctamente!");
                alert.showAndWait();

                // limpiar después de guardar
                txtNombre.clear();
                txtDireccion.clear();
                txtTelefono.clear();
                txtCorreo.clear();
                txtCiudad.clear();
                txtVereda.clear();
                txtObservaciones.clear();
                chkActivo.setSelected(true);

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al guardar");
                alert.setHeaderText("No se pudo insertar el proveedor");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        // --- ACCIÓN LIMPIAR ---
        btnLimpiar.setOnAction(e -> {
            txtNombre.clear();
            txtDireccion.clear();
            txtTelefono.clear();
            txtCorreo.clear();
            txtCiudad.clear();
            txtVereda.clear();
            txtObservaciones.clear();
            chkActivo.setSelected(true);
        });

        // --- ACCIÓN CANCELAR ---
        btnCancelar.setOnAction(e -> {
            Node n = (Node) e.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.getScene().setRoot(new HomeView(app).getRoot());
        });

        HBox botones = new HBox(15, btnGuardar, btnLimpiar, btnCancelar);
        botones.setAlignment(Pos.CENTER);
        botones.setPadding(new Insets(20, 0, 40, 0));

        // --- FORMULARIO ---
        VBox formulario = new VBox(10,
                new Label("Nombre:"), txtNombre,
                new Label("Dirección:"), txtDireccion,
                new Label("Teléfono:"), txtTelefono,
                new Label("Correo:"), txtCorreo,
                new Label("Ciudad:"), txtCiudad,
                new Label("Vereda:"), txtVereda,
                new Label("Observaciones:"), txtObservaciones,
                chkActivo,
                botones
        );
        formulario.setPadding(new Insets(20));
        formulario.setAlignment(Pos.CENTER_LEFT);

        ScrollPane scroll = new ScrollPane(formulario);
        scroll.setFitToWidth(true);
        scroll.setPadding(new Insets(10));
        setCenter(scroll);
    }
}
