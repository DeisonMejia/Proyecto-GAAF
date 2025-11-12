package com.gaaf;
import javafx.geometry.*; import javafx.scene.control.*; import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*; import javafx.scene.layout.*; import javafx.scene.control.TableView;
public class BodegaView {
    private final BorderPane root; private final TableView<BodegaRow> tabla = new TableView<>();
    private final Label pie = new Label("Listo."); private final BodegaDAO dao = new BodegaDAO();
    public BodegaView(App app){
        root = new BorderPane(); root.setPadding(new Insets(6));
        Button btnBack = new Button("\u2190 Regresar"); btnBack.setOnAction(e -> app.mostrarHome());
        ImageView logo = new ImageView(new Image(getClass().getResource("/img/logo.png").toExternalForm())); logo.setFitHeight(24); logo.setPreserveRatio(true);
        Label titulo = new Label("Bodegas"); titulo.getStyleClass().add("titulo");
        HBox header = new HBox(8, btnBack, logo, titulo); header.setAlignment(javafx.geometry.Pos.CENTER_LEFT); header.setPadding(new Insets(6));
        configurarTabla(); cargarDB();
        tabla.setPrefHeight(320);
BorderPane panelTabla = new BorderPane(tabla); panelTabla.setPadding(new Insets(8));
        panelTabla.setStyle("-fx-background-color:#ffffff; -fx-border-color:#d0d3d9; -fx-border-radius:10; -fx-background-radius:10;");
        VBox cuerpo = new VBox(6, header, panelTabla);
        root.setCenter(cuerpo); root.setBottom(pie); BorderPane.setMargin(pie, new Insets(6,10,10,10));
        root.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    }
    private void configurarTabla(){
        TableColumn<BodegaRow,Integer> cId = new TableColumn<>("ID_bodega"); cId.setCellValueFactory(new PropertyValueFactory<>("idBodega"));
        TableColumn<BodegaRow,String> cLugar = new TableColumn<>("Lugar"); cLugar.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        TableColumn<BodegaRow,Integer> cMov = new TableColumn<>("Movimiento"); cMov.setCellValueFactory(new PropertyValueFactory<>("movimiento"));
        tabla.getColumns().setAll(cId,cLugar,cMov);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }
    private void cargarDB(){
        try { tabla.getItems().setAll(dao.listar()); pie.setText("Bodegas cargadas desde base de datos."); }
        catch(Exception ex){ new Alert(Alert.AlertType.ERROR,"Error cargando bodegas: "+ex.getMessage()).showAndWait(); pie.setText("Error al cargar bodegas."); }
    }
    public BorderPane getRoot(){ return root; }
}

