package com.gaaf;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * App: punto de entrada de la aplicacion.
 * Mantiene la ventana (Stage) y cambia entre pantallas.
 */
public class App extends Application {

  /** ventana principal */
  private Stage stage;

  /** arranque de JavaFX: crea la ventana y muestra el home */
  @Override
  public void start(Stage stage) {
    this.stage = stage;
    stage.setTitle("GAAF");
    mostrarHome();
    stage.show();
  }

  /** navega a la pantalla de inicio (home) */
  public void mostrarHome() {
    HomeView home = new HomeView(this);
    Scene scene = new Scene(home.getRoot(), 900, 520);
    stage.setScene(scene);
  }

  /** navega a la pantalla de inventario */
  public void mostrarInventario() {
    InventarioView inventario = new InventarioView(this);
    Scene scene = new Scene(inventario.getRoot(), 900, 520);
    stage.setScene(scene);
  }

  public static void main(String[] args) { launch(args); }
}