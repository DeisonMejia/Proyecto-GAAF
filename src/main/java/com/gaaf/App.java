package com.gaaf;


import javafx.scene.Parent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
    private Stage stage;
    private Scene scene;
    

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        // ARRANCA en Login
        BorderPane root = new LoginView(this).getRoot();
        this.scene = new Scene(root, 900, 600);

        try {
            this.scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        } catch (Exception ignore) {}

        this.stage.setTitle("GAAF");
        this.stage.setScene(this.scene);
        this.stage.show();}

    // Navegacion centralizada
    public void mostrarHome() {
        this.scene.setRoot(new HomeView(this).getRoot());}
    public void mostrarLogin(){
    this.scene.setRoot(new LoginView(this).getRoot());}

    // Usuario en sesion
    
    

    public static void main(String[] args) { launch(args);}

    public void mostrarInventario(){ this.scene.setRoot(new InventarioView(this).getRoot());}
    public void mostrarPedidos(){ this.scene.setRoot(new PedidosView(this).getRoot());}
    public void mostrarProveedores(){ this.scene.setRoot(new ProveedoresView(this).getRoot());}
    public void mostrarBodega(){ this.scene.setRoot(new BodegaView(this).getRoot());}

    public void cerrarSesion(){
        this.usuarioActual = null;
        mostrarLogin();}    public void mostrarInventarioEditar(){
        this.scene.setRoot(new InventarioEditar(this).getRoot());
    }
    // === CONTROL DE ROLES / USUARIO ACTUAL ===
    // por defecto: ADMIN. (cámbialo cuando tengas login real)
    

    
    
    
    // === CONTROL DE ROLES / USUARIO ACTUAL ===
    // por defecto: ADMIN. (cámbialo cuando tengas login real)
    private Usuario usuarioActual = new Usuario(1, "admin", Rol.ADMIN);

    public void setUsuarioActual(Usuario u){ this.usuarioActual = u; }
    public Usuario getUsuarioActual(){ return usuarioActual; }
    public boolean tieneRol(Rol... roles){
        if (usuarioActual == null || roles == null) return false;
        for (Rol r : roles) if (usuarioActual.getRol() == r) return true;
        return false;
    }
    // ==== Helper de permisos ====
    public boolean requiereRolYAdvierte(javafx.stage.Window owner, Rol... roles) {
        if (tieneRol(roles)) return true;
        javafx.scene.control.Alert a =
            new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        a.initOwner(owner);
        a.setTitle("Permisos insuficientes");
        a.setHeaderText(null);
        a.setContentText("No tienes permiso para usar esta función.");
        a.showAndWait();
        return false;
    }
}