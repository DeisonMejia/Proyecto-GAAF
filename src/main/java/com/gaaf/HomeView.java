package com.gaaf;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Pantalla de inicio.
 * Botones centrados verticalmente a la izquierda.
 */
public class HomeView {
    private final BorderPane root;

    public HomeView(App app) {

        root = new BorderPane();
        root.setPadding(new Insets(10));

        // === BotÃ³n Cerrar SesiÃ³n ===
        Button btnCerrarSesion = new Button("Cerrar Sesion");
        btnCerrarSesion.setStyle(" -fx-background-color: linear-gradient(#A6D3EA, #7FB8D8); /* azul claro */-fx-text-fill: #1f2937;");


        btnCerrarSesion.setMaxWidth(Double.MAX_VALUE);
        btnCerrarSesion.setOnAction(e -> {
            Node n = (Node) e.getSource();
            javafx.stage.Stage stage = (javafx.stage.Stage) n.getScene().getWindow();
            stage.setTitle("LoginView");
            stage.getScene().setRoot(new LoginView(app).getRoot());
        });

        // === Otros botones ===
        Button btnInv = new Button("Ver inventario");
btnInv.setOnAction(e -> app.mostrarInventario());

        Button btnPedidos = new Button("Ver pedidos");
        btnPedidos.setOnAction(e -> {
            Node n = (Node) e.getSource();
            javafx.stage.Stage st = (javafx.stage.Stage) n.getScene().getWindow();
            st.getScene().setRoot(new PedidosView(app).getRoot());
        });

        Button btnVerProv = new Button("Ver proveedores");
        btnVerProv.setOnAction(e -> {
            Node n = (Node) e.getSource();
            javafx.stage.Stage st = (javafx.stage.Stage) n.getScene().getWindow();
            st.getScene().setRoot(new ProveedoresView(app).getRoot());
        });

        Button btnInsertarPedido = new Button("Insertar pedido");
        btnInsertarPedido.setOnAction(e -> {
            Node n = (Node) e.getSource();
            javafx.stage.Stage st = (javafx.stage.Stage) n.getScene().getWindow();
            st.getScene().setRoot(new PedidoInsertar(app));
        });
Button btnProv = new Button("Insertar proveedores");
        btnProv.setOnAction(e -> {
            Node n = (Node) e.getSource();
            javafx.stage.Stage st = (javafx.stage.Stage) n.getScene().getWindow();
            st.getScene().setRoot(new ProveedoresInsertar(app));
        });

        Button btnBod = new Button("Ver bodega");
        btnBod.setOnAction(e -> {
            Node n = (Node) e.getSource();
            javafx.stage.Stage st = (javafx.stage.Stage) n.getScene().getWindow();
            st.getScene().setRoot(new BodegaView(app).getRoot());
        });

        // === Menu de botones ===
        // === PERMISOS (disable por rol) ===
        // Por defecto, deshabilitamos todo (excepto Cerrar Sesión, que se deja habilitado donde se creó)
        btnInv.setDisable(true);
        btnPedidos.setDisable(true);
        btnInsertarPedido.setDisable(true);
        btnProv.setDisable(true);
        btnBod.setDisable(true);
        btnVerProv.setDisable(true);

        // Habilitar según rol
        if (app.tieneRol(Rol.ADMIN)) {
            // Admin: todo habilitado
            btnInv.setDisable(false);
            btnPedidos.setDisable(false);
            btnInsertarPedido.setDisable(false);
            btnProv.setDisable(false);
            btnBod.setDisable(false);
            btnVerProv.setDisable(false);
        } else if (app.tieneRol(Rol.OPERARIO)) {
            // Operario: solo ver inventario
            btnInv.setDisable(false);
        } else if (app.tieneRol(Rol.COORDINADOR)) {
            // Coordinador: ver bodega, ver pedidos, ver inventario (no editar),
            // insertar proveedor, insertar pedido
            btnInv.setDisable(false);
            btnPedidos.setDisable(false);
            btnInsertarPedido.setDisable(false);
            btnProv.setDisable(false);
            btnBod.setDisable(false);
            btnVerProv.setDisable(false);
        } else if (app.tieneRol(Rol.JEFE_BODEGA)) {
            // Jefe de Bodega: ver inventario (el botón "Editar inventario" va en la vista de inventario)
            btnInv.setDisable(false);
        }
        // === FIN PERMISOS ===
        VBox menu = new VBox(10, btnInv, btnPedidos, btnInsertarPedido, btnVerProv, btnProv, btnBod);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER_LEFT);
        menu.setFillWidth(true);

        // Todos los botones con mismo ancho
        btnInv.setMaxWidth(Double.MAX_VALUE);
        btnPedidos.setMaxWidth(Double.MAX_VALUE);
        btnInsertarPedido.setMaxWidth(Double.MAX_VALUE);
        btnVerProv.setMaxWidth(Double.MAX_VALUE);
        btnProv.setMaxWidth(Double.MAX_VALUE);
        btnBod.setMaxWidth(Double.MAX_VALUE);

        // Espaciadores para centrar verticalmente
        Region top = new Region();
        Region bottom = new Region();
        VBox.setVgrow(top, Priority.ALWAYS);
        VBox.setVgrow(bottom, Priority.ALWAYS);

        // Columna izquierda con menÃº centrado y botÃ³n de cerrar sesiÃ³n abajo
        VBox left = new VBox(top, menu, bottom, btnCerrarSesion);
        left.setAlignment(Pos.CENTER_LEFT);
        left.setPadding(new Insets(10));
        left.setPrefWidth(200);

        // Centro: logo
        ImageView logo = new ImageView(new Image(getClass().getResource("/img/logo.png").toExternalForm()));
        logo.setFitWidth(360);
        logo.setPreserveRatio(true);
        VBox centro = new VBox(20, logo);
        centro.setAlignment(Pos.CENTER);

        // Aplicar al root
        root.setLeft(left);
        root.setCenter(centro);
        root.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    }

    public BorderPane getRoot() {
        return root;
    }
}
